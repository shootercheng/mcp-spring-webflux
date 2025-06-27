package com.scd.mcp.webflux.tools;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class RagTool {

//    ChatResponse response = ChatClient.builder(chatModel)
//            .build().prompt()
//            .advisors(new QuestionAnswerAdvisor(vectorStore))
//            .user(userText)
//            .call()
//            .chatResponse();

    @Tool(name = "rag")
    public String rag(String question) {
        return "";
    }
}
