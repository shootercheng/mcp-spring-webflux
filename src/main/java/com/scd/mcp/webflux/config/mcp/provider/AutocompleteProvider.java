package com.scd.mcp.webflux.config.mcp.provider;

import com.logaritex.mcp.annotation.McpComplete;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AutocompleteProvider {

    private final Map<String, List<String>> usernameDatabase = new HashMap<>();
    private final Map<String, List<String>> cityDatabase = new HashMap<>();

    public AutocompleteProvider() {
        // Initialize with sample data
        cityDatabase.put("l", List.of("Lagos", "Lima", "Lisbon", "London", "Los Angeles"));

        usernameDatabase.put("a", List.of("alex123", "admin", "alice_wonder", "andrew99"));
        usernameDatabase.put("j", List.of("james", "john", "johnson", "julia", "jane"));
        usernameDatabase.put("b", List.of("bob", "Ben", "Benson"));
    }

    @McpComplete(prompt = "personalized-message")
    public List<String> completeName(String name) {
        String prefix = name.toLowerCase();
        String firstLetter = prefix.substring(0, 1);
        List<String> usernames = usernameDatabase.getOrDefault(firstLetter, List.of());

        return usernames.stream().filter(username -> username.toLowerCase().startsWith(prefix)).toList();
    }

    @McpComplete(uri = "user-status://{username}")
    public List<String> completeUserName(String name) {
        return completeName(name);
    }

    @McpComplete(uri = "user-profile-exchange://{username}")
    public List<String> completeUserNameForProfile(String name) {
        return completeName(name);
    }

    @McpComplete(prompt = "travel-planner")
    public List<String> completeCityName(McpSchema.CompleteRequest.CompleteArgument argument) {
        String prefix = argument.value().toLowerCase();
        String firstLetter = prefix.substring(0, 1);
        List<String> cities = cityDatabase.getOrDefault(firstLetter, List.of());

        return cities.stream()
                .filter(city -> city.toLowerCase().startsWith(prefix))
                .toList();
    }
}
