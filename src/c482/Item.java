package c482;

import java.util.List;

public abstract class Item {

    private String name;
    private double price;
    private int inStock;
    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() == 0)
            throw new IllegalArgumentException("The name cannot be blank.");
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        // Since doubles are initialized to 0, the item will, in fact, always
        // have a price. I'm going to assume that the price must be greater than
        // 0 to meet the requirements of "must have a price".
        if (price <= 0)
            throw new IllegalArgumentException("The price must have a value.");
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        // Inventory level should default to 0, which it does automatically so
        // there's no need to validate that particular requirement.
        if (inStock < min)
            throw new IllegalArgumentException(
                "The stock level cannot be less than the specified minimum.");
        if (inStock > max)
            throw new IllegalArgumentException(
                "The stock level cannot be greater than the specified maximum.");
        this.inStock = inStock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        if (min > max)
            throw new IllegalArgumentException(
                    "The minimum stock level cannot be greater than the maximum.");
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max < min)
            throw new IllegalArgumentException(
                    "The maximum stock level cannot be less than the minimum.");
        this.max = max;
    }
    
    public void resetMinMax() {
        max = Integer.MAX_VALUE;
        min = Integer.MIN_VALUE;
    }
    
    public Item() { }
    
    public Item(String name, double price, int inStock, int min, int max) {
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMax(max);
        setMin(min);
    }
}
