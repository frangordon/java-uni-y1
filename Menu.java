/**
 * Fran Gordon
 * Menu class holds logic for the menu displayed to the user
 */

import java.util.*;

public class Menu {

    private final Scanner userInput;
    private final String catalogueFileName = "catalogue.txt";
    private final String productIDsFileName = "productIDsFile";
    private final String inventoryFileName = "inventory.txt";
    private final Catalogue catalogue;
    private final LocationManager locationManager;

    public Menu() {
        userInput = new Scanner(System.in);
        this.catalogue = Catalogue.load(this.catalogueFileName);
        this.locationManager = LocationManager.load(this.inventoryFileName);
    }

    /**
     * displays initial menu
     */
    private void displayMenu() {
        System.out.print(InitialMenu.initialMenuAsString());
    }

    /**
     * initial menu selection
     */
    private InitialMenu getUserSelection() {
        String s = userInput.nextLine();
        try {
            int i = Integer.parseInt(s);

            if (i >= 1 && i <= InitialMenu.values().length) {
                return InitialMenu.getInitialMenuOption(i);
            } else {
                System.out.print("Invalid selection. Please select a number from the menu:");
            }
        } catch (NumberFormatException e) {
            System.out.print("Please type in a number");
        }
        return InitialMenu.ERROR;
    }

    /**
     * Switch statement that loops through menu
     */
    public void InitialMenuInput(InitialMenu initialMenu) {
        switch (initialMenu) {
            case ADD:
                add();
                break;
            case VIEW_PRODUCT_IDS:
                viewAllProductIDs();
                break;
            case VIEW_PRODUCT_DETAILS:
                viewProductDetails();
                break;
            case CHECK_STOCK_LEVELS:
                checkStockLevels();
                break;
            case CHANGE_STOCK_LEVELS:
                changeStockLevels();
                break;
            case ORDER:
                createOrder();
                break;
            case QUIT:
                System.out.println("Shutting down");
                break;
            default:
                System.out.println(" ");
                break;
        }
    }

