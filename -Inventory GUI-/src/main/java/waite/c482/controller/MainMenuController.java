package waite.c482.controller;

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

import static waite.c482.model.Inventory.*;

/**
 * @author Dustin Waite
 *
 * Controller for main menu, provides logic.
 */
public class MainMenuController implements Initializable {

    /**
     * table, columns, text fields, buttons, and labels from fxml file
     */

    public TextField partSearch;
    public Button searchPartButton;
    public TextField productSearch;
    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableColumn<Part, Integer> partID;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partInventoryLevel;

    @FXML
    private TableColumn<Part, Integer> partCostPerUnit;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Part, Integer> productID;

    @FXML
    private TableColumn<Part, String> productName;

    @FXML
    private TableColumn<Part, Integer> productInventoryLevel;

    @FXML
    private TableColumn<Part, Integer> productCostPerUnit;

    /**
     * directs user to add part menu, to add part to inventory
     *
     * @param actionEvent add button click
     * @throws IOException from fxmlloader
     */
    public void toAddPartMenu(ActionEvent actionEvent) throws IOException {
        Parent addPart = FXMLLoader.load(getClass().getResource("/waite/c482/AddPartView.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(addPart);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * directs user to add product menu, to add product to inventory
     *
     * @param actionEvent add product button click
     * @throws IOException from fxmlloader
     */
    public void toAddProductMenu(ActionEvent actionEvent) throws IOException {


        Parent addProduct = FXMLLoader.load(getClass().getResource("/waite/c482/AddProductView.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(addProduct);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * stops test data from populating tables multiple times
     */
    private static boolean firstTime = true;

    /**
     * populates part and product tables with values to test program
     */
    static void addTestData() {

        if (!firstTime) {
            return;
        }
        firstTime = false;

        Outsourced wheel = new Outsourced(1, "Wheel", 14.99, 7, 1, 3, "BIG");
        Inventory.addPart(wheel);
        Inhouse spokes = new Inhouse(2, "Spokes", 9.99, 2, 0, 10, 1);
        Inventory.addPart(spokes);
        Inhouse helmet = new Inhouse(3, "Helmet", 29.99, 20, 50, 100, 2);
        Inventory.addPart(helmet);
        Outsourced brakes = new Outsourced(4, "Brakes", 34.99, 20, 25, 30, "Slowies");
        Inventory.addPart(brakes);
        Product megabike = new Product(1000, "MegaBike", 1999.99, 0, 0, 10);
        Inventory.addProduct(megabike);
        Product huffy = new Product(1001, "huffy", 149.78, 45, 10, 100);
        Inventory.addProduct(huffy);
    }

    /**
     * searches part table by part ID or name, if part search text field is empty
     * table will populate with all values
     *
     * if text field contains characters but no parts are found, function will display an error
     *
     * @param actionEvent part search button is clicked
     */
    public void searchPart(ActionEvent actionEvent) {
        String q = partSearch.getText();

        ObservableList<Part> parts = Inventory.searchByPartName(q);

        if (parts.size() == 0) {
            try {
                int partId = Integer.parseInt(q);
                Part p = searchByPartID(partId);
                if (p != null)
                    parts.add(p);

            } catch (NumberFormatException e) {
                //ignore
            }
        }
        if (parts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("unable to find any parts.");
            alert.showAndWait();
        }
        partTable.setItems(parts);
        partSearch.setText("");
    }

    /**
     * searches product table by part ID or name, if product search text field is empty
     * table will populate with all values
     *
     * if text field contains characters but no parts are found, function will display an error
     *
     * FUTURE ENHANCEMENT: add a value indicating, on the product table, whether there are associated part(s) included
     * in the product. Also, the parts table could display how many products are dependant on that part. This could help
     * with future searches.
     *
     * @param actionEvent product search button clicked
     */
    public void searchProduct(ActionEvent actionEvent) {
        String q = productSearch.getText();

        ObservableList<Product> products = Inventory.searchByProductName(q);

        if (products.size() == 0) {
            try {
                int productId = Integer.parseInt(q);
                Product p = searchByProductID(productId);

                if (p != null)
                    products.add(p);

            } catch (NumberFormatException e) {
            }
        }
        if (products.size() == 0) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("unable to find any parts.");
            alert.showAndWait();
        }

        productTable.setItems(products);
        productSearch.setText("");
    }

    /**
     * directs to modify part menu
     *
     * sends part data to modify parts window
     *
     * shows error if part isn't selected
     *
     * @param actionEvent modify part button clicked
     * @throws IOException From fxmlloader
     */
    public void modifyPartButton(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/waite/c482/ModifyPartView.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendPart(partTable.getSelectionModel().getSelectedIndex(), partTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Modify Part");
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("please select a product to modify.");
            alert.show();
        }
    }

    /**
     * directs to modify product menu
     * sends part data to modify product window
     * displays error if product isn't selected
     *
     * @param actionEvent modify product button clicked
     * @throws IOException from fxml loader
     */
    public void modifyProductButton(ActionEvent actionEvent) throws IOException {

        Product selected = productTable.getSelectionModel().getSelectedItem();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/waite/c482/ModifyProductView.fxml"));
            loader.load();

            ModifyProductController MPController = loader.getController();
            MPController.sendProduct(selected);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Modify Part");
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NullPointerException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a product to modify");
            alert.show();
        }
    }

    /**
     * removes part from part table
     * shows error window if no part is selected
     * shows confirmation window to confirm part is removed
     *
     * @param actionEvent delete part button clicked
     */
    public void deletePart(ActionEvent actionEvent) {
        Part selected = (Part) partTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select Part");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("remove part?");
            Optional<ButtonType> part = alert.showAndWait();

            if (part.isPresent() && part.get() == ButtonType.OK) {
                Inventory.deletePart(selected);
            }
        }
    }

    /**
     * removes product from product table
     * shows error window if no product is selected
     * shows confirmation window to ensure removing product
     *
     * @param actionEvent delete product button clicked
     */
    public void deleteProduct(ActionEvent actionEvent) {
        Product selected = productTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("please select a product to delete");
            alert.showAndWait();
        } else {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setContentText("confirm the deletion of selected product");
            Optional<ButtonType> p = alert1.showAndWait();
            if(p.isPresent() && p.get() == ButtonType.OK) {
                ObservableList<Part> assParts = selected.getAssociatedParts();

                if (assParts.size() > 0){
                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                    alert3.setContentText("this product is associated with parts and cannot be deleted");
                    alert3.showAndWait();
                } else {
                    Inventory.deleteProduct(selected);
                }
            }
        }}

    /**
     * ends the program
     *
     * @param Exit exit button click
     */
    public void exitProgram(ActionEvent Exit) {
        Stage stage = (Stage) ((Node) Exit.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * initializes controller, adds test data, and populates part and product table views
     *
     * @param url directs data
     * @param resourceBundle directed values
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addTestData();

        partTable.setItems(Inventory.getTheParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostPerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.getTheProducts());
        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostPerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}


