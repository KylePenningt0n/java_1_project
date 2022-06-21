package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;

/**
 * modifypartsform class
 */
public class ModifyPartsForm {
    public TextField ID;
    public RadioButton inHouse;
    public ToggleGroup partType;
    public RadioButton outSourced;
    public Label labelSource;
    public TextField nameTxt;
    public TextField inventoryTxt;
    public TextField priceTxt;
    public TextField maxTxt;
    public TextField machineTxt;
    public TextField minTxt;
    public Button cancel;
    public Button save;


    public Part selectedPart;
    private int partID;
    public Object scene;
    public Stage stage;

    /**
     * updates the modified part
     * RUNTIME ERRORS entered negative values and wouldnt tell whats wrong. fixed this by adding a check for it
     * FUTURE ENHANCEMENTS account for different languages in text boxes
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
            } else if (min < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Your Min value must be greater than or equal to 0 :(");

                alert.showAndWait();
            }
            else {

                int id = Integer.parseInt(ID.getText());
                String name = nameTxt.getText();
                double price = Double.parseDouble(priceTxt.getText());
                if (inHouse.isSelected()) {
                    try {
                        int machineID = Integer.parseInt(machineTxt.getText());
                        InHouse i = new InHouse(id, name, price, stock, min, max, machineID);
                        Inventory.updatePart(partID, i);

                        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
                        stage.setScene(new Scene((Parent) scene));
                        stage.show();

                    } catch (NumberFormatException n) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setContentText("You may not put letters as an id in the machineID box");
                        alert.showAndWait();
                    }
                } else {
                    String companyName = machineTxt.getText();
                    Outsourced o = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(partID, o);

                    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
                    stage.setScene(new Scene((Parent) scene));
                    stage.show();

                }
            }
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Something went Wrong :(");

            alert.showAndWait();
        }
    }


    /**
     * method for cancelling and going back to main screen
     * RUNTIME ERRORS mistyped path to mainform, corrected with typing infor correctly
     * FUTURE ENHANCEMENTS ask user to make sure
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
     * method to set text to machine id
     * @param actionEvent
     */
    public void onHouse(ActionEvent actionEvent) {
        labelSource.setText("Machine ID");
    }

    /**
     * method to set text to company name
     * @param actionEvent
     */
    public void onOutSourced(ActionEvent actionEvent) {
        labelSource.setText("Company Name");
    }

    /**
     * set part method to add selected part from previous screen
     * RUNTIME ERRORS none
     * FUTURE ENHANCEMENTS n/a
     * @param selectedPart
     */
    public void setPart(Part selectedPart) {
        this.selectedPart = selectedPart;
        partID = Inventory.getAllParts().indexOf(selectedPart);
        ID.setText(Integer.toString(selectedPart.getId()));
        nameTxt.setText(selectedPart.getName());
        inventoryTxt.setText(Integer.toString(selectedPart.getStock()));
        priceTxt.setText(Double.toString(selectedPart.getPrice()));
        maxTxt.setText(Integer.toString(selectedPart.getMax()));
        minTxt.setText(Integer.toString(selectedPart.getMin()));
        if(selectedPart instanceof InHouse i){
            inHouse.setSelected(true);
            this.labelSource.setText("Machine ID");
            machineTxt.setText(Integer.toString(i.getMachineId()));
        }
        else{
            Outsourced o = (Outsourced) selectedPart;
            outSourced.setSelected(true);
            this.labelSource.setText("Company Name");
            machineTxt.setText(o.getCompanyName());
        }


        
    }
}
