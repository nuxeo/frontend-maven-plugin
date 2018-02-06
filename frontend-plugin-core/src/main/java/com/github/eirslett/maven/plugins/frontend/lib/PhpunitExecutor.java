package com.github.eirslett.maven.plugins.frontend.lib;

import java.io.File;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.eirslett.maven.plugins.frontend.lib.Utils.prepend;

public class PhpunitExecutor extends AbstractExecutor {

    private final Logger logger;
    private File phpunitPath;
    private final PhpExecutorConfig config;

    public PhpunitExecutor(PhpExecutorConfig config, File phpunitPath) {
        super(Collections.<String>emptyList());
        logger = LoggerFactory.getLogger(getClass());
        this.config = config;
        this.phpunitPath = phpunitPath;
    }

    public void execute(String args, Map<String, String> environment) throws TaskRunnerException {
        final List<String> arguments = getArguments(args);
        logger.info("Running " + taskToString(phpunitPath.getAbsolutePath(), arguments) + " in " + config.getWorkingDirectory());

        try {
            final int result = new PhpExecutor(config, prepend(phpunitPath.getAbsolutePath(), arguments), null).executeAndRedirectOutput(logger);
            if (result != 0) {
                throw new TaskRunnerException(taskToString("phpunit", arguments) + " failed. (error code " + result + ")");
            }
        } catch (ProcessExecutionException e) {
            throw new TaskRunnerException(taskToString("phpunit", arguments) + " failed.", e);
        }
    }
}
