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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * main productform class
 * RUNTIME ERRORS generated id wrong way using size of product observable list. corrected using static int firstID
 * FUTURE ENHANCEMENTS n/a
 */
public class ProductForm implements Initializable {
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


    //this is the stage scene and associatedparts list that is shown in the table.
    private Stage stage;
    private Object scene;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    // this gives a product so you can add associated parts to the list
    public Product temp;


    //this provides a first number to generate a unique id for every product
    static int firstID = 3;

    /**
     * search method for finding parts in allparts table
     * RUNTIME ERRORS none
     * FUTURE ENHANCEMENTS add way to search by company or machine id
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
     * add method to add part to associated parts table
     * RUNTIME ERROR parts would be added to list but wouldn't retain this information. added a temporary product called temp to add it to the associataed parts list then updates when product is saved.
     * FUTURE ENHANCEMENTS add a way to select multiple parts using ctrl on keyboard
     * @param actionEvent
     */
    public void onAdd(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        if(selectedPart != null){
            //This adds the associated part to the list in Product.java
            temp = new Product(1,"temp",1,1,1,1,associatedParts);
            temp.addAssociatedPart(selectedPart);

           updateaPartsTable();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No selected Part");
            alert.setContentText("You must select a part for this button to work ;)");
            alert.showAndWait();
        }
    }

    /**
     * updates associated parts table to see parts added from onadd method
      */
    private void updateaPartsTable() { aPartsTable.setItems(associatedParts);
    }

    /**
     * remove parts method
     * removes a part from the table associated parts
     * RUNTIME ERROR Did not ask user to confirm the removal at first. Corrected this by adding an alert with an optional button
     * FUTURE ENHANCEMENTS add a way to save their selection so they do not have to keep confirming the remove part pop up
     * @param actionEvent
     */
    public void onRemoveParts(ActionEvent actionEvent) {
        Part selectedPart = (Part) aPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete this part");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //User chooses to delete
                associatedParts.remove(selectedPart);
                updateaPartsTable();
            } else {
                alert.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No selected Part");
            alert.setContentText("You must select a part for this button to work :)");
            alert.showAndWait();
        }
    }

    /**
     * on cancel method brings user back to mainform screen
     * @param actionEvent
     * @throws IOException
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        associatedParts.removeAll();
        Inventory.deleteProduct(temp);
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * onsave method this will add the product and associated parts list to the product class
     * RUNTIME ERRORS generated the id incorrectly using size of products list. Corrected this by using a static int firstID then adding a 1 everytime onsave is clicked
     * FUTURE ENHANCEMENTS add a way to save multiple products instead of returning to mainform everytime you want add a product
     * @param actionEvent
     */
    public void onSave(ActionEvent actionEvent) {
        //this generates the unique id for every new product
        firstID++;
        int id = firstID;

        //this makes sure every textfield input is valid
        try{
            String Name = nameTxt.getText();
            double price = Double.parseDouble(priceTxt.getText());
            int stock = Integer.parseInt(inventoryTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            if (max < min){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Something went Wrong :(");
                alert.setContentText("Your max value may not be less than the min value");
                alert.showAndWait();
            }
            else if (stock < min || stock > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Something went Wrong :(");
                alert.setContentText("Your inv level may not be greater than max or less than min");
                alert.showAndWait();
            }else if (min < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Your Min value must be greater than or equal to 0 :(");

                alert.showAndWait();
            }
            else
            {
                Product p = new Product(id,Name,price,stock,min,max,associatedParts);
                Inventory.addProduct(p);


                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();

            }

        }catch (NumberFormatException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went Wrong :(");
            alert.setContentText("Check your fields for the correct inputs ;)");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * sets partstable items
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
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
}
