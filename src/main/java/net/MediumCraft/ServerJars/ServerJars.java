package net.MediumCraft.ServerJars;

import net.MediumCraft.ServerJars.Checksum.SHA256;
import net.MediumCraft.ServerJars.Logging.Logger;
import net.MediumCraft.ServerJars.Providers.*;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;

public class ServerJars {
    static ArrayList<Provider> providers = getProviders();

    public static void main(String[] args) {
        Logger.log("Welcome to ServerJars");
        // TODO: Loop through all of the providers to find args[0], download & run the jar file.
        // TODO: CHECK THE CHECKSUM BECAUSE IF FILE IS NOT CHANGED IT DOES NOT NEED TO BE DOWNLOADED (one of the issues on the previous version)

        String softwareName = null;
        try {
            softwareName = args[0];
        } catch (IndexOutOfBoundsException exception) {
            Logger.error("An argument is required for software. Please check your command.");
            Logger.error("Command Example: java -jar ServerJars.jar softwareName [type] [minecraft version]");

            // Quit as we have nothing else to do
            System.exit(0);
        }

        @Nullable String type = args[1];
        @Nullable String version = args[2];

        for (Provider provider : providers) {
            if (!(provider.getSoftwareId() == softwareName)) {
                continue;
            } else {
                Logger.log("Loaded " + softwareName);
                provider.downloadFile(type, softwareName, version);
                File serverFile = new File("./server.jar");
                String sha256 = SHA256.checkSHA256(serverFile);
                if (sha256 != provider.getHash(type, softwareName, version)) {

                }
            }
        }
    }

    public static ArrayList<Provider> getProviders() {
        ArrayList<Provider> providers = new ArrayList<Provider>();

        // Initialize your provider into a variable
        PaperMC paperMC = new PaperMC();

        // Add your initialized instance here
        providers.add(paperMC);

        return providers;
    }

}