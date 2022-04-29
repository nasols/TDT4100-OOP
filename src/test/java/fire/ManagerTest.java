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
        
        User user3 = manager.getCurrentUser();
        Assertions.assertEquals(user3, manager.getUser("user3"));

        Assertions.assertThrows(NoSuchElementException.class, () -> {
			manager.getUser("user99");
		});
    }

    @Test 
    @DisplayName("Prøver å hente ut et sted som ikke finnes")
    public void testGetRentalPlace(){
        Assertions.assertThrows(NoSuchElementException.class, () -> {
			manager.getRentalPlaceByName("place99");
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
    public void testRentDouble(){
        manager.login("user1");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			manager.newRentalPlace("place1", "description1", "2022-06-02", "2023-06-20");
		});
    }

    @Test
    @DisplayName("Sjekker newRentalPlace")
    public void testNewRentalPlace(){
        manager.newRentalPlace("place4", "description4", "2022-06-02", "2023-06-20");
        Assertions.assertEquals(4, manager.getAllRentalplaces().size());
    }

    @Test
    @DisplayName("Du kan ikke leie fra et sted som ikke eksisterer")
    public void testRentNonExisting(){

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			manager.rentPlace("2022-07-01", "2022-07-03", -1);
		});
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
			manager.rentPlace("2022-07-01", "2022-07-03", 100);
		});

    }

    @Test
    @DisplayName("Feil ved oppdatering av tidsintervaller ved leie")
    public void testIntervalUpdate() {
        Assertions.assertEquals(2, manager.getRentalPlace(0).getAvaliableDates().size());
        
        manager.rentPlace("2023-01-01", "2023-01-05", 0);
        Assertions.assertEquals(4, manager.getRentalPlace(0).getAvaliableDates().size());

        LocalDate d1 = LocalDate.parse("2022-06-01");
        LocalDate d2 = LocalDate.parse("2023-01-01");
        LocalDate d3 = LocalDate.parse("2023-01-05");
        LocalDate d4 = LocalDate.parse("2023-06-30");

        List<LocalDate> expected3 = new ArrayList<>(Arrays.asList(d1, d2, d3, d4));
        Assertions.assertEquals(expected3, manager.getRentalPlace(0).getAvaliableDates());
    }

    @Test 
    @DisplayName("Brukeren skal ikke kunne se sin egen leilighet")
    public void testDisplayList(){
        manager.login("user1");

        List<String> displayList = manager.getRentalStringList();
        List<RentalPlace> allPlaces = manager.getAllRentalplaces();

        int expected1 = 1;
        int actual1 = displayList.size();
        Assertions.assertEquals(expected1, actual1);

        int expected2 = 3;
        int actual2 = allPlaces.size();
        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    @DisplayName("Sjekker om relasjonen mellom brukere og leiligheter holdes etter utleie")
    public void testBookingRelation(){
        manager.rentPlace("2023-01-01", "2023-01-05", 0);

        RentalPlace expected = manager.getUser("user1").getRentalPlace(0);
        Assertions.assertEquals(expected, manager.getCurrentUser().getBookings().get(0).getBookedPlace()); 

    }

    @Test
    @DisplayName("Kan ikke leie før dagsdato")
    public void testBookPast() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			manager.rentPlace("2020-07-01", "2022-07-03", 0);
		});
    }

    @Test
    @DisplayName("Sluttdato må være før startdato")
    public void testBookStartBeforeEnd() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			manager.rentPlace("2022-07-03", "2022-07-01", 0);
		});
    }

    @Test
    @DisplayName("Sjekker om man kan booke utenfor ledig periode")
    public void testBookUnavaliable() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			manager.rentPlace("2023-02-01", "2024-09-03", 1);
		});
    }
}
