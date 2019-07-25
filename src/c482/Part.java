package c482;

import java.util.HashSet;
import java.util.Set;

public abstract class Part extends Item {
    
    private int partID;
    
    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }
    
    public Part() { }
    
    public Part(int partID, String name, double price, int inStock, int min, int max) {
        super(name, price, inStock, min, max);
        setPartID(partID);
    }
}
