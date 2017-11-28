package com.github.eirslett.maven.plugins.frontend.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

final class PhpExecutor {

    private final ProcessExecutor executor;

    public PhpExecutor(File workingDirectory, List<String> arguments, Map<String, String> additionalEnvironment) {
        List<String> localPaths = new ArrayList<>();
        executor = new ProcessExecutor(
                workingDirectory,
                localPaths,
                Utils.prepend("php", arguments),
                Platform.guess(),
                additionalEnvironment);
    }

    public String executeAndGetResult(final Logger logger) throws ProcessExecutionException {
        return executor.executeAndGetResult(logger);
    }

    public int executeAndRedirectOutput(final Logger logger) throws ProcessExecutionException {
        return executor.executeAndRedirectOutput(logger);
    }
}
