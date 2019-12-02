package com.dragonsoft.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Flux;

/**
 * Created by Administrator on 2019/11/12.
 */
@SpringBootApplication
@EnableWebFlux
public class WebfluxApplication {

    public static void main(String []args){

        SpringApplication.run(WebfluxApplication.class, args);

    }
}
