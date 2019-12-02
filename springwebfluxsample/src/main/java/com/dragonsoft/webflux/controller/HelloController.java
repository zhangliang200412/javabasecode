package com.dragonsoft.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Mono<String> hello() {   // 【改】返回类型为Mono<String>
        return Mono.just("Welcome to reactive world ~");     // 【改】使用Mono.just生成响应式数据
    }

//    @GetMapping(value = "/{id}")
//    public Mono<City> findCityById(@PathVariable("id") Long id) {
//        return cityHandler.findCityById(id);
//    }

    @GetMapping("/list")
    public Flux<String> findAllCity() {
        return Flux.just("12","3","4");
    }

    /**
     * netty 下callable无效
     * @return
     */
    @Deprecated
    @GetMapping("/call")
    public Callable<String> findAsyn() {

        System.out.println(Thread.currentThread());

        Callable callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(Thread.currentThread());
                return "callable";
            }
        };
        return callable;
    }
}