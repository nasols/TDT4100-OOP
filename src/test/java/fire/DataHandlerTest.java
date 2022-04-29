package fire;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DataHandlerTest {
    
    private Manager manager;
    private DataHandler dataHandler;

    private static File getFile(String filename) {
        return new File(DataHandler.class.getResource("data/").getFile() + filename + ".txt");
    }

    @BeforeEach
    public void setup() throws FileNotFoundException {
        manager = new Manager();
        dataHandler = new DataHandler();

        manager.login("user1");
        manager.newRentalPlace("place1", "description1", "2022-06-01", "2023-06-30");
        manager.newRentalPlace("place2", "description2", "2022-06-01", "2023-06-30");
        
        manager.login("user2");
        manager.newRentalPlace("place3", "description3", "2022-06-01", "2023-06-30");
        
        manager.login("user3");
        manager.rentPlace("2023-01-01", "2023-01-05", 0);
        
        manager.login("user2");
        manager.rentPlace("2023-01-10", "2023-01-15", 0);
    
        dataHandler.writeData(manager);
    }


    @Test
    @DisplayName("Sjekker om informasjonen fra manager stemmer overens med dat-filen")
    public void testWriteUserData() throws FileNotFoundException {

        String actual1;
        int actual2;
        int actual3;
        String actual4;
        
        try ( Scanner scanner = new Scanner(new FileReader(getFile("dat"))) ){

            actual1 = scanner.nextLine();
            actual2 = Integer.parseInt(scanner.nextLine());
            actual3 = new ArrayList<>(Arrays.asList(scanner.nextLine().split(";"))).size();
            actual4 = new ArrayList<>(Arrays.asList(scanner.nextLine().split(";"))).get(0);
        }

        String expected1 = "user1";
        int expected2 = 2;
        int expected3 = 3;
        String expected4 = "place2";

        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
        Assertions.assertEquals(expected3, actual3);
        Assertions.assertEquals(expected4, actual4);
    }

    @Test
    @DisplayName("Sjekker om informasjonen fra manager stemmer overens med datbookings-filen")
    public void testWriteUserBookings() throws FileNotFoundException {

        String actual1;
        int actual2;
        String actual3;
        int actual4;
        String actual5;
        
        try ( Scanner scanner = new Scanner(new FileReader(getFile("datbookings"))) ){

            actual1 = scanner.nextLine();
            actual2 = Integer.parseInt(scanner.nextLine());
            actual3 = scanner.nextLine();
            actual4 = Integer.parseInt(scanner.nextLine());
            actual5 = scanner.nextLine();
        }

        String expected1 = "user1";
        int expected2 = 0;
        String expected3 = "user2";
        int expected4 = 1;
        String expected5 = "place1;2023-01-10;2023-01-15";

        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
        Assertions.assertEquals(expected3, actual3);
        Assertions.assertEquals(expected4, actual4);
        Assertions.assertEquals(expected5, actual5);
    }

    @Test
    @DisplayName("Skjekker om gjennopprettingen av tilstanden til manager er den samme som før")
    public void testReadData() throws FileNotFoundException {

        Manager newManager = dataHandler.readData();
        newManager.login("user2");
        
        String expected = manager.getRentalPlaceByName("place1").toString(true);
        Assertions.assertEquals(expected, newManager.getRentalPlaceByName("place1").toString(true));

        Assertions.assertEquals(manager.getRentalStringList(), newManager.getRentalStringList());
        Assertions.assertEquals(manager.getBookingStringList(), newManager.getBookingStringList());
    }


}
