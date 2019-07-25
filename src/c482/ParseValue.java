package c482;

public class ParseValue<v> {
    private v value;
    private boolean success = false;
    
    public boolean isSuccessful() {
        return success;
    }
    
    public v getValue() {
        return value;
    }
    
    public ParseValue(v value, boolean success) {
        this.value = value;
        this.success = success;
    }
    
    public ParseValue(boolean success) {
        this.success = success;
    }
}
