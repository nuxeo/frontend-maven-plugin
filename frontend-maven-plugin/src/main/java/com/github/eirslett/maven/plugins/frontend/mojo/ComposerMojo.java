package com.github.eirslett.maven.plugins.frontend.mojo;

import com.github.eirslett.maven.plugins.frontend.lib.ComposerInstaller;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.settings.crypto.SettingsDecrypter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eirslett.maven.plugins.frontend.lib.FrontendException;
import com.github.eirslett.maven.plugins.frontend.lib.FrontendPluginFactory;
import com.github.eirslett.maven.plugins.frontend.lib.ProxyConfig;
import org.sonatype.plexus.build.incremental.BuildContext;

import java.io.File;
import java.util.Collections;

@Mojo(name = "composer", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class ComposerMojo extends AbstractFrontendMojo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Parameter(property = "session", defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Component
    private BuildContext buildContext;

    @Component(role = SettingsDecrypter.class)
    private SettingsDecrypter decrypter;

    /**
     * composer arguments. Default is "install".
     */
    @Parameter(defaultValue = "install", property = "frontend.composer.arguments")
    private String arguments;

    @Parameter(property = "frontend.composer.installDirectory")
    private File composerInstallDirectory;

    @Parameter(property = "frontend.composer.composerInheritsProxyConfigFromMaven", defaultValue = "true")
    private boolean composerInheritsProxyConfigFromMaven;

    @Override
    protected void execute(FrontendPluginFactory factory) throws FrontendException {
        if(composerInstallDirectory == null) {
            composerInstallDirectory = new File(workingDirectory, ComposerInstaller.INSTALL_PATH);
        }
        File composerJson = new File(workingDirectory, "composer.json");
        if (buildContext == null || buildContext.hasDelta(composerJson) || !buildContext.isIncremental()) {
            ProxyConfig proxyConfig = getProxyConfig();
            factory.getComposerRunner(composerInstallDirectory).execute(arguments, null);
        } else {
            getLog().info("Skipping npm install as package.json unchanged");
        }
    }

    private ProxyConfig getProxyConfig() {
        if (composerInheritsProxyConfigFromMaven) {
            return MojoUtils.getProxyConfig(session, decrypter);
        } else {
            getLog().info("composer not inheriting proxy config from Maven");
            return new ProxyConfig(Collections.<ProxyConfig.Proxy>emptyList());
        }
    }

    @Override
    protected boolean skipExecution() {
        return false;
    }

}
