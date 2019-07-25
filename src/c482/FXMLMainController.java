package c482;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLMainController {

// <editor-fold defaultstate="expanded" desc="Fields">
    
    private WindowController windowController;
    private Inventory inventory;
    private FilteredList<Part> filteredParts;
    private FilteredList<Product> filteredProducts;
    
// </editor-fold>
// <editor-fold defaultstate="expanded" desc="FXML Fields">
    
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    
    @FXML private Button partsAddButton;
    @FXML private Button partsModifyButton;
    @FXML private Button partsDeleteButton;
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, ?> partsIDColumn;
    @FXML private TableColumn<Part, ?> partsNameColumn;
    @FXML private TableColumn<Part, ?> partsInventoryColumn;
    @FXML private TableColumn<Part, ?> partsPriceColumn;
    @FXML private TextField partsSearchBox;
    @FXML private Button partsSearchButton;
    
    @FXML private Button productsAddButton;
    @FXML private Button productsModifyButton;
    @FXML private Button productsDeleteButton;
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, ?> productsIDColumn;
    @FXML private TableColumn<Product, ?> productsInventoryColumn;
    @FXML private TableColumn<Product, ?> productsNameColumn;
    @FXML private TableColumn<Product, ?> productsPriceColumn;
    @FXML private TextField productsSearchBox;
    @FXML private Button productsSearchButton;
    
// </editor-fold>
// <editor-fold defaultstate="expanded" desc="Initialization">

    @FXML
    void initialize() {
        assert partsAddButton != null : "fx:id=\"partsAddButton\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert partsDeleteButton != null : "fx:id=\"partsDeleteButton\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert partsIDColumn != null : "fx:id=\"partsIDColumn\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert partsInventoryColumn != null : "fx:id=\"partsInventoryColumn\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert partsModifyButton != null : "fx:id=\"partsModifyButton\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert partsNameColumn != null : "fx:id=\"partsNameColumn\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert partsPriceColumn != null : "fx:id=\"partsPriceColumn\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert partsSearchBox != null : "fx:id=\"partsSearchBox\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert partsSearchButton != null : "fx:id=\"partsSearchButton\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert partsTable != null : "fx:id=\"partsTable\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert productsAddButton != null : "fx:id=\"productsAddButton\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert productsDeleteButton != null : "fx:id=\"productsDeleteButton\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert productsIDColumn != null : "fx:id=\"productsIDColumn\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert productsInventoryColumn != null : "fx:id=\"productsInventoryLevel\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert productsModifyButton != null : "fx:id=\"productsModifyButton\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert productsNameColumn != null : "fx:id=\"productsNameColumn\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert productsPriceColumn != null : "fx:id=\"productsPriceColumn\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert productsSearchBox != null : "fx:id=\"productsSearchBox\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert productsSearchButton != null : "fx:id=\"productsSearchButton\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        assert productsTable != null : "fx:id=\"productsTable\" was not injected: check your FXML file 'FXMLMain.fxml'.";
        
        partsIDColumn.prefWidthProperty().bind(partsTable.widthProperty().multiply(0.19));
        partsIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partsNameColumn.prefWidthProperty().bind(partsTable.widthProperty().multiply(0.38));
        partsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryColumn.prefWidthProperty().bind(partsTable.widthProperty().multiply(0.19));
        partsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partsPriceColumn.prefWidthProperty().bind(partsTable.widthProperty().multiply(0.19));
        partsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productsIDColumn.prefWidthProperty().bind(productsTable.widthProperty().multiply(0.19));
        productsIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productsNameColumn.prefWidthProperty().bind(productsTable.widthProperty().multiply(0.38));
        productsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryColumn.prefWidthProperty().bind(productsTable.widthProperty().multiply(0.19));
        productsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productsPriceColumn.prefWidthProperty().bind(productsTable.widthProperty().multiply(0.19));
        productsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        partsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        partsTable.getSortOrder().setAll(partsNameColumn);
        productsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        productsTable.getSortOrder().setAll(productsNameColumn);
        
        partsSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filterParts(newValue);
        });
        
        productsSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filterProducts(newValue);
        });
    }
    
    public void load(WindowController controller, Inventory inventory) {
        this.windowController = controller;
        this.inventory = inventory;
        
        filteredParts = new FilteredList<>(inventory.getAllParts(), part -> true);
        SortedList<Part> sortedParts = new SortedList<>(filteredParts);
        sortedParts.comparatorProperty().bind(partsTable.comparatorProperty());
        partsTable.setItems(sortedParts);
        
        filteredProducts = new FilteredList<>(inventory.getProducts(), product -> true);
        SortedList<Product> products = new SortedList<>(filteredProducts);
        products.comparatorProperty().bind(productsTable.comparatorProperty());
        productsTable.setItems(products);
    }
    
