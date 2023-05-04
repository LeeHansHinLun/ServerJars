package net.MediumCraft.ServerJars;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.MediumCraft.ServerJars.Checksum.SHA256;
import net.MediumCraft.ServerJars.Logging.Logger;
import net.MediumCraft.ServerJars.Providers.*;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class ServerJars {
    static ArrayList<Provider> providers = getProviders();

    public static void main(String[] args) {
        Logger.log("Welcome to ServerJars");
        // TODO: Loop through all of the providers to find args[0], download & run the jar file.
        // TODO: CHECK THE CHECKSUM BECAUSE IF FILE IS NOT CHANGED IT DOES NOT NEED TO BE DOWNLOADED (one of the issues on the previous version)

        Properties configuration = new Properties();
        boolean firstRun = false;

        try {
            File configurationDirectory = new File("./ServerJars/");
            File configurationFile = new File("./ServerJars/config.properties");

            if (!configurationFile.exists()) {
                if (!configurationDirectory.mkdirs()) {
                    Logger.log("Configuration directory already exists!");
                }

                configurationFile.createNewFile();
                firstRun = true;

            }

            configuration.load(new FileInputStream("./ServerJars/config.properties"));

            if (firstRun) {
                configuration.setProperty("software", "paper");
                configuration.setProperty("type", "server");
                configuration.setProperty("version", "");
                configuration.setProperty("unsafeMode", "false");
            }

        } catch (IOException e) {
            Logger.error("RuntimeException while setting up configuration file!");
            throw new RuntimeException(e);
        }

        String softwareName = configuration.getProperty("software");
        String type = configuration.getProperty("type");
        @Nullable String version = configuration.getProperty("version");
        String unsafeMode = configuration.getProperty("unsafeMode");

        for (Provider provider : providers) {
            if (!(provider.getSoftwareId() == softwareName)) {
                continue;
            } else {
                Logger.log("Loaded " + softwareName);
                File serverJar = new File("./server.jar");
                if (serverJar.exists()) {
                    String localFileChecksum = SHA256.checkSHA256(serverJar);
                    String remoteChecksum = provider.getHash(type, softwareName, version, provider.getBuild());

                    if (localFileChecksum != remoteChecksum) {
                        Logger.log("Outdated local file! Automatically updating!");
                        serverJar.delete();
                        File newServerJar = provider.downloadFile(type, softwareName, version);

                        // Redo Checksum
                        String newLocalFileChecksum = SHA256.checkSHA256(newServerJar);
                        if (newLocalFileChecksum != remoteChecksum) {
                            // Corrupted File Processing
                            if (!(unsafeMode == "true")) {
                                Logger.error("Incorrect Checksum! File is corrupted or altered!");
                                Logger.error("ServerJars will not run a file that does not match checksum unless it is in unsafe mode!");
                                Logger.error("Please re-run to make sure there is no issues");
                            } else {
                                // Unsafe mode, fine I'll run
                                break;
                            }
                        }
                    }
                } else {
                    provider.downloadFile(type, softwareName, version);

                    // Checksum
                    String remoteChecksum = provider.getHash(type, softwareName, version, provider.getBuild());
                    String localChecksum = SHA256.checkSHA256(serverJar);

                    if (localChecksum != remoteChecksum) {
                        if (!(unsafeMode == "true")) {
                            Logger.error("Incorrect Checksum! File is corrupted or altered!");
                            Logger.error("ServerJars will not run a file that does not match checksum unless it is in unsafe mode!");
                            Logger.error("Please re-run to make sure there is no issues");
                        } else {
                            break;
                        }
                    }
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