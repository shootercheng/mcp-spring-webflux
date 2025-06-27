package com.scd.mcp.webflux;

import com.scd.mcp.webflux.tools.CalculateTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpSpringWebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpSpringWebfluxApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider calculateTools(CalculateTool calculateTool) {
		return MethodToolCallbackProvider.builder().toolObjects(calculateTool).build();
	}

}
