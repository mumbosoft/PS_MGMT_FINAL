/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hejsan
 */
public class Excel {
    
    public static void main(String[] args) {
        ArrayList ja = new ArrayList();
        ja.add("hej");
        ja.add("hej");
        ja.add("da");
        ja.add("da");
        ja.add("re");
        ja.add("re");
        //ExportToExcel(ja);
    }
    
    public static void ExportToExcel(ArrayList temp, String path){
        try { 
            PrintWriter hej = new PrintWriter(path);
            hej.print("hej"+"   "+";"+" "+"da"+ "\n");
            hej.flush();
            for(int i = 0; i<temp.size(); i++){
                hej.print(temp.get(i)+"   "+";"+" "+temp.get(i)+ "\n");
                hej.flush();
            }
            hej.close();
                    } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
