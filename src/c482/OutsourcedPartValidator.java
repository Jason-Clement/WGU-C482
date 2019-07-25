package c482;

public class OutsourcedPartValidator extends PartValidator {
    private OutsourcedPart outsourcedPart;
    
    public OutsourcedPart getOutsourcedPart() {
        return outsourcedPart;
    }
    
    public OutsourcedPartValidator(OutsourcedPart outsourcedPart) {
        super(outsourcedPart);
        this.outsourcedPart = outsourcedPart;
    }
    
    public boolean setCompanyName(String companyName) {
        String k = "companyName";
        
        try {
            outsourcedPart.setCompanyName(companyName);
            return true;
        } catch (Exception e) {
            errors.put(k, e.getMessage());
            return false;
        }
    }
}
