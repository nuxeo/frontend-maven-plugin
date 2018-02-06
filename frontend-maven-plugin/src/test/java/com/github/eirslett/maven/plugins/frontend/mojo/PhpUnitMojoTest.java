package com.github.eirslett.maven.plugins.frontend.mojo;

import org.apache.maven.plugin.Mojo;

import java.io.File;

public class PhpUnitMojoTest extends AbstractMojoTestCase {

    public void testPhpUnitGoal() throws Exception {
        File pom = new File(getBasedir(), "target/test-classes/unit/composer/install");

        Mojo mojo = mojoRule.lookupConfiguredMojo(pom, "phpunit");
        mojoRule.setVariableValueToObject(mojo, "installDirectory", new File("/usr/bin"));
        mojoRule.setVariableValueToObject(mojo, "arguments", "--version");
        mojo.execute();
    }
}
