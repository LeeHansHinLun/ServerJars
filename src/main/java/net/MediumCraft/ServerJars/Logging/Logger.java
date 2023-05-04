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

    public static void error(String logContent) {
        if (prefix.length() == 0) {
            System.err.println(logContent);
        } else {
            System.err.printf("[%s] %s%n", prefix, logContent);
        }
    }

}
