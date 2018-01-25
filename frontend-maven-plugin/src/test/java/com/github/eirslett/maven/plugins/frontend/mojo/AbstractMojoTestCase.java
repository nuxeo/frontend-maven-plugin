package com.github.eirslett.maven.plugins.frontend.mojo;

import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;


public abstract class AbstractMojoTestCase extends org.apache.maven.plugin.testing.AbstractMojoTestCase {

    @Rule
    protected MojoRule mojoRule;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mojoRule = new MojoRule(this);
    }
}
