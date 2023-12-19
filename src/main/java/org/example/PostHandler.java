package org.example;

import lombok.RequiredArgsConstructor;
import org.example.exception.PostAlreadyExistsException;
import org.example.model.Post;
import org.example.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.ServerResponse.status;


@Component
@RequiredArgsConstructor
public class PostHandler {
    private final PostService postService;

    public HandlerFunction<ServerResponse> createSportHandler() {
        return request -> {
            String postName = request.pathVariable("postname");
            return postService.createSport(postName)
                    .flatMap(post -> status(HttpStatus.CREATED).bodyValue(post))
                    .onErrorResume(PostAlreadyExistsException.class,
                            ex -> status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()));
        };
    }

    public HandlerFunction<ServerResponse> searchSportsHandler() {
        return request -> {
            String query = request.queryParam("q").orElse("");
            return ServerResponse.ok().body(postService.searchPostsByName(query), Post.class);
        };
    }
}
