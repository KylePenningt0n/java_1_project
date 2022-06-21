package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the class for a Product. This will give all the attributes and methods for a product.
 */
public class Product {
    private ObservableList<Part> aParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * RUNTIME ERROR had trouble adding parts to the associated list in product class from the add product screen and modify product screen. changed the names of lists to make them stand out better
     * FUTURE ENHANCEMENT could add more attributes to a product such as location in transit etc.
     * @param id id of the product
     * @param name name of product
     * @param price this is the price of the product
     * @param stock this is the stock level of the product
     * @param min this is the min value of a product
     * @param max this is the max value of a product
     * @param aParts this is the associated parts list
     */
    public Product(int id, String name, double price, int stock, int min, int max, ObservableList<Part> aParts){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.aParts = aParts;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {this.id = id;}

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     *
     * @param part adds associated part to aparts observable list
     */
    public void addAssociatedPart(Part part){
        aParts.add(part);

    }

    /**
     *
     * @return gets all associated parts from aparts list
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return aParts;
    }

    /**
     *
     * @param selectedAssociatedPart Did not use this method
     * @return did not have to use this method as it was easier to remove it from a list in the addProduct/modifyProduct screen :)
     */
   public boolean deleteAssociatedPart(Part selectedAssociatedPart){return aParts.remove(selectedAssociatedPart);

    }

}
