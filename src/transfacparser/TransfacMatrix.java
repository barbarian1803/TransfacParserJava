package transfacparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransfacMatrix {
    
    public static final char A = 'A';
    public static final char C = 'C';
    public static final char G = 'G';
    public static final char T = 'T';
    
    private String accessionCode;
    private String matrixID;
    private String geneName;
    private String geneDesc;
    private String[] CL;
    private List<String> BF;
    private List<Map<Character,Double>> pwm;
    
    public TransfacMatrix(String accessionCode){
        this.accessionCode = accessionCode;
        BF = new ArrayList();
        pwm = new ArrayList();
    }

    public String getMatrixID() {
        return matrixID;
    }

    public void setMatrixID(String matrixID) {
        this.matrixID = matrixID;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public String getGeneDesc() {
        return geneDesc;
    }

    public void setGeneDesc(String geneDesc) {
        this.geneDesc = geneDesc;
    }
    
    public void insertPWMValue(String[] values){
        Map<Character,Double> valueMap = new HashMap();
        valueMap.put(TransfacMatrix.A, Double.parseDouble(values[0]));
        valueMap.put(TransfacMatrix.C, Double.parseDouble(values[1]));
        valueMap.put(TransfacMatrix.G, Double.parseDouble(values[2]));
        valueMap.put(TransfacMatrix.T, Double.parseDouble(values[3]));
        this.pwm.add(valueMap);
    }
    
    public int getPWMLength(){
        return this.pwm.size();
    }
    
    public List getPWMList(){
        return this.pwm;
    }
    
    public Map<Character,Double> getPWMWithPos(int i){
        return this.pwm.get(i);
    }
    
    public String getTFID(){
        String id = this.getMatrixID().split("\\$")[1];
        return id.split("_")[0];
    }

    public String[] getCL() {
        return CL;
    }

    public void setCL(String[] CL) {
        this.CL = new String[CL.length];
        for(int i=0;i<CL.length;i++){
            this.CL[i]=CL[i].trim();
        }
    }
    
    public void addBF(String entry){
        this.BF.add(entry);
    }
    
    public void printData(){
        System.out.println(this.accessionCode);
        System.out.println(this.matrixID);
        System.out.println(this.getGeneName());
        System.out.println(this.getGeneDesc());
        System.out.println(this.getTFID());
    }
    
    public void printMatrix(){
        for(Map m:this.pwm){
            System.out.println(m.get(TransfacMatrix.A)+"\t"+m.get(TransfacMatrix.C)+"\t"+m.get(TransfacMatrix.G)+"\t"+m.get(TransfacMatrix.T)+"\t");
        }
    }
}