// </editor-fold>
// <editor-fold defaultstate="expanded" desc="Part Methods">
    
    @FXML
    void addPart(ActionEvent event) {
        windowController.loadPart(null);
    }

    @FXML
    void deletePart(ActionEvent event) {
        TableViewSelectionModel<Part> selectionModel = this.partsTable.getSelectionModel();
        Part part = selectionModel.getSelectedItem();
        if (part == null)
            return;
        
        ArrayList<Product> associatedProducts = new ArrayList<>();
        for (Product product : inventory.getProducts()) {
            if (product.getAssociatedParts().contains(part))
                associatedProducts.add(product);
        }
        
        if (!associatedProducts.isEmpty()) {
            Product[] products = new Product[associatedProducts.size()];
            alertHasProducts(part, associatedProducts.toArray(products));
            return;
        }
        
        if (promptForDelete(part))
            inventory.removePart(part.getPartID());
    }
    
    void alertHasProducts(Part part, Product[] products) {
        String contentText = part.getName()
            + " is associated with the following products and cannot"
            + " be deleted:\n\n";
        for (Product product : products)
            contentText += product.getName() + "\n";
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Not Possible");
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.OK);
        alert.showAndWait();
    }
    
    boolean promptForDelete(Part part) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Are you sure?");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete " + part.getName() + "?"
            + " This cannot be undone.");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES) {
            return true;
        }
        return false;
    }

    @FXML
    void modifyPart(ActionEvent event) {
        TableViewSelectionModel<Part> selectionModel = this.partsTable.getSelectionModel();
        Part part = selectionModel.getSelectedItem();
        if (part == null)
            return;
        windowController.loadPart(part);
    }
    
    void filterParts(String filter) {
        filteredParts.setPredicate(part -> {
            if (filter == null || filter.isEmpty())
                return true;
            if (part.getName().toLowerCase().contains(filter))
                return true;
            return false;
        });
    }
    
    @FXML
    void selectPartsFilter(ActionEvent event) {
        partsSearchBox.selectAll();
        partsSearchBox.requestFocus();
    }
    
// </editor-fold>
// <editor-fold defaultstate="expanded" desc="Product Methods">
    
    @FXML
    void addProduct(ActionEvent event) {
        windowController.loadProduct(null);
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        TableViewSelectionModel<Part> selectionModel = this.partsTable.getSelectionModel();
        Part part = selectionModel.getSelectedItem();
        if (part == null)
            return;
        
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Are you sure?");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete " + part.getName() + "?"
            + " This cannot be undone.");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES) {
            inventory.removePart(part.getPartID());
        }
    }

    @FXML
    void modifyProduct(ActionEvent event) {
        TableViewSelectionModel<Product> selectionModel = this.productsTable.getSelectionModel();
        Product product = selectionModel.getSelectedItem();
        if (product == null)
            return;
        windowController.loadProduct(product);
    }
    
    void filterProducts(String filter) {
        filteredProducts.setPredicate(product -> {
            if (filter == null || filter.isEmpty())
                return true;
            if (product.getName().toLowerCase().contains(filter))
                return true;
            return false;
        });
    }
    
    @FXML
    void selectProductsFilter(ActionEvent event) {
        productsSearchBox.selectAll();
        productsSearchBox.requestFocus();
    }
    
// </editor-fold>
    
    @FXML
    void exit() {
        windowController.exit();
    }
}
