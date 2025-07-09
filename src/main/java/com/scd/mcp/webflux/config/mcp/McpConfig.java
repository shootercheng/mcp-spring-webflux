package com.scd.mcp.webflux.config.mcp;

import com.logaritex.mcp.spring.SpringAiMcpAnnotationProvider;
import com.scd.mcp.webflux.config.mcp.provider.AutocompleteProvider;
import com.scd.mcp.webflux.config.mcp.provider.PromptProvider;
import com.scd.mcp.webflux.config.mcp.provider.ResourceProvider;
import io.modelcontextprotocol.server.McpServerFeatures;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class McpConfig {

    @Bean
    public List<McpServerFeatures.SyncCompletionSpecification> syncCompletionSpecifications(
            List<AutocompleteProvider> completeProviders) {
        return SpringAiMcpAnnotationProvider.createSyncCompleteSpecifications(new ArrayList<>(completeProviders));
    }

    @Bean
    public List<McpServerFeatures.SyncPromptSpecification> syncPromptSpecifications(
            List<PromptProvider> promptProviders) {
        return SpringAiMcpAnnotationProvider.createSyncPromptSpecifications(new ArrayList<>(promptProviders));
    }

    @Bean
    public List<McpServerFeatures.SyncResourceSpecification> syncResourceSpecifications(
            List<ResourceProvider> resourceProviders) {
        return SpringAiMcpAnnotationProvider.createSyncResourceSpecifications(new ArrayList<>(resourceProviders));
    }

}
