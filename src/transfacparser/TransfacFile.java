package transfacparser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TransfacFile {
    private String version;
    private Map<String,TransfacMatrix> matrices;
    
    public TransfacFile(){
        this.matrices = new HashMap();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    public void addMatrix(String id,TransfacMatrix entry){
        this.matrices.put(id, entry);
    }
    
    public TransfacMatrix getMatrix(String id){
        return this.matrices.get(id);
    }
    
    public Set<String> getMatrixID(){
        return this.matrices.keySet();
    }
    
    public int getMatricesSize(){
        return this.matrices.size();
    }
}
