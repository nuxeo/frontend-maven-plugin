package com.github.eirslett.maven.plugins.frontend.mojo;

import org.apache.maven.plugin.Mojo;

import java.io.File;

public class ComposerMojoTest extends AbstractMojoTestCase {

    public void testComposerGoal() throws Exception {
        Mojo mojo;

        File testPom = new File(getBasedir(), "target/test-classes/unit/install-composer-goal");
        assertNotNull(testPom);
        assertTrue(testPom.exists());

        mojo = mojoRule.lookupConfiguredMojo(testPom, "install-composer");
        mojoRule.setVariableValueToObject(mojo, "composerVersion", "1.5.2");
        mojo.execute();
    }
}
