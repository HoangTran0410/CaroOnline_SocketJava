/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author nguye
 */
public class FileController {

    File file = null;
    BufferedWriter writer = null;
    Scanner scanner = null;

    public FileController(String file) {
        this.file = new File(file);
    }

    public ArrayList<String> read() {
        ArrayList<String> result = new ArrayList();
        try {
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                result.add(scanner.nextLine());
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        return result;
    }

    public boolean write(ArrayList<String> lines, boolean choice) {
        try {
            writer = new BufferedWriter(new FileWriter(this.file, !choice));
            for (String s : lines) {
                writer.write(s);
                writer.newLine();
                writer.flush();
            }
            writer.close();
        } catch (IOException ex) {
            System.err.println(ex);
            return false;
        }
        return true;
    }

}
