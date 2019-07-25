package c482;

public abstract class PartValidator extends ItemValidator {
    private Part part;
    
    public Part getPart() {
        return part;
    }
    
    public PartValidator(Part part) {
        super(part);
        this.part = part;
    }
    
    public boolean setPartID(String id) {
        String k = "partID";
        String n = "part ID";

        ParseValue<Integer> pv = tryParseRequiredInt(id, k, n);
        if (!pv.isSuccessful())
            return false;
        
        try {
            part.setPartID(pv.getValue());
            return true;
        } catch (Exception e) {
            errors.put(k, e.getMessage());
            return false;
        }
    }
}
