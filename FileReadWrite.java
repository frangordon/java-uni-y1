/**
 * Fran Gordon
 *
 * Class to Read or Write to text files
 *
 * */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;



public class FileReadWrite {

    // reading the first line, serial number

    // static method to read file
    public static ArrayList<String> readStringFromFile(String filePath) {
        ArrayList<String> strings = new ArrayList<>();

        try {
            File fileObj = new File(filePath);
            Scanner scanner = new Scanner(fileObj);

            if (scanner.hasNextLine()) {
                String serialAsString = scanner.nextLine();
                strings.add(serialAsString);
            }

            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                strings.add(s);
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
        return strings;
    }

    /**
     * Writes strings to file
     * */
    public static void writeStringsToFile(String filePath, ArrayList<String> strings)
    {
        try(FileWriter fileWriter = new FileWriter(filePath))
        {

            for(String s: strings)
            {
                fileWriter.write(s + "\n");
            }


        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }

}
