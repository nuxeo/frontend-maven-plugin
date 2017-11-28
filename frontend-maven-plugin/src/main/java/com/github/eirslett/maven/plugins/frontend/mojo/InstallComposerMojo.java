package com.github.eirslett.maven.plugins.frontend.mojo;

import com.github.eirslett.maven.plugins.frontend.lib.FrontendException;
import com.github.eirslett.maven.plugins.frontend.lib.FrontendPluginFactory;
import com.github.eirslett.maven.plugins.frontend.lib.ProxyConfig;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.settings.crypto.SettingsDecrypter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mojo(name = "install-composer", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class InstallComposerMojo extends AbstractFrontendMojo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Parameter(property = "session", defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Component(role = SettingsDecrypter.class)
    private SettingsDecrypter decrypter;

    @Override
    protected void execute(FrontendPluginFactory factory) throws FrontendException {
        ProxyConfig proxyConfig = MojoUtils.getProxyConfig(session, decrypter);

        if(!(skipExecution())) {
            factory.getComposerInstaller(proxyConfig).install();
        } else {
            getLog().info("Skipping execution.");
        }
    }

    @Override
    protected boolean skipExecution() {
        return false;
    }

}
