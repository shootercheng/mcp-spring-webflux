package com.scd.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class ReactorHotPublish {

    @Test
    public void testReactorHotPublish() {
        // 创建热发布者（使用Sinks）
        Sinks.Many<String> hotSource = Sinks.many().multicast().onBackpressureBuffer();

        // 转换为Flux
        Flux<String> hotFlux = hotSource.asFlux().map(s -> "[热]" + s);

        // 第一个订阅者
        hotFlux.subscribe(s -> System.out.println("订阅者A: " + s));

        // 发射数据（所有活跃订阅者都会收到）
        hotSource.tryEmitNext("消息1");
        hotSource.tryEmitNext("消息2");

        // 第二个订阅者（不会收到之前的数据）
        hotFlux.subscribe(s -> System.out.println("订阅者B: " + s));

        hotSource.tryEmitNext("消息3");
    }
}
