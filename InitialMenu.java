/**
 * Fran Gordon
 *
 * InitalMenu class holds enums and logic for the initial or 'main menu'
 *
 * */

public enum InitialMenu {
    ERROR ("Error"),
    ADD ("Add a new component product to the catalogue"),
    VIEW_PRODUCT_IDS("View all product IDs"),
    VIEW_PRODUCT_DETAILS("View product details"),
    CHECK_STOCK_LEVELS("Check product stock levels"),
    CHANGE_STOCK_LEVELS("Change product stock levels"),
    ORDER("Order fulfilment"),
    QUIT ("Quit");

    String message;


    InitialMenu(String message){
        this.message = message;
    }


    /**
     * Displays the initial menu - displays enums as string
     * */
    public static String initialMenuAsString(){
        String s = "";

        InitialMenu[] initialMenuOptions = InitialMenu.values();
        for (int i = 1; i< initialMenuOptions.length; i++)
        {
            s += i + ". " + initialMenuOptions[i].message + "\n";
        }

        return s + ">>> ";
    }

    public static InitialMenu getInitialMenuOption(int i){
        return InitialMenu.values()[i];
    }


    @Override
    public String toString(){
        return "InitialMenu";
    }

}




