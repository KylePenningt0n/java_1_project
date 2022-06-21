package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * This is the class for the Inventory System
 * RUNTIME ERRORS accidentally setup test parts on wrong observable list, fixed this by adding an empty list
 * FUTURE ENHANCEMENTS could add other items besides parts and products
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * adds a part to the allparts observable list
     * @param newPart
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * This calls the method add test items to provide the test data
     */
    static {
        addTestItems();
    }

    /**
     * This adds all the test items such as products and parts
     */
    private static void addTestItems() {
        ObservableList<Part> emptyList = FXCollections.observableArrayList();

        InHouse a = new InHouse(1,"Flywheel",500.00,2,0,5,1);
        Inventory.addPart(a);
        InHouse b = new InHouse(2,"Brakes",250.99,3,1,5,2);
        Inventory.addPart(b);
        Outsourced c = new Outsourced(3,"Shock Sensor",24.50,0,0,2,"Bosh");
        Inventory.addPart(c);
        Product d = new Product(1,"Nissan",24999.99,1,0,15, emptyList);
        Inventory.addProduct(d);
        Product e = new Product(2,"Ford",64000.00,2,0,5, emptyList);
        Inventory.addProduct(e);
        Product f = new Product(3,"Fiat",14789.99,1,0,3, emptyList);
        Inventory.addProduct(f);
    }

    /**
     * Provides a way to lookup parts based of id number
     * @param partID
     * @return list of parts
     */
    public static Part lookupPart(int partID){
        ObservableList<Part> allParts = getAllParts();

        for (Part pa : allParts){
            if(pa.getId() == partID){
                return pa;
            }
        }
        return null;
    }

    /**
     * provides a way to lookup parts based of name
     * @param partName
     * @return list of parts
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part>partsBack = FXCollections.observableArrayList();

        ObservableList<Part>allParts = getAllParts();
        for (Part pa : allParts){
            if (pa.getName().contains(partName)){
                partsBack.add(pa);
            }
        }
        return partsBack;

    }

    /**
     * provides a way to search products by ID
     * @param productId
     * @return product list
     */
    public static Product lookupProduct(int productId){
        ObservableList<Product> allProducts = getAllProducts();

        for(Product pr: allProducts){
            if(pr.getId() == productId){
                return pr;
            }
        }
        return null;

    }

    /**
     * Provides a way to search products by name
     * @param productName
     * @return product lsit
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> productsBack = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = getAllProducts();
        for(Product pr : allProducts){
            if(pr.getName().contains(productName)){
                productsBack.add(pr);
            }
        }
        return productsBack;


    }

    /**
     * gets allparts list
     * @return
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * gets all products list
     * @return
     */
    public static ObservableList<Product>getAllProducts(){
        return allProducts;
    }

    /**
     * adds new product to all products list
     * @param newProduct
     */
    public static void addProduct(Product newProduct){

        allProducts.add(newProduct);
    }

    /**
     * updates part based off index # and uses the selected part
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index,Part selectedPart){
        allParts.set(index, selectedPart);

    }

    /**
     * updates products based off index # and uses the selected product
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index,Product newProduct){
        allProducts.set(index,newProduct);

    }

    /**
     * removes a part from the allparts list
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
        }

    /**
     * removes a product from the allproducts list
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }


}
