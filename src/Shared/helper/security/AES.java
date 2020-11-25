/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared.helper.security;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
// https://teamvietdev.com/ma-hoa-va-giai-ma-aes-trong-java/
public class AES {

    String secretKey;

    public AES() {
        secretKey = UUID.randomUUID().toString();
    }

    public AES(String secretKey) {
        this.secretKey = secretKey;
    }

    public String encrypt(String strToEncrypt) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] key = secretKey.getBytes("UTF-8");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);

            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return strToEncrypt;
    }

    public String decrypt(String strToDecrypt) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] key = secretKey.getBytes("UTF-8");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);

            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return strToDecrypt;
    }

    public static void main(String[] args) {
        String secretKey = UUID.randomUUID().toString();
        String originalString = "teamvietdev.com";

        AES testAES = new AES(secretKey);

        System.out.println("SecretKey: " + secretKey);

        String encryptedString = testAES.encrypt(originalString);
        System.out.println("Encrypt: " + encryptedString);

        String decryptedString = testAES.decrypt(encryptedString);
        System.out.println("Decrypt: " + decryptedString);
    }
}
