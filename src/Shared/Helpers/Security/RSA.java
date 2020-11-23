/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared.Helpers.Security;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
// https://stackjava.com/demo/code-java-vi-du-ma-hoa-giai-ma-voi-rsa.html
public class RSA {

    PublicKey publicKey;
    PrivateKey privateKey;

    public RSA preparePublicKey(String publicKeyPath) {
        try {
            publicKey = getPublicKeyFromFile(publicKeyPath);
        } catch (Exception ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    public RSA preparePrivateKey(String privateKeyPath) {
        try {
            privateKey = getPrivateKeyFromFile(privateKeyPath);
        } catch (Exception ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    public PublicKey getPublicKeyFromFile(String filePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filePath).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public PrivateKey getPrivateKeyFromFile(String filePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filePath).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public String encrypt(String original) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] byteEncrypted = cipher.doFinal(original.getBytes());
            String encrypted = Base64.getEncoder().encodeToString(byteEncrypted);

            return encrypted;

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    public String decrypt(String encrypted) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] byteDecrypted = cipher.doFinal(encrypted.getBytes());
            String decrypted = new String(byteDecrypted);

            return decrypted;

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    public static void main(String[] args) throws Exception {
        RSA serverSide = new RSA()
                .preparePrivateKey("src/Server/rsa_keypair/privateKey")
                .preparePublicKey("src/Server/rsa_keypair/publicKey");

        RSA clientSide = new RSA()
                .preparePublicKey("src/Server/rsa_keypair/publicKey");

        String original = "stackjava.com";
        System.out.println("Original: " + original);

        String encrypted = serverSide.encrypt(original);
        System.out.println("Encrypted: " + encrypted);

        String decrypted = serverSide.decrypt(encrypted);
        System.out.println("Decrypted: " + decrypted);

        // Lỗi javax.crypto.IllegalBlockSizeException: Data must not be longer than 256 bytes         
        // => hay: https://stackoverflow.com/a/46828430
    }
}
