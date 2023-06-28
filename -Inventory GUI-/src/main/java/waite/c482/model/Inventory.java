package waite.c482.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Dustin Waite
 *
 * models inventory for the parts and products.
 *
 * creates permenance and allows all controllers to successfully share information.
 */

public class Inventory {

    /**
     * list of the parts.
     */
    private static ObservableList<Part> theParts = FXCollections.observableArrayList();

    /**
     * adds new part to part list.
     * @param newPart
     */
    public static void addPart(Part newPart){

        theParts.add(newPart);
    }

    /**
     * the part getter.
     * @return
     */
    public static ObservableList<Part> getTheParts(){

        return theParts;
    }

    /**
     * creates a list for the products.
     */
    private static ObservableList<Product> theProducts = FXCollections.observableArrayList();

    /**
     * the products getter.
     * @return
     */
    public static ObservableList<Product> getTheProducts(){

        return theProducts;
    }

    /**
     * the product adder.
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        theProducts.add(newProduct);
    }

    /**
     * searches parts list by name or partial name
     *
     * @param partialName
     * @return part name
     */
    public static ObservableList<Part> searchByPartName(String partialName) {

        ObservableList<Part> partName = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getTheParts();

        for(Part part : allParts){

            if(part.getName().contains(partialName)){

                partName.add(part);
            }
        }

        return partName;
    }

    /**
     * search products list my name or partial name
     * @param partialName
     * @return product name
     */
    public static ObservableList<Product> searchByProductName(String partialName) {
        ObservableList<Product> productName = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getTheProducts();

        for(Product product : allProducts){
            if(product.getName().contains(partialName)){
                productName.add(product);
            }
        }

        return productName;
    }

    /**
     * searches part list by part ID
     *
     * @param partID
     * @return Part or null
     */
    public static Part searchByPartID(int partID){
        ObservableList<Part> allParts = Inventory.getTheParts();

        for(int i = 0; i < allParts.size(); i++){
            Part p = theParts.get(i);

            if(p.getId() == partID){
                return p;
            }
        }
        return null;
    }

    /**
     * searches product list by product ID
     *
     * @param productID
     * @return product or null
     */
    public static Product searchByProductID(int productID){
        ObservableList<Product> allProducts = Inventory.getTheProducts();

        for(int i = 0; i < allProducts.size(); i++){
            Product p = theProducts.get(i);

            if (p.getId() == productID){
                return p;
            }
        }
        return null;
    }

    /**
     * deletes selected part data from list
     *
     * @param selected part
     * @return table without selected part
     */
    public static boolean deletePart(Part selected) {
        return theParts.remove(selected);
    }

    /**
     * deletes selected product data from list
     *
     * @param selected product
     * @return table without selected product
     */
    public static boolean deleteProduct(Product selected){
        return theProducts.remove(selected);
    }

    /**
     * replaces selected part data with new inputted data
     *
     * @param index of selected part
     * @param selected part
     */
    public static void updatePart(int index, Part selected) {
        theParts.set(index, selected);
    }

    /**
     * replaces selected product data with new inputted data
     *
     * @param index of selected product
     * @param selected product
     */
    public static void updateProduct(int index, Product selected) {
        theProducts.set(index, selected);
    }

}

