package c482;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    
// <editor-fold defaultstate="expanded" desc="Fields">
    
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    
// </editor-fold>
// <editor-fold defaultstate="expanded" desc="Properties">
    
    public ObservableList<Product> getProducts() {
        return products;
    }
    
    public ObservableList<Part> getAllParts() {
        return allParts;
    }
    
// </editor-fold>
// <editor-fold defaultstate="expanded" desc="Constructors">
    
    public Inventory() { }
    
    public Inventory(List<Part> parts, List<Product> products) {
        this.addParts(parts);
        this.addProducts(products);
    }
    
// </editor-fold>
// <editor-fold defaultstate="expanded" desc="Part Methods">
    
    public void addPart(Part part) {
        int id = part.getPartID();
        if (id <= 0) {
            for (Part tPart : allParts) {
                if (tPart.getPartID() > id) {
                    id = tPart.getPartID();
                }
            }
            part.setPartID(id + 1);
        }
        this.allParts.add(part);
    }
    
    public void addParts(List<Part> parts) {
        for (Part part : parts)
            this.addPart(part);
    }
    
    public boolean removePart(int id) {
        Part part = this.lookupPart(id);
        if (part == null)
            return false;
        allParts.remove(part);
        return true;
    }
    
    public Part lookupPart(int id) {
        for (Part part : allParts) {
            if (part.getPartID() == id)
                return part;
        }
        return null;
    }
    
    public void updatePart(int id) {
        // implemented in updatePart(Part part)
    }
    
    public void updatePart(Part part) {
        int id = part.getPartID();
        Part ePart = null;
        if (id > 0)
            ePart = lookupPart(part.getPartID());
        if (ePart != null) {
            part.setPartID(ePart.getPartID());
            removePart(ePart.getPartID());
        }
        addPart(part);
    }
    
// </editor-fold>
// <editor-fold defaultstate="expanded" desc="Product Methods">
    
    public void addProduct(Product product) {
        int id = product.getProductID();
        if (id == 0) {
            for (Product tProduct : products) {
                if (tProduct.getProductID() > id) {
                    id = tProduct.getProductID();
                }
            }
            product.setProductID(id + 1);
        }
        this.products.add(product);
    }
    
    public void addProducts(List<Product> products) {
        for (Product product : products)
            this.addProduct(product);
    }
    
    public boolean removeProduct(int id) {
        Product product = this.lookupProduct(id);
        if (product == null)
            return false;
        products.remove(product);
        return true;
    }
    
    public Product lookupProduct(int id) {
        for (Product product : products) {
            if (product.getProductID() == id) {
                return product;
            }
        }
        return null;
    }
    
    public void updateProduct(int id) {
        // implemented in updateProduct(Product product)
    }
    
    public void updateProduct(Product product) {
        int id = product.getProductID();
        Product eProduct = null;
        if (id > 0)
            eProduct = lookupProduct(product.getProductID());
        if (eProduct != null) {
            product.setProductID(eProduct.getProductID());
            removeProduct(eProduct.getProductID());
        }
        addProduct(product);
    }
    
// </editor-fold>
}
