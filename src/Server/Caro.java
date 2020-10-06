/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.dataFilesConnect;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class Caro {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String file = "src/Server/data/Player.txt";
        ArrayList testdt = new ArrayList();
        testdt.add("id1\tname1\tname2");
        testdt.add("id2\thahahuhu\tname2");
        testdt.add("id3\tname1\thehehe");
        testdt.add("id4\tname1\tname2");
        dataFilesConnect conn = new dataFilesConnect(file);
        conn.reWrite(testdt,true);
        System.out.println(conn.read());
        
    }
    
}
