/**
 * Fran Gordon
 *
 * This LocationManger class manages the list of locations
 * It also holds the methods to save and load the whole inventory file
 *
 */

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class LocationManager implements Serializable {
    private Map<String, Location> locations;

    public LocationManager() {
      locations = new HashMap<>();
      initialiseLocations();
    }

    /**
     * Adds the locations
     **/
    private void initialiseLocations()
    {
        Location glasgow = new Location("Glasgow");
        Location edinburgh = new Location("Edinburgh");
        Location gourock = new Location("Gourock");

        locations.put("Glasgow", glasgow);
        locations.put("Edinburgh", edinburgh);
        locations.put("Gourock", gourock);
    }

    /**
     * get all locations as list
     **/
    public Location[] getAllLocations(){
        Location[] orderedLocations = new Location[locations.size()];
        orderedLocations[0] = locations.get("Glasgow");
        orderedLocations[1] = locations.get("Edinburgh");
        orderedLocations[2] = locations.get("Gourock");
        return orderedLocations;
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
     * Load method - deserializes the inventory when we start the program
     *
     * @param filePath String finds the filePath we read from (inventory.txt)
     * @return Null if we get an exception or the deserialized object.
     */
    public static LocationManager load(String filePath) {
        Object locationManager = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            locationManager = ois.readObject();
        } catch (FileNotFoundException e) {
            locationManager = new LocationManager();
        } catch (Exception e) {
            System.out.println(e);
        }
        return (LocationManager) locationManager;
    }
}
