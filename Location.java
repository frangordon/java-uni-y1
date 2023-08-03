/**
 * Fran Gordon
 *
 * Location class represents a specific location (eg Glasgow, Edinburgh, Gourock) -
 * and holds a list of that inventory
 *
 **/
import java.io.Serializable;
import java.util.*;

public class Location implements Serializable {
    private String locationID;
    private Map<String, Integer> inventory;

    public Location(String locationID){
        this.locationID = locationID;
        this.inventory = new HashMap<>();
    }

    /**
     * get locationID eg Glasgow, Edinburgh, Gourock
     **/
    public String getLocationID() {
        return locationID;
    }

    /**
     * get inventory from map
     **/
    public ArrayList<String> getInventory() {
        ArrayList<String> itemsInStock = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            itemsInStock.add(entry.getKey());
        }
        return itemsInStock;
    }

    /**
     * Boolean to check if the ProductID exists in the inventory list
     **/
    public boolean containsProductID(String productID) {
        return inventory.containsKey(productID);
    }

    /**
     * get current stock level and add new stock level to it
     **/
    public void addStock(String productID, int quantity)
    {
        int currentStock = getStockLevel(productID);
        int newStock = currentStock + quantity;
        inventory.put(productID, newStock);
    }


    /**
     * deduct stock and update new stock level
     **/
    public void deductStock(String productID, int quantity)
    {
        if (inventory.containsKey(productID))
        {
            int newQuantity = inventory.get(productID) - quantity;
            inventory.put(productID, newQuantity);
        }
    }


    /**
     * gets stock level of productID in inventory list
     **/
    public int getStockLevel(String productID) {
        if (inventory.containsKey(productID))
        {
            return inventory.getOrDefault(productID, 0);
        }
        else
        {
            return 0;
        }
    }


    /**
     * toString that returns getInventory
     **/
    @Override
    public String toString()
    {
        return "" + this.getInventory();
    }


}
