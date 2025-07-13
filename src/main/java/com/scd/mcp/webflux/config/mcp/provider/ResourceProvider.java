package com.scd.mcp.webflux.config.mcp.provider;

import com.logaritex.mcp.annotation.McpResource;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResourceProvider {

    private final Map<String, String> userProfiles = new HashMap<>();

    public ResourceProvider() {
        userProfiles.put("john", "https://john.com");
        userProfiles.put("jane", "https://jane.com");
        userProfiles.put("bob", "https://bob.com");
        userProfiles.put("alice", "https://alice.com");
    }

    private String getUserStatusByName(String username) {
        // Simple logic to generate a status
        if (username.equals("john")) {
            return "🟢 Online";
        } else if (username.equals("jane")) {
            return "🟠 Away";
        } else if (username.equals("bob")) {
            return "⚪ Offline";
        } else if (username.equals("alice")) {
            return "🔴 Busy";
        } else {
            return "⚪ Offline";
        }
    }

    @McpResource(uri = "user-status://{username}",
            name = "User Status",
            description = "Provides the current status for a specific user")
    public String getUserStatus(String username) {
        return this.getUserStatusByName(username);
    }

    @McpResource(uri = "user-profile-exchange://{username}",
            name = "User Profile with Exchange",
            description = "Provides user profile information with server exchange context")
    public McpSchema.ReadResourceResult getProfileWithExchange(McpSyncServerExchange exchange, String username) {

        exchange.loggingNotification(McpSchema.LoggingMessageNotification.builder()
                .level(McpSchema.LoggingLevel.INFO)
                .data("user-profile-exchange")
                .build());

        String profileInfo = userProfiles.getOrDefault(username.toLowerCase(), "https://sss.com");

        return new McpSchema.ReadResourceResult(List.of(new McpSchema.TextResourceContents("user-profile-exchange://" + username,
                "text/plain", "Profile with exchange for " + username + ": " + profileInfo)));
    }
}
