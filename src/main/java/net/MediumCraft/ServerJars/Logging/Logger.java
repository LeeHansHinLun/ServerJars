package net.MediumCraft.ServerJars.Logging;

/**
 * Customized Logger for ServerJars
 */

public class Logger {

    static String prefix = "ServerJars";

    public static void log(String logContent) {
        if (prefix.length() == 0) {
            System.out.println(logContent);
        } else {
            System.out.printf("[%s] %s%n", prefix, logContent);
        }
    }

}
