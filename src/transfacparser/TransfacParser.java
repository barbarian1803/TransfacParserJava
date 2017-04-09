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
import java.util.Arrays;

/**
 *
 * @author Bharata
 */
public class TransfacParser {

    public static void main(String[] args) {
        String fileName = args[0];
        TransfacFile file = TransfacParser.parseTransfacData(fileName);
    }
    
    private static TransfacFile parseTransfacData(String fileName){
        TransfacFile file = null;
        //read file into stream, try-with-resources
        FileReader fr = null;
        BufferedReader br=null;
        
        try {
            fr = new FileReader(new File(fileName));
            br = new BufferedReader(fr);
            String line;
            TransfacMatrix currMatrix = null;
            boolean startMatrix = false;
            while ((line = br.readLine()) != null) {
                String code = line.substring(0, 2);
                String value = "";
                if(line.length()>2){
                    value = line.substring(4);
                }
                
                if(startMatrix){
                    String[]values = line.split("[\\s]+");
                    currMatrix.insertPWMValue(Arrays.copyOfRange(values, 1, 5));
                }
                
                switch(code){
                    case("VV"):
                        file = new TransfacFile();
                        file.setVersion(value);
                        break;
                    case("AC"):
                        
                        if(currMatrix!=null){
                            file.addMatrix(currMatrix.getMatrixID(), currMatrix);
                        }
                        currMatrix = new TransfacMatrix(value);
                        break;
                    case("ID"):
                        currMatrix.setMatrixID(value);
                        break;
                    case("NA"):
                        currMatrix.setGeneName(value);
                        break;
                    case("DE"):
                        currMatrix.setGeneDesc(value);
                        break;
                    case("CL"):
                        String[] cl = value.split(";");
                        currMatrix.setCL(cl);
                        break;
                    case("BF"):
                        currMatrix.addBF(value);
                        break;
                    case("P0"):
                        startMatrix=true;
                        break;
                    case("XX"):
                        if(startMatrix){
                            startMatrix=false;
                        }
                        break;
                    default:
                        break;
                }
            }
            
        } catch (FileNotFoundException ex) {
            System.err.println("File "+fileName+" is not found");
        } catch (IOException ex) {
            System.err.println("File "+fileName+" has an error");
        } finally {
            try {
                fr.close();
                br.close();
            } catch (IOException ex) {
                System.err.println("File "+fileName+" has an error");
            }
        }
        return file;
    }
}
