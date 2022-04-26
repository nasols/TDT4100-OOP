package fire;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DataHandlerTest {

    private static String getDataFile() throws URISyntaxException {
        URI uri = new URI(DataHandler.class.getResource("data/").toString() + "dat.txt");
        return uri.getPath();
    }

    @Test
    @DisplayName("tester WriteData")
    public void testWriteData() throws FileNotFoundException, URISyntaxException{

        Manager manager = new Manager();
        DataHandler dataHandler = new DataHandler();

        manager.login("user1");
        manager.newRentalPlace("place11", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place12", "description", "2023-01-01", "2023-01-20");

        manager.login("user2");
        manager.newRentalPlace("place21", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place22", "description", "2023-01-01", "2023-01-20");

        dataHandler.writeData(manager);

        String actual1;
        int actual2;
        int actual3;
        String actual4;
        
        try ( Scanner scanner = new Scanner(new FileReader(getDataFile())) ){

            actual1 = scanner.nextLine();
            actual2 = Integer.parseInt(scanner.nextLine());
            actual3 = new ArrayList<>(Arrays.asList(scanner.nextLine().split(";"))).size();
            actual4 = new ArrayList<>(Arrays.asList(scanner.nextLine().split(";"))).get(0);


        }
        String expected1 = "user1";
        int expected2 = 2;
        int expected3 = 3;
        String expected4 = "place12";

        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
        Assertions.assertEquals(expected3, actual3);
        Assertions.assertEquals(expected4, actual4);


    }

    @Test
    @DisplayName("tester ReadData")
    public void testReadData() throws FileNotFoundException, URISyntaxException{

        DataHandler dataHandler = new DataHandler();
        Manager manager = dataHandler.readData();
        

        String actual1 = manager.getUsers().get(0).getUsername();
        int actual2 = manager.getUsers().get(1).getAllRentalPlaces().size();
        String actual3 = manager.getRentalPlaceByName("place12").getTitle();
      

        String expected1 = "user1";
        int expected2 = 2;
        String expected3 = "place12";

        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
        Assertions.assertEquals(expected3, actual3);


    }


}
