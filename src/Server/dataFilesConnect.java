/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguye
 */
public class dataFilesConnect {

    File file = null;
    BufferedWriter writer = null;
    Scanner scanner = null;

    public dataFilesConnect(String file) {
            this.file = new File(file);
    }
    
    public ArrayList read(){
        ArrayList result = new ArrayList();
        try {
            scanner = new Scanner(file);
            while(scanner.hasNext()){
                result.add(scanner.nextLine());
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        return result;
    }

    public boolean reWrite(ArrayList<String> lines, boolean choice) {
        try {
            writer = new BufferedWriter(new FileWriter(this.file, !choice));
            for (String s : lines) {
                try {
                    writer.write(s);
                    writer.newLine();
                    writer.flush();
                } catch (IOException ex) {
                    System.err.println("Error writing " + s + "\n" + ex);
                    return false;
                }
            }
            writer.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return true;
    }

}
