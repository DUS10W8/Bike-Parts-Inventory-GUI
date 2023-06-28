package waite.c482.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import waite.c482.model.Inhouse;
import waite.c482.model.Inventory;
import waite.c482.model.Outsourced;
import java.io.IOException;


/**
 * @author Dustin Waite
 *
 * provides logic for add part controller
 */
public class AddPartController {

    /**
     * the list of Labels, buttons, and text fields that feature on the add part menu
     */

    public Label changeMe;
    public RadioButton OutsourcedRadio;
    public RadioButton inHouseRadio;
    public TextField partNameField;
    public TextField partInventoryField;
    public TextField partPriceField;
    public TextField partMaxField;
    public TextField partToggleID;
    public TextField partMinField;
    public Button savePartButton;
    public TextField partIdField;

    /**
     * directs user to MainMenu
     * @param actionEvent cancel button is clicked
     * @throws IOException for I/O errors
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/waite/c482/MainMenuView.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * toggles label to machine ID if Inhouse radio button is selected
     *
     * @param actionEvent Inhouse radio button is clicked
     */
    public void onInhouse(ActionEvent actionEvent) {
        changeMe.setText("machine ID");
    }

    /**
     * toggles label to company name if outsourced radio button is selected
     *
     * @param actionEvent Outsourced radio button is clicked
     */
    public void onOutsourced(ActionEvent actionEvent) {
        changeMe.setText("company name");
    }

    /**
     * increments an ID for new part
     * saves a new part and it's fields in the inventory and loads the Main Menu
     * ensures valid data input
     *
     * displays alerts if fields are empty
     * displays alerts when min is equal or less than zero or min is equal or greater than max
     * displays alerts when stock is less than min or greater than max
     *
     * if Inhouse is selected, the text field's contents will be saved to machineId.
     * if Outsourced is selected, the text field's contents will be saved to companyName
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void savePart(ActionEvent event) throws IOException {

        int ID = 0;
        for (int i = 0; i < Inventory.getTheParts().size(); i++) {
            if (ID <= Inventory.getTheParts().get(i).getId())
                ID = Inventory.getTheParts().get(i).getId() + 1;
        }
        try {
            String name = partNameField.getText();
            Double cost = Double.parseDouble(partPriceField.getText());
            int stock = Integer.parseInt(partInventoryField.getText());
            int min = Integer.parseInt(partMinField.getText());
            int max = Integer.parseInt(partMaxField.getText());
            int machineId;
            String companyName;

            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in name field.");
                alert.showAndWait();
                return;
            }
                if (min <= 0 || min >= max) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Must set min above zero and less than max.");
                    alert.showAndWait();
                    return;
                }
                if (stock < min || stock > max) {

                    Alert stockAlert = new Alert(Alert.AlertType.ERROR);
                    stockAlert.setContentText("stock amount must be more than min and less than max.");
                    stockAlert.showAndWait();
                    return;
                }
                    if (inHouseRadio.isSelected()) {

                        machineId = Integer.parseInt(partToggleID.getText());
                        Inventory.addPart(new Inhouse(ID, name, cost, stock, min, max, machineId));
                    }

                   else if (OutsourcedRadio.isSelected()) {
                        companyName = partToggleID.getText();
                        Inventory.addPart(new Outsourced(ID, name, cost, stock, min, max, companyName));
                    }
                    toMainMenu(event);
                }
            catch (Exception error) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("incorrect values in submitted.");
            alert.showAndWait();
            }
        }
}
