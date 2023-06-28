package waite.c482.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Dustin Waite
 *
 * Model for a product with associated parts
 */
public class Product {
    /**
     * id for product
     */
    private int id;
    /**
     * name for product
     */
    private String name;
    /**
     * price of product
     */
    private double price;
    /**
     * stock of product
     */
    private int stock;
    /**
     * minimum amount of stock for product
     */
    private int min;
    /**
     * maximum amount of stock for product
     */
    private int max;
    /**
     * list of associated parts for the products
     *
     * LOGICAL ERROR: while using the modify or add products controller, when I added a part to the associated part table, the part would be added to every product.
     * after closely reviewing "The Parts Shuffle" video, I realized I made the associatedParts observable list public which resulted in the modification of every product's associatedParts.
     * By changing to private, only the selected product's associated parts would be added or removed.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Constructor for a new product
     *
     * @param id id of product
     * @param name name of product
     * @param price price of product
     * @param stock stock of product
     * @param min minimum amount of stock of product
     * @param max maximum amount of stock of product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * product id getter
     *
     * @return the product id
     */
    public int getId() {
        return id;
    }

    /**
     * id setter
     *
     * @param id set ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * name getter
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * name setter
     *
     * @param name set product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * price getter
     *
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * price setter
     *
     * @param price sets product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Stock getter
     *
     * @return stock of product
     */
    public int getStock() {
        return stock;
    }

    /**
     * stock setter
     *
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * max getter
     *
     * @return max level of product stock
     */
    public int getMax() {
        return max;
    }

    /**
     * max setter
     *
     * @param max sets max level of product stock
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * min getter
     *
     * @return minimum level of stock for product
     */
    public int getMin() { return min; }

    /**
     * min setter
     *
     * @param min sets min level of product stock
     */
    public void setMin(int min) { this.min = min; }

    /**
     * adds associated part
     *
     * @param part the part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * deletes part from associated parts list of the product.
     *
     * @param selectedAssociatedPart
     * @return boolean showing delete success
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * gets list all associated parts for product
     *
     * @return list of parts
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }


    }





