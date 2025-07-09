package com.scd.mcp.webflux.config.mcp.provider;

import com.logaritex.mcp.annotation.McpArg;
import com.logaritex.mcp.annotation.McpPrompt;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromptProvider {

    @McpPrompt(name = "personalized-message",
            description = "Generates a personalized message based on user information")
    public McpSchema.GetPromptResult personalizedMessage(McpSyncServerExchange exchange,
                                                         @McpArg(name = "name", description = "The user's name", required = true) String name,
                                                         @McpArg(name = "age", description = "The user's age", required = false) Integer age,
                                                         @McpArg(name = "interests", description = "The user's interests", required = false) String interests) {

        exchange.loggingNotification(McpSchema.LoggingMessageNotification.builder()
                .level(McpSchema.LoggingLevel.INFO)
                .data("personalized-message event").build());

        StringBuilder message = new StringBuilder();
        message.append("Hello, ").append(name).append("!\n\n");

        if (age != null) {
            message.append("At ").append(age).append(" years old, you have ");
            if (age < 30) {
                message.append("so much ahead of you.\n\n");
            }
            else if (age < 60) {
                message.append("gained valuable life experience.\n\n");
            }
            else {
                message.append("accumulated wisdom to share with others.\n\n");
            }
        }

        if (interests != null && !interests.isEmpty()) {
            message.append("Your interest in ")
                    .append(interests)
                    .append(" shows your curiosity and passion for learning.\n\n");
        }

        message
                .append("I'm here to assist you with any questions you might have about the Model Context Protocol.");

        return new McpSchema.GetPromptResult("Personalized Message",
                List.of(new McpSchema.PromptMessage(McpSchema.Role.ASSISTANT, new McpSchema.TextContent(message.toString()))));
    }
}
