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
 * @author Dustin Waite
 *
 * control logic for Modify Product controller
 */
public class ModifyProductController implements Initializable {

    /**
     * table, columns, text fields, buttons, and labels from fxml file
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
    @FXML
    private ObservableList<Part> assParts = FXCollections.observableArrayList();

    /**
     * index for indicating what Product is being modified
     */
    public int productIndex;

    /**
     * directs to main menu.
     *
     * @param actionEvent cancel button clicked
     * @throws IOException
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
     * Connects and sends data from main menu
     *
     * @param selected product from main screen table
     */
    public void sendProduct(Product selected){

        assParts.addAll(selected.getAssociatedParts());
        assPartsTable.setItems(assParts);

        productIndex = Inventory.getTheProducts().indexOf(selected);
        productIdField.setText(String.valueOf(selected.getId()));
        productNameField.setText(selected.getName());
        productInventoryField.setText(String.valueOf(selected.getStock()));
        productPriceField.setText(String.valueOf(selected.getPrice()));
        productMaxField.setText(String.valueOf(selected.getMax()));
        productMinField.setText(String.valueOf(selected.getMin()));

    }

    /**
     * adds parts from part table to associated parts table
     * sends error alert if no part is selected
     *
     * @param event add button click
     */
    @FXML
    public void addButton(ActionEvent event) {
        Part select = (Part) partTable.getSelectionModel().getSelectedItem();

        if (select != null && !assParts.contains(select)) {
            assParts.add(select);
            assPartsTable.setItems(assParts);

        } else if (select == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("selection not made");
            alert.showAndWait();
        }
    }

    /**
     * removes associated part from associated parts table view
     * display error shows if no associated part is selected
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
     * shows confirmation alert and redirects to main menu
     *
     * @param actionEvent cancel button click
     * @throws IOException fxmlloader
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
     * searches parts by ID or Name
     * returns an error if it finds null results
     * if search field is empty all values will populate
     *
     * @param actionEvent search button click
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
     * saves inputted product fields and selected associated parts to inventory
     *
     * @param event save button click
     */
    @FXML
    void saveProduct(ActionEvent event) {

        try {

            int id = Integer.parseInt(productIdField.getText());
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

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("must set min above zero and less than max.");
                alert.showAndWait();
                return;
            }
            if (stock < min || stock > max) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Inventory must be set above min and below max");
                alert.showAndWait();
                return;
            }

            Product mp = new Product(id, name, cost, stock, min, max);
            mp.setId(id);
            mp.getAssociatedParts().clear();
            mp.getAssociatedParts().addAll(assParts);
            Inventory.updateProduct(productIndex, mp);

               toMainMenu(event);

        } catch (Exception error) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("incorrect values were submitted.");
            alert.showAndWait();
        }
    }

    /**
     * initializes and populates part and associated parts table
     *
     * @param url location used to direct paths for root object
     * @param resourceBundle information from selected MainScreen product
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
