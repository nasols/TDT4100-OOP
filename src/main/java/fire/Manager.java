package fire;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class Manager {
    
    private User currentUser; 
    private String currentUsername; 

    private LinkedHashMap<String, RentalPlace> rentalPlaces = new LinkedHashMap<>();
    private LinkedHashMap<String, User> users = new LinkedHashMap<>();




    public String getCurrentUsername() {
        return currentUsername;
    }
    
    // sjekker om user allerede ligger i listen over brukere
    private boolean validateUsername(String username){
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
            currentUser = newUser;
            currentUsername = username;

        }
        else{
            currentUser = users.get(username); 
            currentUsername = username;

        }

    }
/* 
    public List rentalDescriptionList() {
        for (i in rentalplaces) {
            List.append(rentalPlaces[i].toString());
        }
        return List
    } */

    public void newRentalPlace(String name, String description, CharSequence availableStart, CharSequence availableEnd, String ... args){

        currentUser.newRentalPlace(name, description, availableStart, availableEnd, args);
        RentalPlace newPlace = currentUser.getRentalPlace(name);
        if (rentalPlaces.containsKey(name) == false){
            rentalPlaces.put(name, newPlace);
        }


    }
    // leier plass, fra dato til dato, samt navnet på stedet du vil leie
    public void rentPlace(CharSequence date1, CharSequence date2, String nameOfPlace){

        /**
         tar inn 2 datoer, fra og til dato, og hvilken bolig du vil leie 
         sjekker om datoene er innenfor et intervall tilhørende bolig 
         oppdaterer så listen over ledige datoer til boligen
         */

        LocalDate rentalDateStart = LocalDate.parse(date1);
        LocalDate rentalDateEnd = LocalDate.parse(date2);

        RentalPlace wishedRented = rentalPlaces.get(nameOfPlace);

        List<LocalDate> availableDates = wishedRented.availableDates;

        if(wishedRented.validateRentalDate(rentalDateStart, rentalDateEnd)){

            if(availableDates.contains(rentalDateStart) && availableDates.contains(rentalDateEnd)){
                availableDates.remove(rentalDateStart);
                availableDates.remove(rentalDateEnd);
            }
            
            else if(availableDates.contains(rentalDateStart)){
                availableDates.remove(rentalDateStart);
                availableDates.add(rentalDateEnd);
            }
            else if(availableDates.contains(rentalDateEnd)){
                availableDates.add(rentalDateStart);
                availableDates.remove(rentalDateEnd);
            }
            else{
                availableDates.add(rentalDateStart);
                availableDates.add(rentalDateEnd);
            }
            
            Collections.sort(availableDates, new LocalDateComparator());

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



    // mana
    public static void main(String[] args) {
        Manager manager = new Manager();

        manager.login("Jonas");
        manager.newRentalPlace("hinna", "description", "2023-02-04", "2023-02-21", "args");
        System.out.println(manager.currentUser.rentalPlaces.get("hinna").availableDates);    
        
        manager.login("Henrik");
        manager.rentPlace("2023-02-05", "2023-02-21", "hinna");

        manager.login("Jonas");
        System.out.println(manager.currentUser.rentalPlaces.get("hinna").availableDates);    


    }


    
}
