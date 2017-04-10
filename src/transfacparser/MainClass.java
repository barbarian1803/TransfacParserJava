package transfacparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MainClass {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "resources/matrix.dat";
        String fileDB = "resources/mart_export.csv";
        String output = "resources/geneListEnsembl.csv";
        TransfacFile file = TransfacParser.parseTransfacData(fileName);
        Map<String,String> geneDB = EnsemblGeneDB.ReadGeneDB(fileDB);
        
        
        
        for(Object obj:file.getMatrixID()){
            TransfacMatrix mtrx = file.getMatrix((String)obj);
            if(!mtrx.isHumanGene())
                continue;
            if(geneDB.containsKey(mtrx.getTFID())){
                geneDB.put(mtrx.getGeneName().toUpperCase(), geneDB.get(mtrx.getTFID()));
            }
        }
        
        Set<String> allTFID = new HashSet<>();
        PrintWriter pw = new PrintWriter(new File(output));
        pw.write("TFID\tGeneName\tEnsemblID\n");
        Set<String> notFound = new HashSet<>();
        for(Object obj:file.getMatrixID()){
            TransfacMatrix mtrx = file.getMatrix((String)obj);
            if(!mtrx.isHumanGene())
                continue;
            if(allTFID.contains(mtrx.getTFID()))
                continue;
            allTFID.add(mtrx.getTFID());
            String s = mtrx.getTFID()+"\t"+mtrx.getGeneName();
            if(geneDB.containsKey(mtrx.getTFID())){
                s+="\t"+geneDB.get(mtrx.getTFID())+"\n";
                geneDB.put(mtrx.getGeneName().toUpperCase(), geneDB.get(mtrx.getTFID()));
            }else{
                
                String[] parsed = mtrx.getGeneName().split(":");
                if(parsed.length>0){
                    s+="\t";
                    for(String xx:parsed){
                        xx = xx.toUpperCase();
                        
                        if(geneDB.containsKey(xx)){
                            s+=geneDB.get(xx);
                        }else{
                            notFound.add(xx);
                            s+="NA";
                        }
                        s+=":";
                    }
                }
                if(s.charAt(s.length()-1)==':'){
                    s = s.substring(0, s.length()-1);
                }
                s+="\n";
            }
            pw.write(s);
        }    

        pw.close();
        int n=1;
        for(String notfound:notFound){
            System.out.println(n+"\t"+notfound);
            n++;
        }
    }
}
