package com.scd.mcp.webflux.config.mcp.provider.tools;

import com.scd.mcp.webflux.aiservices.ChatService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class Langchain4jToolProvider implements ToolProvider {
    private ChatService chatService;

    public Langchain4jToolProvider(ChatService chatService) {
        this.chatService = chatService;
    }

    @Tool(name = "chat with llm")
    public String tool(@ToolParam(description = "input msg") String msg) {
        return chatService.chat(msg);
    }
}
