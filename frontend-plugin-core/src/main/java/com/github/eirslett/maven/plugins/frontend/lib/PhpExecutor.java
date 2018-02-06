package com.github.eirslett.maven.plugins.frontend.lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

final class PhpExecutor extends AbstractExecutor {

    private final ProcessExecutor executor;

    public PhpExecutor(PhpExecutorConfig config, List<String> arguments, Map<String, String> additionalEnvironment) {
        super(arguments);
        executor = new ProcessExecutor(
                config.getWorkingDirectory(),
                Collections.<String>emptyList(),
                Utils.prepend(config.getPhpPath().getAbsolutePath(), arguments),
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
