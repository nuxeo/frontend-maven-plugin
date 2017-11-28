package com.github.eirslett.maven.plugins.frontend.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;

public class ComposerInstaller {

    public static final String INSTALL_PATH = "/composer";

    public static final String DEFAULT_NODEJS_DOWNLOAD_ROOT = "https://getcomposer.org/download/";

    private static final Object LOCK = new Object();

    private final Logger logger;

    private final InstallConfig config;

    private final ArchiveExtractor archiveExtractor;

    private final FileDownloader fileDownloader;

    public ComposerInstaller(InstallConfig config, ArchiveExtractor archiveExtractor, FileDownloader fileDownloader) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.config = config;
        this.archiveExtractor = archiveExtractor;
        this.fileDownloader = fileDownloader;
    }

    public void install() throws InstallationException {
        synchronized (LOCK) {
            this.logger.info("Installing composer");
            new PhpExecutor(config.getWorkingDirectory(), Arrays.asList("--version"), Collections.<String, String>emptyMap());
        }
    }

}
