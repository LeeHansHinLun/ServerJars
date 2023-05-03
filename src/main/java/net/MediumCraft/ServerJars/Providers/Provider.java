package net.MediumCraft.ServerJars.Providers;

import org.jetbrains.annotations.Nullable;

import java.io.File;

public abstract class Provider {
    public abstract String getApiId();
    public abstract String getApiAddress();

    public abstract String getDownloadLink(String type, String name, @Nullable String version);

    public abstract File downloadFile(String type, String category, @Nullable String version);

}
