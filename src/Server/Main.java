/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String file = "src/Server/data/Player.txt";
        
        ArrayList testdt = new ArrayList();
        testdt.add("id1;name1;name2");
        testdt.add("id2;hahahuhu;name2");
        testdt.add("id3;name1;hehehe");
        testdt.add("id4;name1;name2");
        
        FileController conn = new FileController(file);
        conn.write(testdt,true);
        System.out.println(conn.read());
        
    }
    
}
