/**
 * Provider
 * Abstract Class
 *
 * This class is for adding new providers, all providers should always
 * be extended from this Provider abstract class.
 */

package net.MediumCraft.ServerJars.Providers;

import org.jetbrains.annotations.Nullable;

import java.io.File;

public abstract class Provider {
    /**
     * The API id is used to identify which api to use
     * @return API ID
     */
    public abstract String getApiId();

    /**
     * The API address, example: "https://api.myapi.com/"
     * Recommended to leave a slash behind and use https
     * @return API Address
     */
    public abstract String getApiAddress();

    /**
     * The software ID, not required but recommended
     * @return Software ID
     */
    public abstract String getSoftwareId();

    /**
     *
     * @param type Type of software, example: server, proxy, nullable as it is not always required
     * @param name Important, the name of the software, usually used in API urls
     * @param version Minecraft version, not required but recommended
     * @return Download Link
     */
    public abstract String getDownloadLink(@Nullable String type, String name, @Nullable String version);

    /**
     * Download the server JAR file
     *
     * @param type Type of software, example: server, proxy, nullable as it is not always required
     * @param name Important, the name of the software, usually used in API urls
     * @param version Minecraft version, not required but recommended
     * @return A file object to the downloaded file
     */
    public abstract File downloadFile(@Nullable String type, String name, @Nullable String version);

    /**
     * Beta Feature, Recommended to not use
     * Gets hash of the file
     * @param type Type of software, example: server, proxy, nullable as it is not always required
     * @param name Important, the name of the software, usually used in API urls
     * @param version Minecraft version, not required but recommended
     * @return
     */
    public abstract String getHash(@Nullable String type, String name, @Nullable String version, int build);

    /**
     * Get latest build ID
     * @return Build ID in integer
     */
    public abstract int getLatestBuild();
    /**
     * Get latest version
     * @return Version in String
     */
    public abstract String getLatestVersion();
}
