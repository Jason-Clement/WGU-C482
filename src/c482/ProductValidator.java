package c482;

import java.util.List;

public class ProductValidator extends ItemValidator {
    private Product product;
    
    public Product getProduct() {
        return product;
    }
    
    public ProductValidator(Product product) {
        super(product);
        this.product = product;
    }
    
    public boolean setProductID(String id) {
        String k = "productID";
        String n = "product ID";

        ParseValue<Integer> pv = tryParseRequiredInt(id, k, n);
        if (!pv.isSuccessful())
            return false;
        
        try {
            product.setProductID(pv.getValue());
            return true;
        } catch (Exception e) {
            errors.put(k, e.getMessage());
            return false;
        }
    }
    
    public boolean setAssociatedParts(List<Part> parts) {
        String k = "associatedParts";
        String n = "associated parts";
        
        if (parts.isEmpty()) {
            errors.put(k, "The " + n + " must include at least one item.");
            return false;
        }
        
        try {
            product.removeAllAssociatedParts();
            product.addAssociatedParts(parts);
            return true;
        } catch (Exception e) {
            errors.put(k, e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean setPrice(String price) {
        String k = "price";
        String n = k;
        
        if (!super.setPrice(price))
            return false;
        
        double cost = 0;
        for (Part part : product.getAssociatedParts())
            cost += part.getPrice();
        if (product.getPrice() < cost) {
            errors.put(k, "The " + n +  " cannot be less than the sum of the prices of the"
                + " associated parts.");
            return false;
        }
        
        return true;
    }
}
