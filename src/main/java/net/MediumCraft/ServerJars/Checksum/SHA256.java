package net.MediumCraft.ServerJars.Checksum;

import net.MediumCraft.ServerJars.Logging.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    public static String checkSHA256(File file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            FileInputStream inputStream = new FileInputStream(file);

            byte[] byteArray = new byte[1024];
            int bytesCount = 0;

            while ((bytesCount = inputStream.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }

            inputStream.close();

            byte[] bytes = digest.digest();

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                builder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            return builder.toString();
        } catch (NoSuchAlgorithmException exception) {
            Logger.error("NoSuchAlgorithmException: Issue while checking SHA256 hash of server file!");
            exception.printStackTrace();
        } catch (IOException exception) {
            Logger.error("IOException: Issue while checking SHA256 hash of server file!");
            exception.printStackTrace();
        }

        return null;
    }

}
