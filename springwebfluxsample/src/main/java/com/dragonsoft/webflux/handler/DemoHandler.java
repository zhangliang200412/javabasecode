package com.dragonsoft.webflux.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Created by Administrator on 2019/11/13.
 */
@Component
public class DemoHandler {

    public Mono<ServerResponse> sayHello(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("Hello, City!" ));
    }


}
