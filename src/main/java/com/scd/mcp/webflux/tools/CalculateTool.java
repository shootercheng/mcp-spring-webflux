package com.scd.mcp.webflux.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class CalculateTool {

    @Tool(name = "计算2个数的和")
    public Integer sum(
            @ToolParam(description = "num1") Integer num1,
            @ToolParam(description = "num2") Integer num2
    ) {
        return num1 + num2;
    }
}
