package com.dragonsoft.webflux.configuration;

import com.dragonsoft.webflux.handler.DemoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CityRouter {


    @Bean
    public RouterFunction<ServerResponse> routeCity(DemoHandler cityHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/sayHello")
                                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        cityHandler::sayHello);
    }

}