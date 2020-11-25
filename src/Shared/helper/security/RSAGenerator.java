/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared.helper.security;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
// https://stackjava.com/demo/code-java-vi-du-ma-hoa-giai-ma-voi-rsa.html
public class RSAGenerator {

    public static final String PUBLIC_KEY_FILE = "rsa_keypair/publicKey";
    public static final String PRIVATE_KEY_FILE = "rsa_keypair/privateKey";
    public static String basePath = "";

    private KeyPairGenerator keyGen;
    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public RSAGenerator(int keylength, String basePath) {
        try {
            this.basePath = basePath;
            this.keyGen = KeyPairGenerator.getInstance("RSA");
            this.keyGen.initialize(keylength);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createKeys() {
        this.pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public void writeToFile(String path, byte[] key) throws IOException {
        try {
            File f = new File(path);
            f.getParentFile().mkdirs();
            
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(key);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void generateKeysToFile() {
        try {
            System.out.println("Starting generate...");
            this.createKeys();
            this.writeToFile(basePath + PUBLIC_KEY_FILE, this.getPublicKey().getEncoded());
            this.writeToFile(basePath + PRIVATE_KEY_FILE, this.getPrivateKey().getEncoded());
            System.out.println("Generated to '" + basePath + "' !");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new RSAGenerator(1024, "src/Server/").generateKeysToFile();
    }
}
