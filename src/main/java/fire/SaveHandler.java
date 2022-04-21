package fire;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

public class SaveHandler implements ISaveHandler {

    @Override
    public void writeReciept(Manager manager) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(getDataFile())) {
            for (User user : manager.getUsers()) {
                for (RentalPlace place : user.getAllRentalPlaces()) {
                    
                }
                writer.println(String.format("%s;%s;%s", user.getUsername(), "2", "3"));
            }
        }
        catch (URISyntaxException e) {
            System.out.println(e);
        }

    } 

    private static String getDataFile() throws URISyntaxException {
        URI uri = new URI(SaveHandler.class.getResource("data/").toString() + "dat.txt");
        return uri.getPath();
    }
}
