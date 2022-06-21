package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * modifyproductform class
 */
public class ModifyProductForm implements Initializable {
    public TextField ID;
    public TextField nameTxt;
    public TextField inventoryTxt;
    public TextField priceTxt;
    public TextField maxTxt;
    public TextField minTxt;
    public TextField search;
    public TableView partsTable;
    public TableColumn parts1;
    public TableColumn parts2;
    public TableColumn parts3;
    public TableColumn parts4;
    public Button add;
    public Button removeParts;
    public Button cancel;
    public Button save;
    public TableView aPartsTable;
    public TableColumn aparts1;
    public TableColumn aparts2;
    public TableColumn aparts3;
    public TableColumn aparts4;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public Product selectedProduct;
    private int productID;
    public Object scene;
    public Stage stage;

    /**
     * search method to see parts list in tableview
     * RUNTIME ERRORS none
     * FUTURE ENHANCEMENTS add way to search for company name or machine id
     * @param actionEvent
     */
    public void onSearch(ActionEvent actionEvent) {
        String s = search.getText();
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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Something went wrong :(");
                alert.showAndWait();
            }
        }

        partsTable.setItems(parts);
        search.setText("");
        if (parts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("This part does not exist :(");
            alert.setContentText("Searches are case sensitive");
            alert.showAndWait();
        }


    }

    /**
     * save method
     * RUNTIME ERRORS forgot to check for min values under 0. Just added the same else if from parts as it uses the same attribute names
     * FUTURE ENHANCEMENTS add a way to save multiple modified products
     * @param actionEvent
     */
    public void onSave(ActionEvent actionEvent) {
        try {
            int stock = Integer.parseInt(inventoryTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Your max value cannot be lower than the min value :(");
                alert.showAndWait();
            } else if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("You must have a stock level between the minimum value and max value :(");
                alert.showAndWait();
            } else if (min < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Your Min value must be greater than or equal to 0 :(");

                alert.showAndWait();
            }
            else {
                int id = Integer.parseInt(ID.getText());
                String name = nameTxt.getText();
                double price = Double.parseDouble(priceTxt.getText());
                Product i = new Product(id,name,price,stock,min,max, associatedParts);
                Inventory.updateProduct(productID, i);

                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();

            }
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Something went Wrong :(");

            alert.showAndWait();
        }
    }

    /**
     * cancel button method
     * @param actionEvent
     * @throws IOException
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * remove parts method
     * RUNTIME ERRORS forgot to add option to not remove parts from associated parts list. Corrected this by adding an alert with a button.
     * FUTURE ENHANCEMENTS add a way to save my option so i dont have to click ok on every part i want to remove
     * @param actionEvent
     */
    public void onRemoveParts(ActionEvent actionEvent) {
        Part selectedPart = (Part) aPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete this associated part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                updateaPartsTable();
            } else
                alert.close();
        }

         else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No selected Part");
            alert.setContentText("You must select a part for this button to work :)");
            alert.showAndWait();
        }
    }

    /**
     * adds part to associated parts table
     * RUNTIME ERROR part would not add as it wasn't being updated in the table so it wouldnt look like any part was added. Added an update method
     * FUTURE ENHANCEMENTS add a way to select multiple parts to add holding ctrl on the keyboard
     * @param actionEvent
     */
    public void onAdd(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        if(selectedPart != null){
            associatedParts.add(selectedPart);
            updateaPartsTable();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No selected Part");
            alert.setContentText("You must select a part for this button to work :)");
            alert.showAndWait();

        }
    }

    /**
     * updates the associated parts table
     */
    private void updateaPartsTable() { aPartsTable.setItems(associatedParts);
    }

    /**
     * initializes the parts table
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());

        parts1.setCellValueFactory(new PropertyValueFactory<>("id"));
        parts2.setCellValueFactory(new PropertyValueFactory<>("name"));
        parts3.setCellValueFactory(new PropertyValueFactory<>("stock"));
        parts4.setCellValueFactory(new PropertyValueFactory<>("price"));

        aparts1.setCellValueFactory(new PropertyValueFactory<>("id"));
        aparts2.setCellValueFactory(new PropertyValueFactory<>("name"));
        aparts3.setCellValueFactory(new PropertyValueFactory<>("stock"));
        aparts4.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * set product method
     * RUNTIME ERRORS wasn't adding the associated parts list to the list/ was adding it to the table. corrected this by adding it to the list and updating the parts table to show the associated parts table view
     * FUTURE ENHANCEMENTS n/a
     * @param selectedProduct
     */
    public void setProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        productID = Inventory.getAllProducts().indexOf(selectedProduct);
        ID.setText(Integer.toString(selectedProduct.getId()));
        nameTxt.setText(selectedProduct.getName());
        inventoryTxt.setText(Integer.toString(selectedProduct.getStock()));
        priceTxt.setText(Double.toString(selectedProduct.getPrice()));
        maxTxt.setText(Integer.toString(selectedProduct.getMax()));
        minTxt.setText(Integer.toString(selectedProduct.getMin()));
        associatedParts.addAll(selectedProduct.getAllAssociatedParts());
        updateaPartsTable();

    }
}
