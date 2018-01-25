package com.github.eirslett.maven.plugins.frontend.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.github.eirslett.maven.plugins.frontend.lib.Utils.implode;
import static com.github.eirslett.maven.plugins.frontend.lib.Utils.normalize;
import static com.github.eirslett.maven.plugins.frontend.lib.Utils.prepend;
import java.util.Map;

abstract class NodeTaskExecutor extends AbstractExecutor {

    private final Logger logger;
    private final String taskName;
    private final String taskLocation;
    private final NodeExecutorConfig config;

    public NodeTaskExecutor(NodeExecutorConfig config, String taskLocation) {
        this(config, taskLocation, Collections.<String>emptyList());
    }

    public NodeTaskExecutor(NodeExecutorConfig config, String taskName, String taskLocation) {
        this(config, taskName, taskLocation, Collections.<String>emptyList());
    }

    public NodeTaskExecutor(NodeExecutorConfig config, String taskLocation, List<String> additionalArguments) {
        this(config, getTaskNameFromLocation(taskLocation), taskLocation, additionalArguments);
    }

    public NodeTaskExecutor(NodeExecutorConfig config, String taskName, String taskLocation, List<String> additionalArguments) {
        super(additionalArguments);
        this.logger = LoggerFactory.getLogger(getClass());
        this.config = config;
        this.taskName = taskName;
        this.taskLocation = taskLocation;
    }

    private static String getTaskNameFromLocation(String taskLocation) {
        return taskLocation.replaceAll("^.*/([^/]+)(?:\\.js)?$","$1");
    }


    public final void execute(String args, Map<String, String> environment) throws TaskRunnerException {
        final String absoluteTaskLocation = getAbsoluteTaskLocation();
        final List<String> arguments = getArguments(args);
        logger.info("Running " + taskToString(taskName, arguments) + " in " + config.getWorkingDirectory());

        try {
            final int result = new NodeExecutor(config, prepend(absoluteTaskLocation, arguments), environment).executeAndRedirectOutput(logger);
            if (result != 0) {
                throw new TaskRunnerException(taskToString(taskName, arguments) + " failed. (error code " + result + ")");
            }
        } catch (ProcessExecutionException e) {
            throw new TaskRunnerException(taskToString(taskName, arguments) + " failed.", e);
        }
    }

    private String getAbsoluteTaskLocation() {
        String location = normalize(taskLocation);
        if (Utils.isRelative(taskLocation)) {
            File taskFile = new File(config.getWorkingDirectory(), location);
            if (!taskFile.exists()) {
                taskFile = new File(config.getInstallDirectory(), location);
            }
            location = taskFile.getAbsolutePath();
        }
        return location;
    }

}
