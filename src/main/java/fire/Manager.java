package fire;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    
    User currentUser; 
    String currentUsername; 

    List<User> users = new ArrayList<>();
    List<String> userNames = new ArrayList<>();

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

        if(validateUsername(newUser) == true){
            users.add(newUser);
            userNames.add(username);
            this.currentUser = newUser;
            this.currentUsername = username;

        }
        else{
            this.currentUser = newUser; 
            this.currentUsername = username;

        }

    }

    public void newRentalPlace(String name, String description, CharSequence availableStart, CharSequence availableEnd, String ... args){

        currentUser.newRentalPlace(name, description, availableStart, availableEnd, args);
        RentalPlace newRentalPlace = currentUser.rentalPlaces.get(currentUser.rentalPlaces.size() - 1);


        if(rentalPlaces.indexOf(newRentalPlace) == -1){
            rentalPlaces.add(newRentalPlace);


        }

    }

    public void newRating(int score, String comment, Object UserOrPlace){
        currentUser.newRating(score, comment, UserOrPlace);

    }

    public User getUser(String username){
        int index = userNames.indexOf(username);

        if(index != -1){
            return users.get(index);

        }
        else{
            throw new IllegalArgumentException("ingen bruker ved det brukernavnet");

        }
    
        
    }
    public User getCurrentUser(){
        int index = userNames.indexOf(currentUsername);

        return users.get(index);


    }


    public static void main(String[] args) {
        Manager manager = new Manager();

        manager.login("Jonas");
        System.out.println(manager.users.get(0).username);
        manager.login("Henrik");
        System.out.println(manager.users.get(1).username);
        manager.getCurrentUser().newRating(5, "kuk", manager.getUser("Jonas"));
        manager.login("Jonas");
        System.out.println(manager.getCurrentUser().personRatings.get(0).getComment());
    
    }


    
}
