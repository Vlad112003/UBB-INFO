package lab13.iss1.Repository.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {
    public static String getHash(String input)  {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] data = digest.digest(input.getBytes());

        // Convert the byte array to a hexadecimal string
        StringBuilder builder = new StringBuilder();
        for (byte b : data) {
            builder.append(String.format("%02x", b));
        }

        return builder.toString();
    }
}
