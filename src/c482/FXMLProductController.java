package c482;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class FXMLProductController {
    
// <editor-fold defaultstate="expanded" desc="Fields">

    private WindowController windowController;
    private Inventory inventory;
    private ObservableList<Part> availableParts;
    private ObservableList<Part> includedParts;
    private FilteredList<Part> filteredAvailableParts;
    private SortedList<Part> sortedIncludedParts;

// </editor-fold>
// <editor-fold defaultstate="expanded" desc="FXML Fields">
    
    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private Text title;
    @FXML private Text errors;
    
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField stockField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    @FXML private TextField searchBox;
    
    @FXML private TableView<Part> availablePartsTable;
    @FXML private TableColumn<Part, ?> availablePartsIDColumn;
    @FXML private TableColumn<Part, ?> availablePartsNameColumn;
    @FXML private TableColumn<Part, ?> availablePartsInventoryColumn;
    @FXML private TableColumn<Part, ?> availablePartsPriceColumn;
    
    @FXML private TableView<Part> includedPartsTable;
    @FXML private TableColumn<Part, ?> includedPartsIDColumn;
    @FXML private TableColumn<Part, ?> includedPartsNameColumn;
    @FXML private TableColumn<Part, ?> includedPartsInventoryColumn;
    @FXML private TableColumn<Part, ?> includedPartsPriceColumn;

// </editor-fold>
// <editor-fold defaultstate="expanded" desc="Initialization">

    @FXML
    void initialize() {
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert errors != null : "fx:id=\"errors\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert availablePartsIDColumn != null : "fx:id=\"availablePartsIDColumn\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert availablePartsInventoryColumn != null : "fx:id=\"availablePartsInventoryColumn\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert availablePartsNameColumn != null : "fx:id=\"availablePartsNameColumn\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert availablePartsPriceColumn != null : "fx:id=\"availablePartsPriceColumn\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert availablePartsTable != null : "fx:id=\"availablePartsTable\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert idField != null : "fx:id=\"idField\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert includedPartsIDColumn != null : "fx:id=\"includedPartsIDColumn\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert includedPartsInventoryColumn != null : "fx:id=\"includedPartsInventoryColumn\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert includedPartsNameColumn != null : "fx:id=\"includedPartsNameColumn\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert includedPartsPriceColumn != null : "fx:id=\"includedPartsPriceColumn\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert includedPartsTable != null : "fx:id=\"includedPartsTable\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert maxField != null : "fx:id=\"maxField\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert minField != null : "fx:id=\"minField\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert priceField != null : "fx:id=\"priceField\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert searchBox != null : "fx:id=\"searchBox\" was not injected: check your FXML file 'FXMLProduct.fxml'.";
        assert stockField != null : "fx:id=\"stockField\" was not injected: check your FXML file 'FXMLProduct.fxml'.";

        availablePartsIDColumn.prefWidthProperty().bind(availablePartsTable.widthProperty().multiply(0.19));
        availablePartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        availablePartsNameColumn.prefWidthProperty().bind(availablePartsTable.widthProperty().multiply(0.38));
        availablePartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        availablePartsInventoryColumn.prefWidthProperty().bind(availablePartsTable.widthProperty().multiply(0.19));
        availablePartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        availablePartsPriceColumn.prefWidthProperty().bind(availablePartsTable.widthProperty().multiply(0.19));
        availablePartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        includedPartsIDColumn.prefWidthProperty().bind(includedPartsTable.widthProperty().multiply(0.19));
        includedPartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        includedPartsNameColumn.prefWidthProperty().bind(includedPartsTable.widthProperty().multiply(0.38));
        includedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        includedPartsInventoryColumn.prefWidthProperty().bind(includedPartsTable.widthProperty().multiply(0.19));
        includedPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        includedPartsPriceColumn.prefWidthProperty().bind(includedPartsTable.widthProperty().multiply(0.19));
        includedPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        availablePartsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        availablePartsTable.getSortOrder().setAll(availablePartsNameColumn);
        includedPartsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        includedPartsTable.getSortOrder().setAll(includedPartsNameColumn);
        
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filterParts(newValue);
        });
    }
    
    public void load(WindowController controller, Inventory inventory, Product product) {
        this.windowController = controller;
        this.inventory = inventory;
        
        if (product == null) {
            this.title.setText("Add Product");
            includedParts = FXCollections.observableArrayList();
        } else {
            this.title.setText("Modify Product");
            this.idField.setText(Integer.toString(product.getProductID()));
            this.nameField.setText(product.getName());
            this.stockField.setText(Integer.toString(product.getInStock()));
            this.priceField.setText(Double.toString(product.getPrice()));
            if (product.getMin() != Integer.MIN_VALUE)
                this.minField.setText(Integer.toString(product.getMin()));
            if (product.getMax() != Integer.MAX_VALUE)
                this.maxField.setText(Integer.toString(product.getMax()));
            includedParts = product.getAssociatedParts();
        }
        
        availableParts = inventory.getAllParts();
        filteredAvailableParts = new FilteredList<>(availableParts, p -> true);
        SortedList<Part> sortedAvailableParts = new SortedList<>(filteredAvailableParts);
        sortedAvailableParts.comparatorProperty().bind(availablePartsTable.comparatorProperty());
        availablePartsTable.setItems(sortedAvailableParts);
        
        sortedIncludedParts = new SortedList<Part>(includedParts);
        sortedIncludedParts.comparatorProperty().bind(includedPartsTable.comparatorProperty());
        includedPartsTable.setItems(sortedIncludedParts);
        
        filterParts();
    }
    
// </editor-fold>
// <editor-fold defaultstate="expanded" desc="Form methods">
    
    private void filterParts() {
        String filter = searchBox.getText();
        filterParts(filter);
    }
    
    private void filterParts(String filter) {
        filteredAvailableParts.setPredicate(part -> {
            if (sortedIncludedParts.contains(part))
                return false;
            if (filter == null || filter.isEmpty())
                return true;
            if (part.getName().toLowerCase().contains(filter))
                return true;
            return false;
        });
    }
    
    @FXML
    void selectFilter(ActionEvent event) {
        searchBox.selectAll();
        searchBox.requestFocus();
    }
    
    @FXML
    void addPartToIncluded(ActionEvent event) {
        TableViewSelectionModel<Part> selectionModel = this.availablePartsTable.getSelectionModel();
        Part part = selectionModel.getSelectedItem();
        includedParts.add(part);
        filterParts();
    }
    
    @FXML
    void deleteIncludedPart(ActionEvent event) {
        TableViewSelectionModel<Part> selectionModel = this.includedPartsTable.getSelectionModel();
        Part part = selectionModel.getSelectedItem();
        includedParts.remove(part);
        filterParts();
    }
    
// </editor-fold>
// <editor-fold defaultstate="expanded" desc="Save/Cancel">

    @FXML
    void save(ActionEvent event) {
        Product product = new Product();
        ProductValidator validator = new ProductValidator(product);
                
        if (!idField.getText().trim().isEmpty())
            validator.setProductID(idField.getText().trim());
        validator.setName(nameField.getText().trim());
        validator.setAssociatedParts(includedParts);
        validator.setPrice(priceField.getText().trim());
        validator.setMinMax(minField.getText().trim(), maxField.getText().trim());
        validator.setInStock(stockField.getText().trim());
        
        if (validator.hasErrors()) {
            errors.setText("The product could not be saved because:\n\n" +
                String.join("\n", validator.getErrorsAsArray()));
        } else {
            inventory.updateProduct(product);
            windowController.loadMain();
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
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
