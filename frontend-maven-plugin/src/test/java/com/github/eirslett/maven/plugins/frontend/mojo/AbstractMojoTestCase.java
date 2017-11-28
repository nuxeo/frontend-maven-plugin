package com.github.eirslett.maven.plugins.frontend.mojo;

import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.project.MavenProject;
import org.junit.Rule;

import java.io.File;

public abstract class AbstractMojoTestCase extends org.apache.maven.plugin.testing.AbstractMojoTestCase {

    @Rule
    protected MojoRule mojoRule = new MojoRule();
    protected MavenProject project;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        File testPom = new File(getBasedir(), "target/test-classes/unit/phpunit-goal");
        assertNotNull(testPom);
        assertTrue(testPom.exists());

        project = mojoRule.readMavenProject(testPom);
    }
}
