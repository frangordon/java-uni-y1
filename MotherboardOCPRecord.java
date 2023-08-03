/**
 * Fran Gordon
 *
 * The Motherboard OCPRecord is a sublcass of OCPRecord.
 * It is used to create Motherboard objects
 * These are passed to the catalogue to be saved
 *
 */

public class MotherboardOCPRecord extends OCPRecord{

    public final ComponentType componentType = ComponentType.MOTHERBOARD;
    public InterfaceType cpuInterface;
    public InterfaceType memoryInterface;
    public InterfaceType storageInterface;
    public InterfaceType psuInterface;


    /**
     * constructs empty motherboard OCP record
     */
    public MotherboardOCPRecord() {
    }


    public void setCPUInterface(InterfaceType cpuInterface) {
        this.cpuInterface = cpuInterface;
    }

    public void setMemoryInterface(InterfaceType memoryInterface) {
        this.memoryInterface = memoryInterface;
    }

    public void setStorageInterface(InterfaceType storageInterface) {
        this.storageInterface = storageInterface;
    }

    public void setPSUInterface(InterfaceType psuInterface) {
        this.psuInterface = psuInterface;
    }

    /**
     * toString shows user the contents of Motherboard OCP record
     */
    @Override
    public String toString() {
        return "Product is " + "\"" + componentType.getMessage() + " " + description +
                " with interface " + cpuInterface.getMessage() + ", " + memoryInterface.getMessage() + ", " +
                 storageInterface.getMessage() + ", " + psuInterface.getMessage() + " manufactured by "
                + manufacturer + " at price £" + price + "\". ";
    }

}
