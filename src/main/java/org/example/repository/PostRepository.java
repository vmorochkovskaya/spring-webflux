package org.example.repository;

import org.example.model.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PostRepository extends ReactiveCrudRepository<Post, Integer> {
    Mono<Post> findByTitle(String title);

    Flux<Post> findByTitleContainingIgnoreCase(String search);
}
