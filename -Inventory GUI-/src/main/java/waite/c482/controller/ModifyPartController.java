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
import waite.c482.model.Part;
import java.io.IOException;

/**
 * @author Dustin Waite
 *
 * provides control logic for modify part controller
 */
public class ModifyPartController {

    /**
     * table, columns, text fields, buttons, and labels from fxml file
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
    private int Index = 0;

    /**
     * connects information to main menu
     *
     * sets index, fields, and radio buttons to the selected part in the main menu
     *
     *
     * @param selectedIndex
     * @param selected
     */
    public void sendPart(int selectedIndex, Part selected) {

        Index = selectedIndex;

        partIdField.setText(String.valueOf(selected.getId()));
        partNameField.setText(selected.getName());
        partInventoryField.setText(String.valueOf(selected.getStock()));
        partPriceField.setText(String.valueOf(selected.getPrice()));
        partMaxField.setText(String.valueOf(selected.getMax()));
        partMinField.setText(String.valueOf(selected.getMin()));

        if (selected instanceof Inhouse) {

            Inhouse i = (Inhouse) selected;
            inHouseRadio.setSelected(true);
            this.changeMe.setText("Machine ID");
            partToggleID.setText(String.valueOf(i.getMachineId()));
        }
        else {

            Outsourced o = (Outsourced) selected;
            OutsourcedRadio.setSelected(true);
            this.changeMe.setText("CompanyName");
            partToggleID.setText(String.valueOf(o.getCompanyName()));
        }
    }

    /**
     * saves modified part to inventory and redirects to main menu
     * out of bounds, empty, or invalid values result in an error display
     *
     * @param event save button click
     * @throws IOException FXMLLoader
     */
    @FXML
    void savePart(ActionEvent event) throws IOException {

        try {
            int ID = Integer.parseInt(partIdField.getText());
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


            } else if(min <= 0 || min >= max) {

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

                    machineId = Integer.parseInt(partIdField.getText());
                    Inhouse update = new Inhouse(ID, name, cost, stock, min, max, machineId);
                    Inventory.updatePart(Index, update);

                }

                if (OutsourcedRadio.isSelected()) {
                    companyName = partToggleID.getText();
                    Outsourced update = new Outsourced(ID, name, cost, stock, min, max, companyName);
                    Inventory.updatePart(Index, update);
                }
                toMainMenu(event);
            }catch(NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("fields are blank, or incorrect values are submitted.");
                alert.showAndWait();
                return;
            }
        }

    /**
     * directs Main Menu controller
     *
     * @param actionEvent cancel button clicked
     * @throws IOException
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/waite/c482/MainMenuView.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * changes label to machine ID when radio button selected.
     * @param actionEvent
     */
    public void onInhouse(ActionEvent actionEvent) {
        changeMe.setText("machine ID");
    }

    /**
     * changes label to company name when radio button is selected.
     * @param actionEvent
     */
    public void onOutsourced(ActionEvent actionEvent) {
        changeMe.setText("company name");
    }

}
