package com.github.eirslett.maven.plugins.frontend.lib;

import java.io.File;

/**
 * @author Pierre-Gildas MILLON <pgmillon@gmail.com>
 */
public class PhpExecutorConfig {

    private final InstallConfig installConfig;

    public PhpExecutorConfig(InstallConfig installConfig) {
        this.installConfig = installConfig;
    }

    public File getWorkingDirectory() {
        return installConfig.getWorkingDirectory();
    }

    public File getPhpPath() {
        return new File(installConfig.getInstallDirectory() + "/php");
    }
}
