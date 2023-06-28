package waite.c482.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import waite.c482.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static waite.c482.model.Inventory.searchByPartID;

/**
 * provides logic for add product controller
 *
 * @author Dustin Waite
 */
public class AddProductController implements Initializable {

    /**
     * table, columns, text fields, buttons, and labels from fxml file.
     */
    public TextField partSearchField;

    public TableColumn partID;

    public TableColumn partName;

    public TableColumn partInventoryLevel;
    public TableColumn partCostPerUnit;
    public TableColumn assPartID;
    public TableColumn assPartInventoryLevel;
    public TableColumn assPartName;
    public TableColumn assPartCostPerUnit;
    public TableView partTable;
    public TableView assPartsTable;
    public Button removeButton;
    public TextField productIdField;
    public TextField productNameField;
    public TextField productInventoryField;
    public TextField productPriceField;
    public TextField productMaxField;
    public TextField productMinField;

    /**
     * keeps list of associated parts
     */
    @FXML
    private ObservableList<Part> assParts = FXCollections.observableArrayList();

    /**
     * redirects to main menu.
     *
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
     * saves a new product to inventory and re directs to the Main Menu
     * <p>
     * ensures valid data input and displays alerts
     *
     * @param actionEvent save button is clicked
     */
    @FXML
    void saveProduct(ActionEvent actionEvent) {
        int id = 0;

        for (int i = 0; i < Inventory.getTheProducts().size(); i++) {
            if (id <= Inventory.getTheProducts().get(i).getId())
                id = Inventory.getTheProducts().get(i).getId() + 1;
        }

        try {
            String name = productNameField.getText();
            Double cost = Double.parseDouble(productPriceField.getText());
            int stock = Integer.parseInt(productInventoryField.getText());
            int min = Integer.parseInt(productMinField.getText());
            int max = Integer.parseInt(productMaxField.getText());

            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in name field.");
                alert.showAndWait();
                return;
            }
            if (min <= 0 || min >= max) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("Must set min above zero and less than max");
                alert2.showAndWait();
                return;
            }
            if (stock < min || stock > max) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("inventory field must be more than min and less than max");
                alert2.showAndWait();
                return;
            }

            Product p = new Product(id, name, cost, stock, min, max);
            Inventory.addProduct(p);
            p.getAssociatedParts().addAll(assParts);
            toMainMenu(actionEvent);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid data entered");
            alert.showAndWait();
        }
    }


    /**
     * moves items from parts table to associated parts table
     *
     * if no part is selected, error message will show
     * @param actionEvent add button clicked
     */
    @FXML
    void addButton(ActionEvent actionEvent) {
        Part selected = (Part) partTable.getSelectionModel().getSelectedItem();

        if (selected != null && !assParts.contains(selected)) {
            assParts.add(selected);
            assPartsTable.setItems(assParts);
        }

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must select associated part to add.");
            alert.showAndWait();
        }
    }

    /**
     * remove associated part from associated parts table
     *
     * if no associated part is selected, will show error
     * confirms associated part deletion
     *
     * @param event remove button clicked
     */
    @FXML
    void removeButton(ActionEvent event) {
        Part selected = (Part) assPartsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must select associated part to remove.");
            alert.showAndWait();

        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("remove part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                assParts.remove(selected);
                assPartsTable.setItems(assParts);

        }
    }}

    /**
     * confirms exiting AddProductController and re directs to Main Menu
     *
     * @param actionEvent cancel button clicked
     * @throws IOException from fxmlloader
     */
    public void cancelButton(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setContentText("proceed to main screen and cancel changes?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            toMainMenu(actionEvent);
        }
    }

    /**
     * searches values in part search text field by name and populates the table with results
     * if no values are found will search field for possible IDs
     * if text field is blank, it will populate table with all parts
     * if field has integers or letters are in the field but no values are found an alert will display indicating there are no results
     *
     * @param actionEvent search button clicked
     */
    public void searchButton(ActionEvent actionEvent) {
    String q = partSearchField.getText();

    ObservableList<Part> parts = Inventory.searchByPartName(q);

    if(parts.size() == 0){
        try{
            int partId = Integer.parseInt(q);
            Part p = searchByPartID(partId);

            if(p != null)
                parts.add(p);

        }
        catch (NumberFormatException e)
        {
            //ignore
        }
    }if (parts.size() == 0){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("unable to find any parts.");
        alert.showAndWait();
    }
    partTable.setItems(parts);
    partSearchField.setText("");
}

    /**
     * initializes and populates table and columns
     *
     * adds data to table columns
     *
     * @param url directs paths for the root objects, or null if location is unknown.
     * @param resourceBundle resources used to load the root object, or null if the root object was not loaded.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTable.setItems(Inventory.getTheParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostPerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        assPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        assPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assPartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assPartCostPerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}



