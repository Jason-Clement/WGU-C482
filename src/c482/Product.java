package c482;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product extends Item {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
    
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
    
    public Product() { }
    
    public Product(int productID, String name, double price, int inStock, int min,
                   int max, List<Part> associatedParts) {
        super(name, price, inStock, min, max);
        if (associatedParts.size() < 1)
            throw new IllegalArgumentException("Product must contain at least one part.");
        setProductID(productID);
        addAssociatedParts(associatedParts);
    }
    
    public void removeAllAssociatedParts() {
        associatedParts.clear();
    }
    
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public void addAssociatedParts(List<Part> parts) {
        associatedParts.addAll(parts);
    }
    
    public boolean removeAssociatedPart(int id) {
        for (Part part : associatedParts) {
            if (part.getPartID() == id) {
                if (associatedParts.size() <= 1)
                    throw new IllegalArgumentException("Product must contain at least one part.");
                associatedParts.remove(part);
                return true;
            }
        }
        return false;
    }
    
    public Part lookupAssociatedPart(int id) {
        for (Part part : associatedParts) {
            if (part.getPartID() == id) {
                return part;
            }
        }
        return null;
    }
}
