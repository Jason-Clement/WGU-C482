package c482;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXMLPartController {
    
// <editor-fold defaultstate="expanded" desc="Fields">
    
    private WindowController windowController;
    private String extraRestoreText;
    private Inventory inventory;
    
// </editor-fold>
// <editor-fold defaultstate="expanded" desc="FXML Fields">

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Text title;
    @FXML private TextField extraField;
    @FXML private Label extraFieldLabel;
    @FXML private TextField idField;
    @FXML private RadioButton inHouseField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    @FXML private TextField nameField;
    @FXML private RadioButton outsourcedField;
    @FXML private TextField priceField;
    @FXML private TextField stockField;
    @FXML private Text errors;

// </editor-fold>
// <editor-fold defaultstate="expanded" desc="Initialization">
    
    @FXML
    void initialize() {
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'FXMLPart.fxml'.";
        assert extraField != null : "fx:id=\"extraField\" was not injected: check your FXML file 'FXMLPart.fxml'.";
        assert extraFieldLabel != null : "fx:id=\"extraFieldLabel\" was not injected: check your FXML file 'FXMLPart.fxml'.";
        assert idField != null : "fx:id=\"idField\" was not injected: check your FXML file 'FXMLPart.fxml'.";
        assert inHouseField != null : "fx:id=\"inHouseField\" was not injected: check your FXML file 'FXMLPart.fxml'.";
        assert minField != null : "fx:id=\"minField\" was not injected: check your FXML file 'FXMLPart.fxml'.";
        assert maxField != null : "fx:id=\"maxField\" was not injected: check your FXML file 'FXMLPart.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'FXMLPart.fxml'.";
        assert outsourcedField != null : "fx:id=\"outsourcedField\" was not injected: check your FXML file 'FXMLPart.fxml'.";
        assert priceField != null : "fx:id=\"priceField\" was not injected: check your FXML file 'FXMLPart.fxml'.";
        assert stockField != null : "fx:id=\"stockField\" was not injected: check your FXML file 'FXMLPart.fxml'.";
        assert errors != null : "fx:id=\"errors\" was not injected: check your FXML file 'FXMLPart.fxml'.";
    }
    
    public void load(WindowController controller, Inventory inventory, Part part) {
        this.windowController = controller;
        this.inventory = inventory;
        if (part == null) {
            this.title.setText("Add Part");
            return;
        }
        this.title.setText("Modify Part");
        this.idField.setText(Integer.toString(part.getPartID()));
        this.nameField.setText(part.getName());
        this.stockField.setText(Integer.toString(part.getInStock()));
        this.priceField.setText(Double.toString(part.getPrice()));
        if (part.getMin() != Integer.MIN_VALUE)
            this.minField.setText(Integer.toString(part.getMin()));
        if (part.getMax() != Integer.MAX_VALUE)
            this.maxField.setText(Integer.toString(part.getMax()));
        if (part instanceof InhousePart) {
            setInhouse(null);
            this.extraField.setText(Integer.toString(((InhousePart)part).getMachineID()));
        } else {
            setOutsourced(null);
            this.extraField.setText(((OutsourcedPart)part).getCompanyName());
        }
    }
    
// </editor-fold>
// <editor-fold defaultstate="expanded" desc="Form methods">
    
    @FXML
    void setInhouse(ActionEvent event) {
        if (event != null)
            event.consume();
        this.inHouseField.setSelected(true);
        this.outsourcedField.setSelected(false);
        this.extraFieldLabel.setText("Machine ID");
        this.extraField.setPromptText("Machine ID");
        String s = this.extraField.getText();
        this.extraField.setText(this.extraRestoreText);
        this.extraRestoreText = s;
    }

    @FXML
    void setOutsourced(ActionEvent event) {
        if (event != null)
            event.consume();
        this.inHouseField.setSelected(false);
        this.outsourcedField.setSelected(true);
        this.extraFieldLabel.setText("Company Name");
        this.extraField.setPromptText("Company Name");
        String s = this.extraField.getText();
        this.extraField.setText(this.extraRestoreText);
        this.extraRestoreText = s;
    }

// </editor-fold>
// <editor-fold defaultstate="expanded" desc="Save/Cancel">
    
    @FXML
    void save(ActionEvent event) {
        Part part;
        PartValidator validator;
        
        if (inHouseField.isSelected()) {
            InhousePart iPart = new InhousePart();
            InhousePartValidator iPartV = new InhousePartValidator(iPart);
            iPartV.setMachineID(extraField.getText().trim());
            part = iPart;
            validator = iPartV;
        } else {
            OutsourcedPart iPart = new OutsourcedPart();
            OutsourcedPartValidator iPartV = new OutsourcedPartValidator(iPart);
            iPartV.setCompanyName(extraField.getText().trim());
            part = iPart;
            validator = iPartV;
        }
        
        if (!idField.getText().trim().isEmpty())
            validator.setPartID(idField.getText().trim());
        validator.setName(nameField.getText().trim());
        validator.setPrice(priceField.getText().trim());
        validator.setMinMax(minField.getText().trim(), maxField.getText().trim());
        validator.setInStock(stockField.getText().trim());
        
        if (validator.hasErrors()) {
            errors.setText(String.join("\n", validator.getErrorsAsArray()));
        } else {
            inventory.updatePart(part);
            windowController.loadMain();
        }
    }
    
    @FXML
    void cancel(ActionEvent event) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Are you sure?");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to cancel? Any changes made will not be saved.");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES)
            windowController.loadMain();
    }
    
// </editor-fold>
}
