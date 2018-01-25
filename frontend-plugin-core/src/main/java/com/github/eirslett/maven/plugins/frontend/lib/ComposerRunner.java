package com.github.eirslett.maven.plugins.frontend.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.eirslett.maven.plugins.frontend.lib.Utils.prepend;

public class ComposerRunner extends AbstractExecutor {

    private final Logger logger;
    private File composerDirectory;
    private final PhpExecutorConfig config;

    public ComposerRunner(PhpExecutorConfig config, File composerDirectory) {
        super(new ArrayList<String>());
        this.composerDirectory = composerDirectory;
        this.logger = LoggerFactory.getLogger(getClass());
        this.config = config;
    }

    public final void execute(String args, Map<String, String> environment) throws TaskRunnerException {
        final List<String> arguments = getArguments(args);
        final File composerPath = new File(composerDirectory, "composer.phar");
        logger.info("Running " + taskToString(composerPath.getAbsolutePath(), arguments) + " in " + config.getWorkingDirectory());

        try {
            final int result = new PhpExecutor(config, prepend(composerPath.getAbsolutePath(), arguments), null).executeAndRedirectOutput(logger);
            if (result != 0) {
                throw new TaskRunnerException(taskToString("composer", arguments) + " failed. (error code " + result + ")");
            }
        } catch (ProcessExecutionException e) {
            throw new TaskRunnerException(taskToString("composer", arguments) + " failed.", e);
        }
    }

}
