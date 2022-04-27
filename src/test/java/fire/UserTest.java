package fire;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {
    User user;
    RentalPlace place;
    Booking booking; 


    @Test
    @DisplayName("sjekker konstruktøren, tom og ikke tom")
    public void testKonstruktør(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new User(""));

        User user1 = new User("jonas");
        User user2 = new User("jonas123");

        String expected1 = "jonas";
        String actual1 = user1.getUsername();

        Assertions.assertEquals(expected1, actual1);

    }

    @Test
    @DisplayName("sjekker addRentalplace")
    public void testAddRentalPlace(){

        User user1 = new User("user1");
        RentalPlace place1  = new RentalPlace(user1, "name", "description", "2023-01-01", "2023-01-20");
        RentalPlace place2 = new RentalPlace(user1, "name", "description", "2023-02-01", "2023-02-20");
        user1.addRentalPlace(place1);


    }

    
}
