package com.github.eirslett.maven.plugins.frontend.lib;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ComposerInstaller {

    public static final String INSTALL_PATH = ".php_modules/bin";

    public static final String DEFAULT_DOWNLOAD_ROOT = "https://getcomposer.org/download/";

    private static final Object LOCK = new Object();

    private final String version;

    private String username, password, downloadRoot = DEFAULT_DOWNLOAD_ROOT;

    private final Logger logger;

    private final InstallConfig config;

    private final FileDownloader fileDownloader;

    public ComposerInstaller(InstallConfig config, String version, FileDownloader fileDownloader) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.config = config;
        this.version = version;
        this.fileDownloader = fileDownloader;
    }

    public void install() throws InstallationException {
        synchronized (LOCK) {
            logger.info("Installing composer version {}", version);
            try {
                String extension = "phar";
                String downloadUrl = downloadRoot + version + "/composer." + extension;

                CacheDescriptor cacheDescriptor = new CacheDescriptor("composer", version, extension);

                File cachedBinary = config.getCacheResolver().resolve(cacheDescriptor);

                download(downloadUrl, cachedBinary, username, password);

                File destinationDirectory = config.getInstallDirectory();
                File destination = new File(destinationDirectory, "composer.phar");

                if(!destinationDirectory.exists()) {
                    logger.debug("Creating install directory {}", destinationDirectory);
                    destinationDirectory.mkdirs();
                }

                logger.info("Copying composer from {} to {}", cachedBinary, destination);
                FileUtils.copyFile(cachedBinary, destination);
            } catch (DownloadException e) {
                throw new InstallationException("Could not download composer", e);
            } catch (IOException e) {
                throw new InstallationException("Could not install composer", e);
            }
        }
    }

    protected void download(String downloadUrl, File destination, String username, String password)
            throws DownloadException {
        if(!destination.exists()) {
            logger.info("Downloading {} to {}", downloadUrl, destination);
            fileDownloader.download(downloadUrl, destination.getPath(), username, password);
        } else {
            logger.info("Destination file already exists");
        }
    }

    public ComposerInstaller setDownloadRoot(String downloadRoot) {
        this.downloadRoot = downloadRoot;
        return this;
    }

}
