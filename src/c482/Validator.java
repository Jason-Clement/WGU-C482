package c482;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Implement a way to optionally enforce the order in which fields are set.

public abstract class Validator {
    protected HashMap<String, String> errors = new HashMap<>();
    
    public Map<String, String> getErrors() {
        return Collections.unmodifiableMap(errors);
    }
    
    public String[] getErrorsAsArray() {
        String[] array = new String[errors.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : errors.entrySet())
            array[i++] = entry.getValue();
        return array;
    }
    
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
    
// TODO: The following methods can be made static.
    
    protected ParseValue<Integer> tryParseInt(String s, String k, String name) {
        int v = 0;
        try {
            v = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            errors.put(k, "The " + name + " must be a whole number.");
            return new ParseValue<>(false);
        }
        return new ParseValue<>(v, true);
    }
    
    protected ParseValue<Integer> tryParseRequiredInt(String s, String k, String name) {
        if (s == null || s.isEmpty()) {
            errors.put(k, "The " + name + " must have a value.");
            return new ParseValue<>(false);
        }
        return tryParseInt(s, k, name);
    }
    
    protected ParseValue<Double> tryParseDouble(String s, String k, String name) {
        double v = 0;
        try {
            v = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            errors.put(k, "The " + name + " must be a number.");
            return new ParseValue<>(false);
        }
        return new ParseValue<>(v, true);
    }
    
    protected ParseValue<Double> tryParseRequiredDouble(String s, String k, String name) {
        if (s == null || s.isEmpty()) {
            errors.put(k, "The " + name + " must have a value.");
            return new ParseValue<>(false);
        }
        return tryParseDouble(s, k, name);
    }
}
