package com.scd.mcp.webflux.aiservices;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

@AiService
public interface StreamChatService {

    @SystemMessage("请输出英文")
    Flux<String> chat(String userMsg);
}
