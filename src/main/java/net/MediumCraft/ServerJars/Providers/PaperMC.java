package net.MediumCraft.ServerJars.Providers;

import com.fasterxml.jackson.databind.*;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PaperMC extends Provider {

    public static void main(String[] args) {
        PaperMC instance = new PaperMC();
        instance.getDownloadLink("server", "paper", null);
    }
    public String getApiId() {
        // PaperMC
        // Used for API usually, DO NOT CHANGE
        return "paper";
    }

    public String getApiAddress() {
        return "https://api.papermc.io/";
    }

    public String getDownloadLink(String type, String name, @Nullable String version) {
        try {
            String url = getApiAddress() + String.format("v2/projects/%s", getApiId());

            // Get JSON response from API
            /**ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(new URL(url));

            // Get list of available versions
            List<String> versions = Collections.emptyList();
            JsonNode versionsNode = json.path("versions");
            if (versionsNode.isArray()) {
                versions = mapper.convertValue(versionsNode, List.class);
            }
            **/
            if (version == null) {
                // TODO: Find latest version and build

                // Find latest version
                ArrayList<String> versions = getAllVersions();

                String latestVersion = versions.get((versions.size() - 1));
                System.out.println("[Debug] Latest PaperMC Version: " + latestVersion);
            } else {
                // TODO: Find latest build
            }

            // TODO: Generate download link
        } catch (IOException error) {
            // Error handling
            System.err.println("[ServerJars] IOException: There appears to be some issues with connection to the API or the JSON processing.");
            System.err.println("[ServerJars] There are a few steps to try and fix:");
            System.err.println("[ServerJars] 1. Check your internet connection");
            System.err.println("[ServerJars] 2. Update your Java version");
            System.err.println("[ServerJars] If you cannot fix it. It probably isn't your fault. Please report the bug.");
            System.err.println("[ServerJars] Error Logs for Debug:");
            error.printStackTrace();
        }

        return null;
    }

    public File downloadFile(String type, String category, @Nullable String version) {
        // TODO: Download the server file into server.jar
        return null;
    }

    public ArrayList<String> getAllVersions() throws IOException {
        ArrayList<String> versions = new ArrayList<String>();
        URL url = new URL(getApiAddress() + String.format("v2/projects/%s", getApiId()));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(url);
        JsonNode versionsNode = rootNode.path("versions");
        for (JsonNode versionNode : versionsNode) {
            String version = versionNode.asText();
            versions.add(version);
        }
        return versions;
    }
}
