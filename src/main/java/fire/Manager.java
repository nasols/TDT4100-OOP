package fire;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

public class Manager {
    
    User currentUser; 
    String currentUsername; 

    LinkedHashMap<String, RentalPlace> rentalPlaces = new LinkedHashMap<>();
    LinkedHashMap<String, User> users = new LinkedHashMap<>();

    // sjekker om user allerede ligger i listen over brukere
    public boolean validateUsername(String username){
        if(users.containsKey(username) == true){
            return true;

        }
        else{
            return false; 

        }
        
    }

    public void login(String username){
        User newUser = new User(username);

        if(validateUsername(username) == false){
            users.put(username, newUser);
            this.currentUser = newUser;
            this.currentUsername = username;

        }
        else{
            this.currentUser = users.get(username); 
            this.currentUsername = username;

        }

    }

    public void newRentalPlace(String name, String description, CharSequence availableStart, CharSequence availableEnd, String ... args){

        currentUser.newRentalPlace(name, description, availableStart, availableEnd, args);
        RentalPlace newPlace = currentUser.getRentalPlace(name);
        if (rentalPlaces.containsKey(name) == false){
            rentalPlaces.put(name, newPlace);
        }


    }
    // leier plass, input er dato og hvor mange dager du vil leie, samt navnet på stedet du vil leie
    public void rentPlace(CharSequence date, int numberOfDays, String nameOfPlace){

        LocalDate rentalDateStart = LocalDate.parse(date);
        LocalDate rentalDateEnd = rentalDateStart.plusDays(numberOfDays);

        RentalPlace wishedRented = rentalPlaces.get(nameOfPlace);

        List<LocalDate> availableDates = wishedRented.availableDates;

        int counter = 0;

        for(int i = 0; i<availableDates.size();  i+=2){
            LocalDate availableStart = availableDates.get(i);
            LocalDate availableEnd = availableDates.get(i+1);

            

            if((availableStart.isBefore(rentalDateStart) || availableStart.isEqual(rentalDateStart)) && (availableEnd.isAfter(rentalDateEnd) || availableEnd.isEqual(rentalDateEnd))){
                currentUser.addRentedPlace(nameOfPlace, wishedRented);
                currentUser.addRentedDates(nameOfPlace, rentalDateStart, rentalDateEnd);
                
                

                // sjekker om datoene som du vil leie fra - til er innenfor et intervall av ledige datoer, isåfall gjør det riktige
                if(availableStart.isEqual(rentalDateStart) && availableEnd.isEqual(rentalDateEnd)){
                    wishedRented.availableDates.remove(rentalDateEnd);
                    wishedRented.availableDates.remove(rentalDateStart);
                    counter ++;

                }
                else if (availableStart.isEqual(rentalDateStart) && !(availableEnd.isEqual(rentalDateEnd))){
                    wishedRented.availableDates.add(availableDates.indexOf(availableStart), rentalDateEnd);
                    wishedRented.availableDates.remove(rentalDateStart);
                    counter ++;
                    
                }
                else if (!(availableStart.isEqual(rentalDateStart)) && availableEnd.isEqual(rentalDateEnd)){
                    wishedRented.availableDates.add(availableDates.indexOf(availableEnd), rentalDateStart);
                    wishedRented.availableDates.remove(rentalDateEnd);
                    counter ++;
                    
                }
                else if(!(availableStart.isEqual(rentalDateStart) && availableEnd.isEqual(rentalDateEnd))){
                    wishedRented.availableDates.add(availableDates.indexOf(availableEnd), rentalDateStart);
                    wishedRented.availableDates.add(availableDates.indexOf(availableEnd), rentalDateEnd);
                    counter ++; 

                }

            

            }

        }
        if (counter == 0){
            throw new IllegalArgumentException("neineinei");

        }



    }

    public void newRating(int score, String comment, Object UserOrPlace){
        currentUser.newRating(score, comment, UserOrPlace);

    }

    public User getUser(String username){

        if(users.containsKey(username)){
            return users.get(username);

        }
        else{
            throw new IllegalArgumentException("ingen bruker ved det brukernavnet");

        }
    
        
    }
    public User getCurrentUser(){
        return users.get(currentUsername);

    }


    public static void main(String[] args) {
        Manager manager = new Manager();

        manager.login("Jonas");
        System.out.println(manager.getCurrentUser().getUsername());
        manager.login("Henrik");
        System.out.println(manager.getCurrentUser().getUsername());

        System.out.println(manager.getUser("Jonas").getUsername());

        manager.getCurrentUser().newRating(5, "sesam", manager.getUser("Jonas"));
        manager.login("Jonas");
        System.out.println(manager.getCurrentUser().getRatingByIndex(0).getComment());
        manager.login("Henrik");
        System.out.println(manager.getUser("Jonas").getRatingByIndex(0).getComment());

        manager.newRentalPlace("hinna", "description", "2022-04-01", "2022-04-25", "ingenting");
        System.out.println(manager.rentalPlaces.get("hinna"));
        manager.login("Jonas");
        manager.rentPlace("2022-04-06", 5, "hinna");
        

        manager.login("Tobias");
        manager.rentPlace("2022-04-15", 8, "hinna");
        System.out.println(manager.rentalPlaces.get("hinna").availableDates.size());
        for (int i = 0; i<manager.rentalPlaces.get("hinna").availableDates.size(); i ++){
            System.out.println(manager.rentalPlaces.get("hinna").availableDates.get(i));


        }

        manager.login("Martin");
        manager.rentPlace("2022-04-12", 2, "hinna");
        System.out.println(manager.rentalPlaces.get("hinna").availableDates.size());
        for (int i = 0; i<manager.rentalPlaces.get("hinna").availableDates.size(); i ++){
            System.out.println(manager.rentalPlaces.get("hinna").availableDates.get(i));


        }


    }


    
}
