/**
 * Fran Gordon
 *
 * StockMenu class holds enums and logic for the stock
 * options that the user can select when adding, deducting or transfering stock
 *
 * */

public enum StockMenu {
    ERROR (""),
    ADD_STOCK ("Add stock to location"),
    DEDUCT_STOCK ("Deduct stock from location"),
    TRANSFER_STOCK ("Transfer stock");

    private String message;

    StockMenu(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    public static String stockOptionsAsString()
    {
        try {
            String s = "";
            StockMenu[] stockMenuMenus = StockMenu.values();
            for (int i = 1; i< stockMenuMenus.length; i++)
            {
                s += i + ". " + stockMenuMenus[i].message + "\n";
            }
            return s + ">>> ";

        }
        catch (NumberFormatException e){
            System.out.println("");
        }
        return null;
    }

}
