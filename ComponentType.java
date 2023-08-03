/**
 * Fran Gordon
 *
 * ComponentType class holds enums and logic for the components that
 * the user can select when adding products to the catalogue
 *
 * */

public enum ComponentType {
    ERROR (""),
    MOTHERBOARD("Motherboard"),
    CPU("CPU"),
    GPU("GPU"),
    MEMORY("Memory"),
    STORAGE("Storage"),
    PSU("PSU");


    private String message;

    ComponentType(String message)
    {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static String componentTypeMenuAsString()
    {
        try{
            String s = "";
        ComponentType[] componentTypeMenuOptions = ComponentType.values();
        for (int i = 1; i< componentTypeMenuOptions.length; i++)
        {
            s += i + ". " + componentTypeMenuOptions[i].message + "    ";
        }
        return s + "\n" + ">>> ";
        }
        catch (NumberFormatException e){
            System.out.println("");
        }
        return null;
    }
}