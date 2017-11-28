package com.github.eirslett.maven.plugins.frontend.mojo;

import com.github.eirslett.maven.plugins.frontend.mojo.AbstractMojoTestCase;
import org.apache.maven.plugin.Mojo;

public class ComposerMojoTest extends AbstractMojoTestCase {

    public void testComposerGoal() throws Exception {
        Mojo mojo = lookupConfiguredMojo(project, "install-composer");
        assertNotNull(mojo);

        mojo.execute();
    }
}
