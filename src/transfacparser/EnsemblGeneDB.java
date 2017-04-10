/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfacparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bharata
 */
public class EnsemblGeneDB {
    private static Map<String,String> geneDB;
    
    public static Map ReadGeneDB(String filename){
        return ReadGeneDB(filename, ";");
    }
    
    
    public static Map ReadGeneDB(String filename,String sep){
        BufferedReader br = null;
        FileReader fr = null;
        geneDB = new HashMap<>();
        try {
            
            fr = new FileReader(new File(filename));
            br = new BufferedReader(fr);
            String line = "";
            
            while ((line = br.readLine()) != null) {
                String[] parsed = line.split(sep);
                geneDB.put(parsed[1], parsed[0]);
            }
            
            
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            
        }finally{
            try {
                fr.close();
                br.close();
            } catch (IOException ex) {
                
            }
            
        }
        return geneDB;
    }
    
}
