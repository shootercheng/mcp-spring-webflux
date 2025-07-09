package com.scd.jbang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class JbangTest {

    @Test
    public void testVersion() {
        String command = "E:\\Tools\\jbang\\0.126.3\\bin\\jbang.cmd";
        try {
            new ProcessBuilder().command(command, "--version").start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            String message = "jbang is not available (could not execute command '" + command
                    + "', MCP integration tests will be skipped. "
                    + "The command may be overridden via the system property 'jbang.command'";
        }
    }
}
