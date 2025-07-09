package com.scd.mcp.webflux.controller;

import com.scd.mcp.webflux.aiservices.StreamChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
public class ChatController {

    private final StreamChatService streamChatService;

    public ChatController(StreamChatService streamChatService) {
        this.streamChatService = streamChatService;
    }

    @GetMapping(value = "/streamingChat", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamingChat(
            @RequestParam(value = "message", defaultValue = "你好") String message) {
        return streamChatService.chat(message);
    }
}
