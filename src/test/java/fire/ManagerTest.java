package fire;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ManagerTest {
    Manager manager;
    
    @BeforeEach 
    public void setup() {
        manager = new Manager();
        manager.login("user1");
        manager.newRentalPlace("place1", "description1", "2022-06-01", "2023-06-30");
        manager.newRentalPlace("place2", "description2", "2022-06-01", "2023-06-30");
        manager.login("user2");
        manager.newRentalPlace("place3", "description3", "2022-06-01", "2023-06-30");
        manager.login("user3");
    }

    @Test 
    @DisplayName("Kan ikke logge inn med tomt brukernavn")
    public void testLoginException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			manager.login(" ");
		});
    }

    @Test 
    @DisplayName("Skjekker getCurrentUser og getUser")
    public void testGetUser(){
        Assertions.assertEquals("user3", manager.getCurrentUsername());
        
        Assertions.assertThrows(NoSuchElementException.class, () -> {
			manager.getUser("user99");
		});
    }

    @Test 
    @DisplayName("Sjekker login")
    public void testLogin(){

        Assertions.assertEquals(3, manager.getUsers().size());

        manager.login("user1");
        Assertions.assertEquals(3, manager.getUsers().size());

        manager.login("user4");
        Assertions.assertEquals(4, manager.getUsers().size());
    }

    @Test 
    @DisplayName("Du kan ikke leie ut to steder med samme navn")
    public void testGetUserf(){
        Assertions.assertEquals("user3", manager.getCurrentUsername());
        
        Assertions.assertThrows(NoSuchElementException.class, () -> {
			manager.getUser("user99");
		});
    }

    @Test
    @DisplayName("Sjekker newRentalPlace")
    public void testNewRentalPlace(){


        Assertions.assertThrows(IllegalArgumentException.class, () -> manager.newRentalPlace("hinna", "description", "2020-01-01", "2020-01-20"));

        manager.newRentalPlace("oslo", "description", "2023-02-01", "2023-02-20");

        int expected = 2;
        Assertions.assertEquals(expected, manager.getCurrentUser().getAllRentalPlaces().size());

        User expUser = manager.getUser("user1");
        Assertions.assertEquals(expUser, manager.getRentalPlaceByName("hinna").getOwner());



    }

    @Test
    @DisplayName("sjekker rentPlace")
    public void testRentPlace(){

        manager.login("user1");
        manager.newRentalPlace("name", "description", "2023-01-01", "2023-01-20");

        manager.login("user2");
        manager.newRentalPlace("place2", "description", "2023-01-01", "2023-01-20");
        manager.rentPlace("2023-01-10", "2023-01-15", 0);

        manager.login("user3");
        Assertions.assertThrows(IllegalArgumentException.class, () -> manager.rentPlace("2023-01-05", "2023-01-10", 0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> manager.rentPlace("2022-01-05", "2023-01-10", 0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> manager.rentPlace("2023-01-05", "2024-01-10", 0));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> manager.rentPlace("2023-01-05", "2023-01-10", 15));

        int expected1 = 4;
        Assertions.assertEquals(expected1, manager.getRentalPlace(0).getAvaliableDates().size());

        manager.rentPlace("2023-01-01", "2023-01-05", 1);
        int expected2 = 2;
        Assertions.assertEquals(expected2, manager.getRentalPlace(1).getAvaliableDates().size());

        LocalDate d1 = LocalDate.parse("2023-01-05");
        LocalDate d2 = LocalDate.parse("2023-01-20");
        List<LocalDate> expected3 = new ArrayList<>(Arrays.asList(d1, d2));
        Assertions.assertEquals(expected3, manager.getRentalPlace(1).getAvaliableDates());



    }

    @Test 
    @DisplayName("sjekker displaylist index overens med liste")
    public void testDisplayList(){

        manager.login("user1");
        manager.newRentalPlace("place11", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place12", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place13", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place14", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place15", "description", "2023-01-01", "2023-01-20");

        manager.login("user2");
        manager.newRentalPlace("place21", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place22", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place23", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place24", "description", "2023-01-01", "2023-01-20");

        manager.rentPlace("2023-01-10", "2023-01-15", 0);

        List<String> displayList = manager.getRentalStringList();
        List<RentalPlace> allPlaces = manager.getAllRentalplaces();

        int expected1 = 5;
        int actual1 = displayList.size();
        Assertions.assertEquals(expected1, actual1);

        int expected2 = 9;
        int actual2 = allPlaces.size();
        Assertions.assertEquals(expected2, actual2);

        Assertions.assertEquals(manager.getUser("user1").getRentalPlace(0), manager.getRentalList().get(0));



    }

    @Test
    @DisplayName("tester booking")
    public void testBooking(){

        Manager manager = new Manager();
        manager.login("user1");
        manager.newRentalPlace("place11", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place12", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place13", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place14", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place15", "description", "2023-01-01", "2023-01-20");

        manager.login("user2");
        manager.newRentalPlace("place21", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place22", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place23", "description", "2023-01-01", "2023-01-20");
        manager.newRentalPlace("place24", "description", "2023-01-01", "2023-01-20");

        manager.rentPlace("2023-01-10", "2023-01-15", 0);
        manager.rentPlace("2023-01-10", "2023-01-15", 1);
        manager.rentPlace("2023-01-10", "2023-01-15", 2);

        manager.login("user1");
        manager.rentPlace("2023-01-10", "2023-01-15", 0);
        manager.rentPlace("2023-01-10", "2023-01-15", 1);
        manager.rentPlace("2023-01-10", "2023-01-15", 2);

        int expected1 = 3;
        int actual1 = manager.getBookingStringList().size();
        Assertions.assertEquals(expected1, actual1);

        int expected2 = 6;
        int actual2 = manager.getUser("user1").getBookingList().size() + manager.getUser("user2").getBookingList().size();
        Assertions.assertEquals(expected2, actual2);


    }

    
}
