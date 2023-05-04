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
     *
     * @param type Type of software, example: server, proxy, nullable as it is not always required
     * @param name Important, the name of the software, usually used in API urls
     * @param version Minecraft version, not required but recommended
     * @return A file object to the downloaded file
     */
    public abstract File downloadFile(String type, String name, @Nullable String version);

}
