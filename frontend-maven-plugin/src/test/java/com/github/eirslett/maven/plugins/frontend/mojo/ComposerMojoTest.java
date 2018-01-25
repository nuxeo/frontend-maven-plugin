package com.github.eirslett.maven.plugins.frontend.mojo;

import org.apache.maven.plugin.Mojo;
import org.junit.Test;

import java.io.File;

public class ComposerMojoTest extends AbstractMojoTestCase {

    private static final String COMPOSER_VERSION = "1.5.2";

    public void testInstallComposer() throws Exception {
        Mojo mojo;

        File parentPom = new File(getBasedir(), "target/test-classes/unit/composer");
        assertNotNull(parentPom);
        assertTrue(parentPom.exists());

        mojo = mojoRule.lookupConfiguredMojo(parentPom, "install-composer");
        mojoRule.setVariableValueToObject(mojo, "composerVersion", COMPOSER_VERSION);
        mojo.execute();

    }

    @Test
    public void testComposerInstall() throws Exception {
        Mojo mojo;

        File pom = new File(getBasedir(), "target/test-classes/unit/composer/install");

        mojo = mojoRule.lookupConfiguredMojo(pom, "install-composer");
        mojoRule.setVariableValueToObject(mojo, "composerVersion", COMPOSER_VERSION);
        mojo.execute();

        mojo = mojoRule.lookupConfiguredMojo(pom, "composer");
        mojoRule.setVariableValueToObject(mojo, "installDirectory", new File("/usr/bin"));
        mojo.execute();
    }
}