    /**
     * Displays Product IDs held in catalogue to user
     */
    public void viewAllProductIDs() {
        System.out.println("Product IDs currently in catalogue:");
        for (String productID : this.catalogue.getProductIDs()) {
            System.out.println(productID);
        }
        System.out.println();

        System.out.println("Press any key to return to main menu...");
        try {
            userInput.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * View the details for a product if the productID is entered
     */
    public void viewProductDetails() {
        System.out.println("Enter product ID:");
        String productID = userInput.nextLine();

        try {
            productID = productID.toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.catalogue.containsProductID(productID)) {
            OCPRecord selectedProductDetails = this.catalogue.getProductDetails(productID);
            if (selectedProductDetails.componentType.equals(ComponentType.MOTHERBOARD)) {
                MotherboardOCPRecord motherboardOCPRecord = (MotherboardOCPRecord) selectedProductDetails;
                System.out.println("Manufacturer: " + motherboardOCPRecord.manufacturer);
                System.out.println("Component Type: " + motherboardOCPRecord.componentType.getMessage());
                System.out.println("CPU Interface: " + motherboardOCPRecord.cpuInterface.getMessage());
                System.out.println("Memory Interface: " + motherboardOCPRecord.memoryInterface.getMessage());
                System.out.println("Storage Interface: " + motherboardOCPRecord.storageInterface.getMessage());
                System.out.println("PSU Interface: " + motherboardOCPRecord.psuInterface.getMessage());
                System.out.println("Description: " + motherboardOCPRecord.description);
                System.out.println("Price: " + motherboardOCPRecord.price);

            } else {
                System.out.println("Manufacturer: " + selectedProductDetails.manufacturer);
                System.out.println("Component Type: " + selectedProductDetails.componentType.getMessage());
                System.out.println("Interface: " + selectedProductDetails.interfaceType.getMessage());
                System.out.println("Description: " + selectedProductDetails.description);
                System.out.println("Price: " + selectedProductDetails.price);
            }

        } else {
            System.out.println("Product ID not found");
        }
        System.out.println("Press any key to return to main menu...");
        try {
            userInput.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check product stock levels
     */
    public void checkStockLevels() {
        try {
            System.out.println("Enter the product ID: ");
            String productID = userInput.nextLine();
            // check the catalogue to see if the productID exists
            catalogue.containsProductID(productID);
            if (!catalogue.containsProductID(productID)) {
                System.out.println("Product ID not found");
            }

            int totalStockLevel = 0;

            for (Location location : this.locationManager.getAllLocations()) {
                int stockLevel = location.getStockLevel(productID);
                totalStockLevel += stockLevel;
                System.out.println(location.getLocationID() + ": " + stockLevel);
            }
            if (catalogue.containsProductID(productID)) {
                System.out.println("Total: " + totalStockLevel);
            }

            System.out.println("\n" + "Press any key to return to main menu...");
            {
                userInput.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Change product stock levels - functionality to add, deduct and transfer stock
     */
    public void changeStockLevels() {
        System.out.println("Enter the product ID: ");
        String productID = userInput.nextLine();
        System.out.println("Enter an option: ");
        System.out.print(StockMenu.stockOptionsAsString());
        int option = Integer.parseInt(userInput.nextLine());

        if (option != 3) {
            // Add or deduct stock from location
            Location[] locations = this.locationManager.getAllLocations();
            System.out.println("Select a location:");
            for (int i = 0; i < locations.length; i++) {
                System.out.print((i + 1) + ". " + locations[i].getLocationID() + "   ");
            }

            System.out.print("\n" + ">>> ");
            int locationSelection = Integer.parseInt(userInput.nextLine()) - 1;
            Location userLocation = locations[locationSelection];

            System.out.println("Enter an amount: ");
            int amount = Integer.parseInt(userInput.nextLine());

            if (option == 1) {
                userLocation.addStock(productID, amount);
                locationManager.save(this.inventoryFileName);
            }
            if (option == 2) {
                userLocation.deductStock(productID, amount);
                locationManager.save(this.inventoryFileName);
            }
            for (Location location : this.locationManager.getAllLocations()) {
                if (location.getLocationID().equals(userLocation.getLocationID())) {
                    System.out.println(location.getLocationID() + " now has " + userLocation.getStockLevel(productID) + " items of " + productID + " available.");
                }
            }
        }

        // Transfer stock from one location to another
        if (option == 3) {
            System.out.println("Select a location to transfer from: ");
            // these can only be locations with stock in them
            Location[] locations = this.locationManager.getAllLocations();
            locations[0].containsProductID(productID);

            if (productID != null) {
                for (int i = 0; i < locations.length; i++) {
                    if (locations[i].getStockLevel(productID) > 0) {
                        System.out.print((i + 1) + ". " + locations[i].getLocationID() + "   ");
                    }
                }
            }
            System.out.print("\n" + ">>> ");
            int locationSelection = Integer.parseInt(userInput.nextLine()) - 1;
            Location transferFrom = locations[locationSelection];

            System.out.println("Select a location to transfer to: ");
            for (int i = 0; i < locations.length; i++) {
                if (locations[i] != transferFrom || locations[i].getStockLevel(productID) == 0) {
                    System.out.print((i + 1) + ". " + locations[i].getLocationID() + "   ");
                }
            }
            System.out.print("\n" + ">>> ");
            int locationSelectionDestination = Integer.parseInt(userInput.nextLine()) - 1;
            Location destination = locations[locationSelectionDestination];
            System.out.println("Enter an amount to transfer: ");
            int amount = Integer.parseInt(userInput.nextLine());
            if (amount <= transferFrom.getStockLevel(productID)) {
                transferFrom.deductStock(productID, amount);
                destination.addStock(productID, amount);
                locationManager.save(this.inventoryFileName);
            } else {
                System.out.println("Not enough stock to transfer");
            }

            // print new stock levels to user
            for (Location location : this.locationManager.getAllLocations()) {
                if (location.getLocationID().equals(destination.getLocationID())) {
                    System.out.println(location.getLocationID() + " now has " + destination.getStockLevel(productID) + " items of " + productID + " available.");
                }
                if (location.getLocationID().equals(transferFrom.getLocationID())) {
                    System.out.println(location.getLocationID() + " now has " + transferFrom.getStockLevel(productID) + " items of " + productID + " available.");
                }
            }
        }

        System.out.println("\n" + "Press any key to return to main menu...");
        try {
            userInput.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays component types to user
     */
    public void displayComponentTypes() {
        System.out.println("Select the component type to be added: ");
        System.out.print(ComponentType.componentTypeMenuAsString());
    }

    /**
     * handles user selection from Component Type enums & Interface Type enums
     * @param inputType user's input as string
     */
    public OCPRecord handleComponentTypeAndInterfaceTypeInput(String inputType) {
        int selection = -1;
        try {
            ComponentType[] componentTypes = ComponentType.values();
            InterfaceType[] interfaceTypes = InterfaceType.values();

            // ignore this for now - error handling
            while (selection < 1 ||
                    ((inputType.equals("ComponentType") && selection > componentTypes.length - 1)
                            || (inputType.equals("InterfaceType") && selection > interfaceTypes.length - 1))
            ) {
                selection = Integer.parseInt(userInput.nextLine());
                if (selection < 1) {
                    System.out.println("Invalid selection. Please try again.");
                }
            }

            // if user selects any component type other than motherboard:
            if (inputType.equals("ComponentType")) {
                if (componentTypes[selection] != ComponentType.MOTHERBOARD) {
                    OCPRecord ocpRecord = new OCPRecord();
                    ComponentType selectedComponentType = componentTypes[selection];
                    ocpRecord.setComponentType(selectedComponentType);
                    // displays componentTypes to acceptable interfaceTypes
                    InterfaceType selectedInterface = allowedWithAnyComponentType();
                    // sets selected interfaceType to ocpRecord
                    ocpRecord.setInterfaceType(selectedInterface);

                    return ocpRecord;
                }
            }

            if(componentTypes[selection] == ComponentType.MOTHERBOARD){
                // create new motherboard object
                MotherboardOCPRecord mocpRecord = new MotherboardOCPRecord();
                ComponentType selectedComponentType = componentTypes[selection];
                mocpRecord.setComponentType(selectedComponentType);
                // motherboard conditions are:
                InterfaceType [] selectedInterfaces = allowedWithMotherboardComponentType();

                mocpRecord.setCPUInterface(selectedInterfaces[0]);
                mocpRecord.setMemoryInterface(selectedInterfaces[1]);
                mocpRecord.setStorageInterface(selectedInterfaces[2]);
                mocpRecord.setPSUInterface(selectedInterfaces[3]);

                return (OCPRecord) mocpRecord;
            }

        } catch (Exception e) {
        }

        return null;
    }

    /**
     * Array that holds the users interface selections for motherboard
     */
    private InterfaceType[] allowedWithMotherboardComponentType() {

        System.out.println("Select CPU interface: ");
        InterfaceType[] CPUInterfaceTypeMenu = InterfaceType.getCPUTypes();
        displayInterfaceMenuOptions(CPUInterfaceTypeMenu);
        System.out.print("\n" + ">>> ");
        int CPUSelection = Integer.parseInt(userInput.nextLine());
        InterfaceType selectedCPUInterface = CPUInterfaceTypeMenu[CPUSelection -1];

        System.out.println("Select Memory interface: ");
        InterfaceType[] memoryTypeInterfaceTypeMenu = InterfaceType.getMemoryTypes();
        displayInterfaceMenuOptions(memoryTypeInterfaceTypeMenu);
        System.out.print("\n" + ">>> ");
        int memorySelection = Integer.parseInt(userInput.nextLine());
        InterfaceType selectedMemoryInterface = memoryTypeInterfaceTypeMenu[memorySelection -1];

        System.out.println("Select Storage interface: ");
        InterfaceType[] storageTypeInterfaceTypeMenu = InterfaceType.getStorageTypes();
        displayInterfaceMenuOptions(storageTypeInterfaceTypeMenu);
        System.out.print("\n" + ">>> ");
        int storageSelection = Integer.parseInt(userInput.nextLine());
        InterfaceType selectedStorageInterface = storageTypeInterfaceTypeMenu[storageSelection -1];

        System.out.println("Select PSU interface: ");
        InterfaceType[] PSUInterfaceTypeMenu = InterfaceType.getPSUTypes();
        displayInterfaceMenuOptions(PSUInterfaceTypeMenu);
        System.out.print("\n" + ">>> ");
        int PSUSelection = Integer.parseInt(userInput.nextLine());
        InterfaceType selectedPSUInterface = PSUInterfaceTypeMenu[PSUSelection -1];

        return new InterfaceType[] {selectedCPUInterface, selectedMemoryInterface, selectedStorageInterface, selectedPSUInterface};
    }

    /**
     * Logic and user interface selection allowed with any component (except motherboard)
     */
    private InterfaceType allowedWithAnyComponentType() {
        int selection = Integer.parseInt(userInput.nextLine());

        if (selection == 1) {
            System.out.println("Select the interface type: ");
            //motherboard
        }

        if (selection == 2){
            System.out.println("Select the interface type: ");
            InterfaceType[] CPUInterfaceTypeMenu = InterfaceType.getCPUTypes();
            displayInterfaceMenuOptions(CPUInterfaceTypeMenu);
            System.out.print("\n" + ">>> ");

            int CPUSelection = Integer.parseInt(userInput.nextLine());
            InterfaceType selectedCPUInterface = CPUInterfaceTypeMenu[CPUSelection -1];
            return selectedCPUInterface;
        }

        if (selection == 3){
            // GPU - PCIe automatically added
            InterfaceType[] GPUTypes = InterfaceType.getGPUTypes();
            // no display because it is auto assigned, they don't select
            System.out.println("PCIe automatically assigned with GPU");
            int GPUSelection = 1;
            InterfaceType autoAssignedGPU = GPUTypes[GPUSelection -1];
            return autoAssignedGPU;
        }

        if (selection == 4){
            System.out.println("Select the interface type: ");
            InterfaceType[] memoryTypes = InterfaceType.getMemoryTypes();
            displayInterfaceMenuOptions(memoryTypes);
            System.out.print("\n" + ">>> ");
            int memorySelection = Integer.parseInt(userInput.nextLine());
            InterfaceType selectedMemoryInterface = memoryTypes[memorySelection - 1];
            return selectedMemoryInterface;
        }

        if (selection == 5){
            System.out.println("Select the interface type: ");
            InterfaceType[] storageTypes = InterfaceType.getStorageTypes();
            displayInterfaceMenuOptions(storageTypes);
            System.out.print("\n" + ">>> ");
            int storageSelection = Integer.parseInt(userInput.nextLine());
            InterfaceType selectedStorageInterface = storageTypes[storageSelection - 1];
            return selectedStorageInterface;
        }

        if (selection == 6){
            System.out.println("Select the interface type: ");
            InterfaceType[] PSUTypes = InterfaceType.getPSUTypes();
            displayInterfaceMenuOptions(PSUTypes);
            System.out.print("\n" + ">>> ");
            int storageSelection = Integer.parseInt(userInput.nextLine());
            InterfaceType selectedPSUInterface = PSUTypes[storageSelection - 1];
            return selectedPSUInterface;
        }
        return null;
    }

    /**
     * Displays interface Menu options
     */
    private void displayInterfaceMenuOptions(InterfaceType[] interfaceTypes) {
        int i = 1;
        for(InterfaceType interfaceType:  interfaceTypes) {
            System.out.print(i + ". " + interfaceType.getMessage() + "   ");
//            System.out.println();
            i++;
        }
    }


    /**
     * Order fulfilment
     */
    public void createOrder(){
        try {
            Order order = new Order();
            List<String> productIDs = new ArrayList<>();
            Location[] locations = this.locationManager.getAllLocations();
            // prints all locations, minus Gourock:
            System.out.println("Select a location to collect from: ");
            for (int i = 0; i < locations.length - 1; i++) {
                System.out.print((i + 1) + ". " + locations[i].getLocationID() + "   ");
            }
            System.out.print("\n" + ">>> ");
            int locationSelection = Integer.parseInt(userInput.nextLine()) - 1;
            Location userLocation = locations[locationSelection];
            // sets user's location choice
            order.setLocationID(userLocation.toString());

            // loop to get a list of Product IDs;
            for (int i = 0; i < 3; i++) {
                System.out.println("Enter the product ID for order: ");
                String productID = userInput.nextLine();
                if (catalogue.containsProductID(productID)) {
                    order.setProductID(productID);
                } else {
                    System.out.println("No such productID. Restarting your order..." + "\n");
                    createOrder();
                }

                //enter an amount:
                System.out.println("Enter an amount: ");
                int amount = Integer.parseInt(userInput.nextLine());
                order.setAmount(amount);

                // ADD ANOTHER PRODUCT?
                System.out.println("Add another product? (Y/N)");
                String anotherProduct = userInput.nextLine();
                if (anotherProduct.equals("N") || anotherProduct.equals("n")) {
                    break;
                }
                if (anotherProduct.equals("Y") || anotherProduct.equals("y")){
                    System.out.println("Enter the product ID for order: ");
                    String productID2 = userInput.nextLine();
                    order.setProductID(productID2);
                    int amount2 = Integer.parseInt(userInput.nextLine());
                    order.setAmount(amount2);
                }
                productIDs.add(productID);
                break;
            }

            // use the String[] of productIDs to ask catalogue for actual inventory
            String[] productsIDs = new String[0];
            for (String productId : productsIDs) {
                List<OCPRecord> componentsToOrder = new ArrayList<>();
                componentsToOrder.add(this.catalogue.getProductDetails(productId));
            }

            // if they have stock, great - order fulfilled
            if (userLocation.getInventory() != null){
                order.setCollectionDateToday();
                System.out.println(order);
            }

            // if they don't have stock, check other locations
            if (userLocation.getInventory() == null){
                for (int i = 0; i < locations.length - 1; i++) {
                    if (locations[i].getInventory() != null){
                        order.setCollectionDate();
                        System.out.println(order);
                    }
                }
            }

            // if they don't have stock, can't fulfill order
            if(userLocation.getInventory() == null){
                System.out.println("This order cannot be fulfilled. Insufficient stock of the following items: " + productsIDs);
            }

            System.out.println("\n" + "Press any key to return to the main menu...");
            userInput.nextLine();
        }
        catch (Exception e){
        }

    }


    /**
     * When user selects add from initial menu, this method handles logic for collection
     * of user input
     */
    public void add() {
        displayComponentTypes();
        OCPRecord ocpRecord = handleComponentTypeAndInterfaceTypeInput("ComponentType");

        System.out.print("Enter details for the component:" + "\n" + "Manufacturer: ");
        String manufacturer = userInput.nextLine();
        ocpRecord.setManufacturer(manufacturer);
        System.out.print("Description: ");
        String description = userInput.nextLine();
        ocpRecord.setDescription(description);

        System.out.print("Price: ");
        try {
            float price = Float.parseFloat(userInput.nextLine());
            ocpRecord.setPrice(price);
        } catch (NumberFormatException e) {
            System.out.print("Please type in a number: ");
        }

        // details displayed to the user
        System.out.println(ocpRecord);

        // user to confirm details
        System.out.print("Is this correct? (Y/N) ");
        String userConfirm = userInput.nextLine();

        // if confirmed, generate ProductID
        if (userConfirm.equals("Y") || userConfirm.equals("y")) {

            String productID = ocpRecord.generateProductID(this.catalogue.getNextAvailableSerialNumber());

            catalogue.addOCPRecordToCatalogue(ocpRecord);
            catalogue.addProductDetails(productID, ocpRecord);

            FileReadWrite.writeStringsToFile(this.productIDsFileName, this.catalogue.getProductIDs());
            catalogue.save(this.catalogueFileName);

            System.out.println(productID + " added to the catalogue." + "\n");

            //user press any key to return to main menu...
            System.out.println("Press any key to return to main menu...");
            try {
                userInput.nextLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            }

        if (userConfirm.equals("N") || userConfirm.equals("n")) {
                System.out.println("Unconfirmed. Please re-enter details");
                add();
            }
    }

    /**
     * Method runs initial menu
     */
    public void run() {
        System.out.println("Welcome to the OCP catalogue system:");
        System.out.println("Enter an option:");
        InitialMenu initialMenu = InitialMenu.ERROR;

        // while initial menu is not equal to QUIT
        while (initialMenu != InitialMenu.QUIT) {
            displayMenu();
            initialMenu = getUserSelection();
            InitialMenuInput(initialMenu);
        }
    }
}