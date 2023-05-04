package net.MediumCraft.ServerJars;

import net.MediumCraft.ServerJars.Providers.*;

import java.util.ArrayList;

public class ServerJars {
    ArrayList<Provider> providers = new ArrayList<Provider>();

    public static void main(String[] args) {
        System.out.println("Welcome to ServerJars");
        // TODO: Loop through all of the providers to find args[0], download & run the jar file.
        // TODO: CHECK THE CHECKSUM BECAUSE IF FILE IS NOT CHANGED IT DOES NOT NEED TO BE DOWNLOADED (one of the issues on the previous version)
    }

}