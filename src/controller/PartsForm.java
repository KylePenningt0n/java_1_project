package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.util.UUID;
import java.util.random.RandomGenerator;

/**
 * main class add parts form
 * RUNTIME ERROR id was generated from amount of items in the list so ids were not unique. corrected this by setting a static int then adding a 1 everytime i need a new part
 * FUTURE ENHANCEMENTS could add a hybrid inhouse/out sourced part where we can make the part or get it from someone else
 */
public class PartsForm {
    public Label labelSource;
    public TextField nameTxt;
    public TextField inventoryTxt;
    public TextField priceTxt;
    public TextField maxTxt;
    public TextField machineTxt;
    public TextField minTxt;
    public Button cancel;
    public Button save;
    public RadioButton inHouse;
    public ToggleGroup partType;
    public RadioButton outSourced;

    private Stage stage;
    private Object scene;


    //this provides an id number to change so evey part id is unique
    static int firstId = 3;

    /**
     * cancel method for button
     * @param actionEvent
     * @throws IOException
     */
    public void onCancel(ActionEvent actionEvent)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * onsave creates part object then updates the adds part to list
     * RUNTIME ERRORS generate id an incorrect way by going off number of parts in list. corrected with using a static int and adding to it everytime
     * FUTURE ENHANCEMENTS show the added part on a popup screen to confirm the on save process
     * @param actionEvent
     */
    public void onSave(ActionEvent actionEvent){
        try {
            //generates unique id for new part
            firstId++;
            int id = firstId;


            //adds the rest of the user inputs
            String name = nameTxt.getText();
            double price = Double.parseDouble(priceTxt.getText());
            int stock = Integer.parseInt(inventoryTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());

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
            } else if (min < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Your Min value must be greater than or equal to 0 :(");

                alert.showAndWait();
            }
            else {
                if (inHouse.isSelected()) {
                    int machineID = Integer.parseInt(machineTxt.getText());
                    InHouse i = new InHouse(id, name, price, stock, min, max, machineID);
                    Inventory.addPart(i);
                }
                else {
                    String companyName = machineTxt.getText();
                    Outsourced o = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(o);
                }
                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
            } catch(Exception exception){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something went Wrong :(");
                alert.setContentText("Check fields for correct inputs");

                alert.showAndWait();
            }
    }

// these two methods change the text of the label for Machine ID and Company name

    /**
     * changes the text to machine ID when inhouse radio button is pressed
     * RUNTIME ERRORS misspelled inhouse to onhouse corrected this by using refactor tool
     * @param actionEvent
     */
    public void inHouse(ActionEvent actionEvent) {
        labelSource.setText("Machine ID");
    }

    /**
     * changes the text to company name when outsources radio button is pressed
     * @param actionEvent
     */
    public void onOutSourced(ActionEvent actionEvent) {
        labelSource.setText("Company Name");
    }
}
