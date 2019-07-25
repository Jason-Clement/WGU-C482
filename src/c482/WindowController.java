package c482;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class WindowController {
        
    private Inventory inventory;
    private Stage stage;
    private double width;
    private double height;
    private Scene scene;
    
    public WindowController(Stage stage, Inventory inventory) {
        this.stage = stage;
        this.inventory = inventory;
    }
    
    public void exit() {
        stage.close();
    }
    
    public void loadMain() {
        FXMLLoader loader = loadFXML("FXMLMain.fxml", "FXML Main");
        FXMLMainController controller = loader.<FXMLMainController>getController();
        controller.load(this, inventory);
        showStage();
    }
    
    public void loadPart(Part part) {
        FXMLLoader loader = loadFXML("FXMLPart.fxml", "Part FXML");
        FXMLPartController controller = loader.<FXMLPartController>getController();
        controller.load(this, inventory, part);
        showStage();
    }
    
    public void loadProduct(Product product) {
        FXMLLoader loader = loadFXML("FXMLProduct.fxml", "FXML Product");
        FXMLProductController controller = loader.<FXMLProductController>getController();
        controller.load(this, inventory, product);
        showStage();
    }
    
    private void showStage() {
        stage.setScene(scene);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.show();
    }
    
    private FXMLLoader loadFXML(String url, String name) {
        width = stage.getWidth();
        height = stage.getHeight();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR, "The " + name + " could not be loaded", ButtonType.OK);
            alert.showAndWait();
            return null;
        }
        
        scene = new Scene(pane);
        
        return loader;
    }
    
}
