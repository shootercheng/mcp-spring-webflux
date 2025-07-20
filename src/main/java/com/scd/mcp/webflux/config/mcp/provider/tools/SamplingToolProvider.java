package com.scd.mcp.webflux.config.mcp.provider.tools;

import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SamplingToolProvider implements ToolProvider {
    private static final String FILE_APPROVAL_MSG = "文件%s删除不可恢复,是否删除文件? Y-是, N-否";

    @Tool(name = "delete file")
    public String deleteFile(@ToolParam(description = "file name") String fileName, ToolContext toolContext) {
        Object exchangeObj = toolContext.getContext().get(McpToolUtils.TOOL_CONTEXT_MCP_EXCHANGE_KEY);
        if (exchangeObj instanceof McpSyncServerExchange mcpSyncServerExchange) {
            var createMessageRequest = McpSchema.CreateMessageRequest.builder()
                    .messages(List.of(new McpSchema.SamplingMessage(McpSchema.Role.USER,
                            new McpSchema.TextContent(String.format(FILE_APPROVAL_MSG, fileName))))
                    )
                    .modelPreferences(McpSchema.ModelPreferences.builder()
                            .hints(List.of())
                            .costPriority(1.0)
                            .speedPriority(1.0)
                            .intelligencePriority(1.0)
                            .build())
                    .build();
            McpSchema.CreateMessageResult result = mcpSyncServerExchange.createMessage(createMessageRequest);
            if (result == null) {
                return "获取客户端采样(sample)响应失败";
            }
            log.info("收到客户端结果 {}", result);
            if (result.content() instanceof McpSchema.TextContent textContent) {
                if ("Y".equalsIgnoreCase(textContent.text())) {
                    log.info("客户端选择删除文件");
                    log.info("执行文件删除 {}", fileName);
                    return fileName + " 文件删除成功";
                }
            }
            return "已确认不删除文件";
        }
        return "请求异常";
    }
}
