package net.MediumCraft.ServerJars.Providers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.MediumCraft.ServerJars.Logging.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class PaperMC extends Provider {

    public static void main(String[] args) {
        PaperMC instance = new PaperMC();
        instance.getDownloadLink("server", "velocity", null);
    }
    public String getApiId() {
        return "paper";
    }

    public String getApiAddress() {
        return "https://api.papermc.io/";
    }

    public String getSoftwareId() {
        return "paper";
    }

    public String getDownloadLink(String type, String name, @Nullable String version) {
        try {
            // Define build ID
            int build = 0;

            if (version == null) {
                // Find the latest version
                ArrayList<String> versions = getAllVersions(name);
                version = versions.get((versions.size() - 1));


                // Find the latest build
                ArrayList<String> builds = getAllBuilds(version, name);
                build = Integer.parseInt(builds.get((builds.size() - 1)));
            } else {
                // Find the latest build
                ArrayList<String> builds = getAllBuilds(version, name);
                build = Integer.parseInt(builds.get((builds.size() - 1)));
            }

            String fileName = getFileName(version, build, name);
            String sha256 = getSha256(version, build, name);

            String downloadUrl = getApiAddress() + String.format("v2/projects/%s/versions/%s/builds/%s/downloads/%s", name, version, build, fileName);
            System.out.println("[ServerJars] Created Download Link!");
            Logger.log("SHA256 Checksum: " + sha256);
            return downloadUrl;

        } catch (IOException error) {
            // Error handling
            // I suck at it, don't judge me
            System.err.println("[ServerJars] IOException: There appears to be some issues with connection to the API or the JSON processing.");
            System.err.println("[ServerJars] There are a few steps to try and fix:");
            System.err.println("[ServerJars] 1. Check your internet connection");
            System.err.println("[ServerJars] 2. Update your Java version");
            System.err.println("[ServerJars] If you cannot fix it. It probably isn't your fault. Please report the bug.");
            System.err.println("[ServerJars] Error Logs for Debug:");
            error.printStackTrace();

            return null;
        }
    }

    public ArrayList<String> getAllBuilds(String version, String softwareName) throws IOException {
        ArrayList<String> builds = new ArrayList<String>();
        URL url = new URL(getApiAddress() + String.format("v2/projects/%s/versions/%s/builds", softwareName, version));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(url);
        JsonNode buildsNode = rootNode.path("builds");
        for (JsonNode buildNode : buildsNode) {
            String build = buildNode.get("build").asText();
            builds.add(build);
        }
        return builds;
    }

    public File downloadFile(String type, String name, @Nullable String version) {
        // TODO: Download the server file into server.jar
        return null;
    }

    public ArrayList<String> getAllVersions(String softwareName) throws IOException {
        ArrayList<String> versions = new ArrayList<String>();
        URL url = new URL(getApiAddress() + String.format("v2/projects/%s", softwareName));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(url);
        JsonNode versionsNode = rootNode.path("versions");
        for (JsonNode versionNode : versionsNode) {
            String version = versionNode.asText();
            versions.add(version);
        }
        return versions;
    }

    public String getFileName(String version, int build, String softwareName) throws IOException {
        URL url = new URL(getApiAddress() + String.format("v2/projects/%s/versions/%s/builds/%d", softwareName, version, build));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(url);
        JsonNode downloadsNode = rootNode.path("downloads");
        JsonNode applicationNode = downloadsNode.path("application");
        return applicationNode.path("name").asText();
    }

    public String getSha256(String version, int build, String softwareName) throws IOException {
        URL url = new URL(getApiAddress() + String.format("v2/projects/%s/versions/%s/builds/%d", softwareName, version, build));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(url);
        JsonNode downloadsNode = rootNode.path("downloads");
        JsonNode applicationNode = downloadsNode.path("application");
        return applicationNode.path("sha256").asText();
    }

}
