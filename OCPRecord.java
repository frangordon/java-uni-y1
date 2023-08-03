/**
 * Fran Gordon
 *
 * The OCPRecord creates items and the associated ProductID number
 * These are passed to the catalogue to be saved
 *
 */

import java.io.*;

public class OCPRecord implements Serializable {

    public ComponentType componentType;
    public InterfaceType interfaceType;
    public String manufacturer;
    public Float price;
    public String description;
    private String productID;
    private static int serialNumber = 1;

    public OCPRecord() {

    }

    /**
     * Setters
     */
    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public void setInterfaceType(InterfaceType interfaceType) {
        this.interfaceType = interfaceType;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getters
     */
    public String getProductID() {
        return productID;
    }

    /**
     * toString shows user the contents of OCP record
     */
    @Override
    public String toString() {
        return "Product is " + "\"" + componentType.getMessage() + " " + description + " with interface " + interfaceType.getMessage() + " manufactured by " + manufacturer + " at price £" + price + "\". ";
    }


    /**
     * Generates productID
     */
    public String generateProductID(int serialNumber) {
        String manufacturerID = manufacturer.substring(0, Math.min(manufacturer.length(), 4)).toUpperCase();
        manufacturerID = String.format("%-4s", manufacturerID).replace(" ", "x");
        String serial = String.format("%04d", serialNumber);
        productID = manufacturerID + serial;
        return productID;
    }

}
