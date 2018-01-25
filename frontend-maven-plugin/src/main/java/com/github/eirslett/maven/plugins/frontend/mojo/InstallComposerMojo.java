package com.github.eirslett.maven.plugins.frontend.mojo;

import com.github.eirslett.maven.plugins.frontend.lib.*;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.settings.crypto.SettingsDecrypter;
import org.eclipse.aether.RepositorySystemSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Mojo(name = "install-composer", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class InstallComposerMojo extends AbstractMojo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Parameter(property = "session", defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Component(role = SettingsDecrypter.class)
    private SettingsDecrypter decrypter;

    /**
     * The base directory for running all Node commands. (Usually the directory that contains package.json)
     */
    @Parameter(defaultValue = "${basedir}", property = "workingDirectory", required = false)
    protected File workingDirectory;

    /**
     * The base directory for installing node and npm.
     */
    @Parameter(property = "installDirectory", required = false)
    protected File installDirectory;

    @Parameter(defaultValue = "${repositorySystemSession}", readonly = true)
    private RepositorySystemSession repositorySystemSession;

    @Parameter()
    protected CacheResolver cacheResolver;

    /**
     * The version of Composer to install.
     */
    @Parameter(property = "composerVersion", required = true)
    private String composerVersion;

    @Override
    public void execute() {
        ProxyConfig proxyConfig = MojoUtils.getProxyConfig(session, decrypter);
        if (installDirectory == null) {
            installDirectory = new File(workingDirectory, ComposerInstaller.INSTALL_PATH);
        }
        if(cacheResolver == null) {
            cacheResolver = new RepositoryCacheResolver(repositorySystemSession);
        }

        try {
            new FrontendPluginFactory(workingDirectory, installDirectory, cacheResolver).getComposerInstaller(composerVersion, proxyConfig).install();
        } catch (InstallationException e) {
            e.printStackTrace();
        }
    }

}
