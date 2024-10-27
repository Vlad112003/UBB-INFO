package org.charity.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.charity.Volunteer;
import org.charity.VolunteerRepository;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Objects;
import java.util.Properties;

public class AuthenticationService {
    private static final Logger logger = LogManager.getLogger(AuthenticationService.class);
    private static final String ALGORITHM = "AES";
    private final VolunteerRepository volunteerRepository;
    private final SecretKey secretKey;

    public AuthenticationService(VolunteerRepository volunteerRepository) {
        logger.traceEntry("Starting Authentication Service. Initialize the secret key.");
        this.volunteerRepository = volunteerRepository;
        this.secretKey = loadSecretKey();
        logger.traceExit();
    }

    private static SecretKey loadSecretKey() {
        logger.traceEntry("loading secret key");
        try (InputStream input = AuthenticationService.class.getClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                logger.error("Unable to find app.properties");
                return null;
            }
            Properties prop = new Properties();
            prop.load(input);
            String encodedKey = prop.getProperty("secretKey");
            byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
            logger.traceExit();
            return new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
        } catch (IOException e) {
            logger.error(e);
            return null;
        }
    }

    public String encrypt(String plaintext) {
        logger.traceEntry("Encrypting password");
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
            logger.traceExit();
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            logger.error(e);
            logger.traceExit();
            return null;
        }
    }

    public String decrypt(String encryptedText) {
        logger.traceEntry("Decrypting password.");
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            logger.traceExit();
            return new String(decryptedBytes);
        } catch (Exception e) {
            logger.error(e);
            logger.traceExit();
            return null;
        }
    }

    public boolean login(String username, String password) {
        logger.traceEntry("Entering login for: {}", username);
        String encriptedPass = encrypt(password);
        Volunteer volunteerFromDB = volunteerRepository.findOneByUsername(username);
        if (volunteerFromDB != null && Objects.equals(volunteerFromDB.getPassword(), encriptedPass)){
            logger.traceExit("Login successful for {}", username);
            return true;
        }
        logger.traceExit("Login not successful for {}", username);

        return false;
    }

    private void logout(Volunteer user) {
    }
}
