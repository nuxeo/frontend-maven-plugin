package com.github.eirslett.maven.plugins.frontend.mojo;

import com.github.eirslett.maven.plugins.frontend.lib.CacheDescriptor;
import com.github.eirslett.maven.plugins.frontend.lib.CacheResolver;

import java.io.File;

public class TestCacheResolver implements CacheResolver {

    private File destination;

    @Override
    public File resolve(CacheDescriptor cacheDescriptor) {
        return destination.toPath().resolve(cacheDescriptor.getName() + "." + cacheDescriptor.getExtension()).toFile();
    }

    public File getDestination() {
        return destination;
    }

    public TestCacheResolver setDestination(File destination) {
        this.destination = destination;
        return this;
    }
}
