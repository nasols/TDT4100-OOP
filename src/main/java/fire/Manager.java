package fire;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Manager {

    User currentUser; 

    List<User> users = new ArrayList<>();
    List<RentalPlace> rentalPlaces = new ArrayList<>();

    // sjekker om user allerede ligger i listen over brukere
    public boolean validateUsername(User user){
        if(users.indexOf(user) != -1){
            return false;

        }
        else{
            return true; 

        }

    }

    public void login(String username){
        User newUser = new User(username);

        if(validateUsername(newUser) == false){
            users.add(newUser);
            this.currentUser = newUser;

        }
        else{
            this.currentUser = newUser; 
        }

    }
    
}
