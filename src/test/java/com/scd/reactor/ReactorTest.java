package com.scd.reactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReactorTest {

    /**
     * 0 .. N 元素的异步序列
     */
    @Test
    public void testFlux() {
        Flux<Integer> flux = Flux.just(1,2,3,4);
        flux.subscribe(System.out::println);
    }

    /**
     * 0-1 个结果
     */
    @Test
    public void testMono() {
        Mono<Integer> mono = Mono.just(1);
        mono.subscribe(System.out::println);
    }

    @Test
    public void testFluxToMono() {
        Mono<List<Integer>> listMono = Flux.just(1,2,3,4).collectList();
        listMono.subscribe(integers -> {
            var result = List.of(1,2,3,4);
            Assertions.assertEquals(result.size(), integers.size());
            for (int i = 0; i < result.size(); i++) {
                Assertions.assertEquals(result.get(i), integers.get(i));
            }
        });
    }

    @Test
    public void testMonoToFlux() {
       Flux<Integer> flux = Mono.just(1).concatWith(Mono.just(2));
       final int[] i = {0};
       var result = List.of(1, 2);
       flux.subscribe(integer -> {
           Assertions.assertEquals(result.get(i[0]), integer);
           i[0]++;
       });
    }

    @Test
    public void testFluxMap() {
        var flux = Flux.just(1,2,3);
        flux.map(n -> n*n)
                .reduce(0, Integer::sum)
                .subscribe(integer ->
                        Assertions.assertEquals(1 + 2*2 + 3*3, integer));
    }

    @Test
    public void testFluxZip() {
        var flux1 = Flux.just(1,2,3);
        var flux2 = Flux.just("A","B","C");
        var result = List.of("1A","2B","3C");
        var index = new AtomicInteger(0);
        Flux.zip(flux1, flux2)
                .map(t -> t.getT1() + t.getT2())
                .collectList().subscribe(list -> {
                    int curIndex = index.get();
                    Assertions.assertEquals(result.get(curIndex), list.get(curIndex));
                    index.getAndIncrement();
                });
    }

    @Test
    public void testAsyncInvoke() {
        String[] threadNames = new String[2];
        Mono<String> result = getUserToken()
                .flatMap( token -> {
                    threadNames[0] = Thread.currentThread().getName();
                    return getProfile(token);
                }).flatMap(profile -> {
                    threadNames[1] = Thread.currentThread().getName();
                    return getInfo(profile);
                });
        result.subscribe(s -> Assertions.assertEquals("info profile token", s));
        System.out.println(Thread.currentThread().getName() + ":"
                + threadNames[0] + "-" + threadNames[1]);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + ":"
                + threadNames[0] + "," + threadNames[1]);
    }


    private Mono<String> getUserToken() {
        return Mono.just("token").delayElement(Duration.ofSeconds(1));
    }

    private Mono<String> getProfile(String token) {
        return Mono.just("profile " + token).delayElement(Duration.ofSeconds(1));
    }

    private Mono<String> getInfo(String profile) {
        return Mono.just("info " + profile).delayElement(Duration.ofSeconds(1));
    }

    @Test
    public void testErrorBack() {
        mainSource().onErrorResume(throwable -> {
            throwable.printStackTrace();
            return backSource();
        }).subscribe(System.out::println);
    }

    private Flux<String> mainSource() {
        return Flux.error(new RuntimeException("mock main source error"));
    }

    private Flux<String> backSource() {
        return Flux.range(1, 10)
                .map(String::valueOf);
    }

    @Test
    public void testPublish() throws InterruptedException {
        var publish = Flux.interval(Duration.ofSeconds(1))
                        .share();
        publish.subscribe(aLong -> System.out.println("sub1:" + aLong));
        Thread.sleep(5000);
        publish.subscribe(aLong -> System.out.println("sub2:" + aLong));
        Thread.sleep(5000);
    }

}
