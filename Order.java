/**
 * Fran Gordon
 *
 * Order collects and sets the details for order fulfillment option in menu
 *
 * */

import java.time.LocalDate;


public class Order {

    private String locationID;
    private String productID;
    private int amount;
    private LocalDate collectionDate;
    private float price;

    public Order() {

    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getAmount() {
        return amount;
    }


    public void setAmount(int amount) {
        this.amount = amount;
    }

    // displays collection date
    public void setCollectionDateToday() {
        LocalDate localDate = LocalDate.now();
        this.collectionDate = localDate;
    }

    // displays collection date for different site
    public void setCollectionDate() {
        LocalDate localDate = LocalDate.now().plusDays(2);
        this.collectionDate = localDate;
    }


    public void setPrice(){
        this.price = price;
    }



    @Override
    public String toString() {
        return "The order can be fulfilled and collected on " + collectionDate + ". The total price is " + "£" + price;
    }

}
