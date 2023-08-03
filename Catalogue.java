/**
 * Fran Gordon
 *
 * Catalogue class holds OCP Records
 *
 * Where serialization and deserialization of those objects is done
 **/

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Catalogue implements Serializable {

    private Map<String, OCPRecord> productDetailsMap;


    public Catalogue() {
        productDetailsMap = new HashMap<>();
    }


    public ArrayList<String> getProductIDs() {
        ArrayList<String> productIDs = new ArrayList<>();
        for (Map.Entry<String, OCPRecord> entry : productDetailsMap.entrySet()) {
            productIDs.add(entry.getKey());
        }
        return productIDs;
    }


    /**
     * adds OCPRecord to catalogue to map
     * */
    public void addOCPRecordToCatalogue(OCPRecord ocpRecord) {
        productDetailsMap.put(ocpRecord.getProductID(), ocpRecord);
    }

    /**
     * adds product details
     * @param productID add to map
     * @param productDetails add to map
     * */
    public void addProductDetails(String productID, OCPRecord productDetails) {
        productDetailsMap.put(productID, productDetails);
    }

    /**
     * get product details (ocp record) from map
     * */
    public OCPRecord getProductDetails(String productID) {
        return productDetailsMap.get(productID);
    }

    /**
     * checks if map contains productID
     * */
    public boolean containsProductID(String productID) {
        return productDetailsMap.containsKey(productID);
    }

    /**
     * Get next available serial no - reads product size and adds 1
     * */
    public int getNextAvailableSerialNumber() {

        return productDetailsMap.size() + 1;
    }

    /**
     * Save object into file (serialised)
     *
     * @param filePath String used to find file to save object to.
     */
    public void save(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Load method - deserializes the catalogue when we start the program
     *
     * @param filePath String finds the filePath we read from (catalogue)
     * @return Null if we get an exception or the deserialized object.
     */
    public static Catalogue load(String filePath) {
        Object catalogue = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            catalogue = ois.readObject();
        } catch (FileNotFoundException e) {
            catalogue = new Catalogue();
        } catch (Exception e) {
            System.out.println(e);
        }
        return (Catalogue) catalogue;
    }

    @Override
    public String toString() {
        return ""+
                this.getProductIDs()
                ;
    }

}
