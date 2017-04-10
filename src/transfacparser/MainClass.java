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
        Map geneDB = EnsemblGeneDB.ReadGeneDB(fileDB);
        
        PrintWriter pw = new PrintWriter(new File(output));
        Set<String> allTFID = new HashSet<>();
        for(Object obj:file.getMatrixID()){
            TransfacMatrix mtrx = file.getMatrix((String)obj);
            if(!mtrx.isHumanGene())
                continue;
            if(allTFID.contains(mtrx.getTFID()))
                continue;
            allTFID.add(mtrx.getTFID());
            String s = mtrx.getTFID()+"\t"+mtrx.getGeneName();
            if(geneDB.containsKey(mtrx.getTFID())){
                s+="\t"+geneDB.get(mtrx.getTFID()).toString()+"\n";
            }else{
                s+="\n";
            }
            pw.write(s);
        }
        pw.close();
    }
}
