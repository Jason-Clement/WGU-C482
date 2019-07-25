package c482;

public abstract class ItemValidator extends Validator {
    private Item item;
    
    public Item getItem() {
        return item;
    }
    
    public ItemValidator(Item item) {
        this.item = item;
    }
    
    public boolean setName(String name) {
        String k = "name";
        try {
            item.setName(name);
            return true;
        } catch (Exception e) {
            errors.put(k, e.getMessage());
            return false;
        }
    }
    
    public boolean setPrice(String price) {
        String k = "price";
        String n = k;
        
        ParseValue<Double> pv = tryParseRequiredDouble(price, k, n);
        if (!pv.isSuccessful())
            return false;

        try {
            item.setPrice(pv.getValue());
            return true;
        } catch (Exception e) {
            errors.put(k, e.getMessage());
            return false;
        }
    }
    
    public boolean setInStock(String inStock) {
        String k = "inStock";
        String n = "stock level";
        int v = 0;
        
        // Skip if String is empty because this has a default value.
        if (inStock != null && !inStock.isEmpty()) {
            ParseValue<Integer> pv = tryParseInt(inStock, k, n);
            if (!pv.isSuccessful())
                return false;
            v = pv.getValue();
        }
        
        try {
            item.setInStock(v);
            return true;
        } catch (Exception e) {
            errors.put(k, e.getMessage());
            return false;
        }
    }
    
    public boolean setMinMax(String min, String max) {
        // Set min and max at the same time since they are interelated and also 
        // because both must be reset before setting, otherwise a valid min-max
        // range could fail if it's outside the current range.
        
        String kMin = "min";
        String nMin = "minimum";
        String kMax = "max";
        String nMax = "maximum";
        boolean minHasValue = min != null && !min.isEmpty();
        boolean maxHasValue = max != null && !max.isEmpty();
        boolean hasFailed = false;
        int vMin = 0;
        int vMax = 0;
        
        // Skip if String is empty because this is not required.
        if (minHasValue) {
            ParseValue<Integer> pv = tryParseInt(min, kMin, nMin);
            if (!pv.isSuccessful())
                hasFailed = true;
            else
                vMin = pv.getValue();
        }
        if (maxHasValue) {
            ParseValue<Integer> pv = tryParseInt(max, kMax, nMax);
            if (!pv.isSuccessful())
                hasFailed = true;
            else
                vMax = pv.getValue();
        }
        
        if (hasFailed)
            return false;
        
        // If one is present, the other should be present as well. There's
        // no rule in the requirements that either should have a default value.
        if (minHasValue ^ maxHasValue) {
            if (minHasValue)
                errors.put(kMax,
                    "Maximum must have a value if the minimum has a value.");
            else
                errors.put(kMin,
                    "Minimum must have a value if the maximum has a value.");
            return false;
        }
        
        item.resetMinMax();
        
        // If both are missing, we've already reset the values, so we're done.
        if (!minHasValue && !maxHasValue)
            return true;
        
        try {
            item.setMax(vMax);
        } catch (Exception e) {
            errors.put(kMax, e.getMessage());
            hasFailed = true;
        }
        
        try {
            item.setMin(vMin);
        } catch (Exception e) {
            errors.put(kMin, e.getMessage());
            hasFailed = true;
        }
        
        if (hasFailed)
            return false;
        
        return true;
    }
}
