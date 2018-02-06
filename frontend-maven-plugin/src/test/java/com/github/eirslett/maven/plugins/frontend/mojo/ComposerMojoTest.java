package com.github.eirslett.maven.plugins.frontend.mojo;

import org.apache.maven.plugin.Mojo;
import org.junit.Test;

import java.io.File;

public class ComposerMojoTest extends AbstractMojoTestCase {

    private static final String COMPOSER_VERSION = "1.5.2";

    public void testInstallComposer() throws Exception {
        File parentPom = new File(getBasedir(), "target/test-classes/unit");
        assertNotNull(parentPom);
        assertTrue(parentPom.exists());

        Mojo mojo = mojoRule.lookupConfiguredMojo(parentPom, "install-composer");
        mojoRule.setVariableValueToObject(mojo, "composerVersion", COMPOSER_VERSION);
        mojo.execute();

    }

    public void testComposerInstall() throws Exception {
        File pom = new File(getBasedir(), "target/test-classes/unit/composer/install");

        Mojo mojo = mojoRule.lookupConfiguredMojo(pom, "install-composer");
        mojoRule.setVariableValueToObject(mojo, "composerVersion", COMPOSER_VERSION);
        mojo.execute();

        mojo = mojoRule.lookupConfiguredMojo(pom, "composer");
        mojoRule.setVariableValueToObject(mojo, "installDirectory", new File("/usr/bin"));
        mojo.execute();
    }

}
