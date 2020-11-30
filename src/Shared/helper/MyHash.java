/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared.helper;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class MyHash {

    // https://www.baeldung.com/java-password-hashing#2-implementing-pbkdf2-in-java
    // hàm này mỗi lần hash_sha_256 lại ra 1 chuỗi khác nhau, và không hiển thị trong console được, khó hiểu
    // => Không dùng
    public static String hash_WTF(String str) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            KeySpec spec = new PBEKeySpec(str.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] hash = factory.generateSecret(spec).getEncoded();

            return new String(hash);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(MyHash.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    // https://stackoverflow.com/a/5531479
    // hàm này dùng sha256 nhưng cũng không hiển thị trong console được
    public static String hash_sha_256(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));

            return new String(hash);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MyHash.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    // https://www.geeksforgeeks.org/md5-hash-in-java/
    // hàm này dùng md5
    public static String hash(String str) {
        try {
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(str.getBytes());

            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value 
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
            
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }

        return "";
    }

}
