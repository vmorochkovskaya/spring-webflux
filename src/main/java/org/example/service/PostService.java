package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.PostAlreadyExistsException;
import org.example.model.Post;
import org.example.repository.PostRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    public Mono<Post> createSport(String postName) {
        return postRepository.findByTitle(postName)
                .flatMap(existingPost ->
                        Mono.error(new PostAlreadyExistsException("Post with title " + postName + " already exists"))
                )
                .switchIfEmpty(postRepository.save(new Post(postName)))
                .map(savedSport -> (Post) savedSport);
    }

    public Flux<Post> searchPostsByName(String query) {
        return postRepository.findByTitleContainingIgnoreCase(query);
    }
}
