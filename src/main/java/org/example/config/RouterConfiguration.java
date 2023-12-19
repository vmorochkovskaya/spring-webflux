package org.example.config;

import org.example.PostHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterConfiguration {
    @Bean
    public RouterFunction<ServerResponse> sportRoutes(PostHandler postHandler) {
        return RouterFunctions.route(POST("/api/v1/post/{postname}"), postHandler.createSportHandler())
                .andRoute(GET("/api/v1/post").and(queryParam("q", q -> true)), postHandler.searchSportsHandler());
    }
}
