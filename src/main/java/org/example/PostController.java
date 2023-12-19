package org.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.client.PostClient;
import org.example.model.Post;
import org.example.repository.PostRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostRepository postRepository;
    private final PostClient postClient;
    private List<Post> accumulatedPosts;


    @GetMapping("/back")
    public Flux<Post> getPostsBackPressure() {
        accumulatedPosts = new ArrayList<>();
        return Flux.create(sink -> postClient.getPosts()
                .log()
                .subscribe(new CustomSubscriber(sink, postRepository)));
    }

    @GetMapping
    public Flux<Post> getPosts() {
        return postClient.getPosts()
                .publish(postRepository::saveAll);
    }

    class CustomSubscriber extends BaseSubscriber<Post> {
        private final FluxSink<Post> sink;
        private final PostRepository postRepository;
        private int receivedCount = 0;

        public CustomSubscriber(FluxSink<Post> sink, PostRepository postRepository) {
            this.sink = sink;
            this.postRepository = postRepository;
            request(20);
        }

        @Override
        protected void hookOnNext(Post value) {
            accumulatedPosts.add(value);
            receivedCount++;
            if (receivedCount % 20 == 0) {
                log.info("Received 20 items");
                request(20);
            }
        }

        @Override
        protected void hookOnError(Throwable throwable) {
            log.error("Error: " + throwable.getMessage());
            sink.error(throwable);
        }

        @Override
        protected void hookOnComplete() {
            log.error("API stream completed");
            log.info("Size {}", accumulatedPosts.size());
            postRepository.saveAll(accumulatedPosts)
                    .collectList()
                    .subscribe(savedPosts -> {
                        log.info("Saved {} posts to the database", savedPosts.size());
                        sink.complete();
                    });
        }

    }
}
