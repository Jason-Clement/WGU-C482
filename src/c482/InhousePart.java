package c482;

public class InhousePart extends Part {
    private int machineID;

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
    
    public InhousePart() { }
    
    public InhousePart(int partID, String name, double price, int inStock, int min, int max, int machineID) {
        super(partID, name, price, inStock, min, max);
        this.setMachineID(machineID);
    }
}
