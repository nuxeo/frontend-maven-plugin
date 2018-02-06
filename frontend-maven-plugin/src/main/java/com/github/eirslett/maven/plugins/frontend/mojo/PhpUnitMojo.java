package com.github.eirslett.maven.plugins.frontend.mojo;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.settings.crypto.SettingsDecrypter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.plexus.build.incremental.BuildContext;

import com.github.eirslett.maven.plugins.frontend.lib.FrontendException;
import com.github.eirslett.maven.plugins.frontend.lib.FrontendPluginFactory;

import java.io.File;

@Mojo(name = "phpunit", defaultPhase = LifecyclePhase.TEST)
public class PhpUnitMojo extends AbstractFrontendMojo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Parameter(property = "session", defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Component
    private BuildContext buildContext;

    @Component(role = SettingsDecrypter.class)
    private SettingsDecrypter decrypter;

    /**
     * phpunit arguments.
     */
    @Parameter(property = "frontend.phpunit.arguments")
    private String arguments;

    @Parameter(property = "frontend.phpunit.path")
    private File phpunitPath;

    @Override
    protected void execute(FrontendPluginFactory factory) throws FrontendException {
        if(phpunitPath == null) {
            phpunitPath = new File("/usr/bin/phpunit");
        }
        factory.getPhpunitRunner(phpunitPath).execute(arguments, null);
    }

    @Override
    protected boolean skipExecution() {
        return false;
    }

}
