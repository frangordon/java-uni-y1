/**
 * Fran Gordon
 *
 * InterfaceType class holds enums and logic for the interfaces that
 * the user can select when adding products to the catalogue
 *
 * */

public enum InterfaceType {
    ERROR (""),
    SOCKET_AM ("SocketAM"),
    LGA ("LGA"),
    PCIE ("PCIe"),
    DDR3 ("DDR3"),
    DDR4 ("DDR4"),
    DDR5 ("DDR5"),
    NVME ("NVME"),
    SATA ("SATA"),
    ATX ("ATX"),
    ITX ("ITX");

    String message;

    InterfaceType(String message)
    {
        this.message = message;
    }


    /**
     * Gets method - gets message from enum to use in toString
     */
    public String getMessage()
    {
        return message;
    }


    /**
     * Gets memory types
     */
    public static InterfaceType[] getMemoryTypes(){
            return new InterfaceType[] {InterfaceType.DDR3, InterfaceType.DDR4, InterfaceType.DDR5};
    }

    /**
     * Gets CPU types
     */
    public static InterfaceType[] getCPUTypes(){
        return new InterfaceType[] {InterfaceType.SOCKET_AM, InterfaceType.LGA};
    }

    /**
     * Gets Storage types
     */
    public static InterfaceType[] getStorageTypes(){
        return new InterfaceType[] {InterfaceType.NVME, InterfaceType.SATA};
    }

    /**
     * Gets PSU types
     */
    public static InterfaceType[] getPSUTypes(){
        return new InterfaceType[] {InterfaceType.ATX, InterfaceType.ITX};
    }

    /**
     * Gets GPU types
     */
    public static InterfaceType[] getGPUTypes(){
        return new InterfaceType[] {InterfaceType.PCIE};
    }




}

