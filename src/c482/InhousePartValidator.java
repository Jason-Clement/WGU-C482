package c482;

public class InhousePartValidator extends PartValidator {
    private InhousePart inhousePart;
    
    public InhousePart getInhousePart() {
        return inhousePart;
    }
    
    public InhousePartValidator(InhousePart inhousePart) {
        super(inhousePart);
        this.inhousePart = inhousePart;
    }
    
    public boolean setMachineID(String id) {
        // I'm assuming that the machine ID required even though it doesn't
        // say so in the requirements document. Since it's an int, it has to
        // have a value and since no default has been specified, it must be
        // required.
        
        String k = "machineID";
        String n = "machine ID";
        
        ParseValue<Integer> pv = tryParseRequiredInt(id, k, n);
        if (!pv.isSuccessful())
            return false;
        
        try {
            inhousePart.setMachineID(pv.getValue());
            return true;
        } catch (Exception e) {
            errors.put(k, e.getMessage());
            return false;
        }
    }
}
