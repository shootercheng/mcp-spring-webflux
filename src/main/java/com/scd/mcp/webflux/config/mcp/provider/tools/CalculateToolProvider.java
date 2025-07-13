package com.scd.mcp.webflux.config.mcp.provider.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class CalculateToolProvider implements ToolProvider{

    @Tool(name = "sum")
    public int sum(@ToolParam(description = "num1") int a,
                   @ToolParam(description = "num2") int b) {
        return a+b;
    }
}
