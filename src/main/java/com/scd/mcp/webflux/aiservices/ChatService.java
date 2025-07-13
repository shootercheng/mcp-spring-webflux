package com.scd.mcp.webflux.aiservices;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface ChatService {

    @SystemMessage("请输出英文")
    String chat(String userMsg);
}
