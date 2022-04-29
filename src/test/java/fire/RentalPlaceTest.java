package fire;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RentalPlaceTest {

    @Test
    @DisplayName("tester konstruktør")
    public void testConstructor(){
        User user1 = new User("user1");
        RentalPlace place1 = new RentalPlace(user1, "place1", "description", "2023-01-01", "2023-01-20");

        Assertions.assertEquals(user1, place1.getOwner());
        Assertions.assertEquals("place1", place1.getTitle());

        Assertions.assertThrows(IllegalArgumentException.class, () -> new RentalPlace(user1, "", "description", "2023-01-01", "2023-01-20"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RentalPlace(user1, "place2", "description", "2020-01-01", "2023-01-20"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RentalPlace(user1, "place2", "description", "2023-01-01", "2022-01-20"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RentalPlace(user1, "place2", "description", "2023-01-01", "2022-12-20"));
    }
    
}
