package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Main Form Class
 */
public class MainForm implements Initializable {
    public Button exit;
    public TableView partsTable;
    public TableColumn parts1;
    public TableColumn parts2;
    public TableColumn parts3;
    public TableColumn parts4;
    public Button delete1;
    public Button add1;
    public Button modify1;
    public TextField search1;
    public TableView productsTable;
    public TableColumn products1;
    public TableColumn products2;
    public TableColumn products3;
    public TableColumn products4;
    public Button delete2;
    public Button add2;
    public Button modify2;
    public TextField search2;

    private Parent scene;

    /**
     * This is the onexit button on the mainform screen. This will exit the stage.
     * @param actionEvent
     */
    public void onExit(ActionEvent actionEvent) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    /**
     * This is the search box for the products table
     * RUNTIME ERRORS copied and pasted same method to parts onsearch and would still get products. corrected this by changin the list and other variables.
     * FUTURE ENHANCEMENTS provide other ways to search for products such as company name
     * @param actionEvent
     */
    public void onSearch2(ActionEvent actionEvent) {
        String s = search2.getText();

        ObservableList<Product> products = Inventory.lookupProduct(s);
        if(products.size() == 0){
            try {

                int id = Integer.parseInt(s);
                Product pr = Inventory.lookupProduct(id);
                if (pr != null)
                    products.add(pr);
            }
            catch (NumberFormatException e){
                //ignore me :)
            }
        }

        productsTable.setItems(products);
        search2.setText("");
        if (products.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("This product does not exist :(");
            alert.setContentText("Searches are case sensitive");
            alert.showAndWait();
        }
    }

    /**
     * This will take you to the modify products screen
     * RUNTIMER ERRORS none
     * FUTURE ENHANCEMENTS none
     * @param actionEvent
     * @throws IOException
     */
    public void onModify2(ActionEvent actionEvent)throws IOException {
        Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            return;
        }
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifyProductForm.fxml"));
        scene = loader.load();
        ModifyProductForm controller = loader.getController();
        controller.setProduct(selectedProduct);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * method for going to add products
     * RUNTIME ERRORS mistyped the .getresource method and got an error to fix this I changed the path
     * FUTURE ENHANCEMENTS n/a
     * @param actionEvent
     * @throws IOException
     */
    public void onAdd2(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/productForm.fxml"));
        Stage stage =(Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * deletes a product from the allproducts list
     * RUNTIME ERRORS forgot to ask whether or not to delete. added an alert button option to ask the user to delete
     * FUTURE ENHANCEMENTS add an option to delete all associated parts WITH the product
     * @param actionEvent
     */
    public void onDelete2(ActionEvent actionEvent) {
        Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete");
        alert.setContentText("Do you want to delete this product");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (selectedProduct.getAllAssociatedParts().size() > 0){
                alert.close();
                Alert multiplePartsAlert = new Alert(Alert.AlertType.ERROR);
                multiplePartsAlert.setTitle("Error Dialog");
                multiplePartsAlert.setContentText("You cannot delete a product with a part associated with it :(");
                multiplePartsAlert.showAndWait();
            }
            else
            Inventory.deleteProduct(selectedProduct);
            alert.close();
        }
    }

    /**
     * This is the search method for parts table
     * RUNTIME ERRORS couldn't find parts without using case corrected this with a popup that states searches are case sensitive
     * FUTURE ENHANCEMENTS automatically correct searches to not use case sensitivity
     * @param actionEvent
     */
    public void onSearch1(ActionEvent actionEvent) {
        String s = search1.getText();
        ObservableList<Part> parts = Inventory.lookupPart(s);

        if(parts.size() == 0){
            try {

                int id = Integer.parseInt(s);
                Part pa = Inventory.lookupPart(id);
                if (pa != null)
                    parts.add(pa);
            }
            //catches any misinformation
            catch (NumberFormatException e){
            }
        }

        partsTable.setItems(parts);
        search1.setText("");
        if (parts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("This part does not exist :(");
            alert.setContentText("Searches are case sensitive");
            alert.showAndWait();
        }


    }

    /**
     * method to get to modify parts screen
     * RUTTIME ERRORS n/a
     * FUTURE ENHANCEMENTS n/a
     * @param actionEvent
     * @throws IOException
     */
    public void onModify1(ActionEvent actionEvent) throws IOException {
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifyPartsForm.fxml"));
        scene = loader.load();
        ModifyPartsForm controller = loader.getController();
        controller.setPart(selectedPart);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * method to get to add parts screen
     * RUNTIME ERRORS n/a
     * FUTURE ENHANCEMENTS n/a
     * @param actionEvent
     * @throws IOException
     */
    public void onAdd1(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/partsForm.fxml"));
        Stage stage =(Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * method to delete a part
     * RUNTIME ERROR none
     * FUTURE ENHANCEMENT automatically delete parts deleted from this list from all associated parts lists
     * @param actionEvent
     */
    public void onDelete1(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete");
        alert.setContentText("Do you want to delete this part");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //User chooses to delete
            Inventory.deletePart(selectedPart);
        } else {
            alert.close();
        }
    }

    /**
     * gets all parts and products for tables and puts the data in the right columns
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        partsTable.setItems(Inventory.getAllParts());

        productsTable.setItems(Inventory.getAllProducts());

        parts1.setCellValueFactory(new PropertyValueFactory<>("id"));
        parts2.setCellValueFactory(new PropertyValueFactory<>("name"));
        parts3.setCellValueFactory(new PropertyValueFactory<>("stock"));
        parts4.setCellValueFactory(new PropertyValueFactory<>("price"));

        products1.setCellValueFactory(new PropertyValueFactory<>("id"));
        products2.setCellValueFactory(new PropertyValueFactory<>("name"));
        products3.setCellValueFactory(new PropertyValueFactory<>("stock"));
        products4.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}
