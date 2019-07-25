package c482;
public class OutsourcedPart extends Part {
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public OutsourcedPart() { }
    
    public OutsourcedPart(int id, String name, double price, int inStock, int min, int max, String companyName) {
        super(id, name, price, inStock, min, max);
        this.setCompanyName(companyName);
    }
}
