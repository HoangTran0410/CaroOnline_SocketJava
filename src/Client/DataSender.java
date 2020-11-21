/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author nguye
 */
public class DataSender {
    Socket s;
    DataInputStream dis;
    DataOutputStream dos;

    public DataSender() {
        try {
            s = new Socket("localhost", 5056);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(DataSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DataSender(Socket s) {
        try {
            this.s = s;
            this.dis = new DataInputStream(s.getInputStream());
            this.dos = new DataOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(DataSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DataSender(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }
    
    
    
    public boolean sendData(JSONObject sjson){
        try {
            dos.writeUTF(sjson.toString());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(DataSender.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
}
