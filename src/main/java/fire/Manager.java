package fire;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Manager {
    
    private User currentUser; 
    private String currentUsername; 

    private List<RentalPlace> otherRentalPlaces = new ArrayList<>();
    private List<RentalPlace> currentUserRentalPlaces = new ArrayList<>();
    private List<User> users = new ArrayList<>();




    public String getCurrentUsername() {
        return currentUsername;
    }
    
    // sjekker om user allerede ligger i listen over brukere
    private boolean validateUsername(String username){
        for(User e : users){

            if(e.getUsername() == username){

                return true;
    
            }

        }
        return false;
  
    }

    public void login(String username){

        User newUser = new User(username);

        if(validateUsername(username) == false){
            users.add(newUser);
            currentUser = newUser;
            currentUsername = username;

        }
        else{
            for (User e : users){
                if (e.getUsername() == username){
                    currentUser = e; 
                    currentUsername = username;
                }

            }

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
        if (currentUserRentalPlaces.contains(newPlace) == false){
            currentUserRentalPlaces.add(newPlace);
            otherRentalPlaces.add(newPlace);


        }

    }
    // leier plass, fra dato til dato, samt navnet på stedet du vil leie
    public void rentPlace(CharSequence date1, CharSequence date2, int indexOfPlace){

        /**
         tar inn 2 datoer, fra og til dato, og hvilken bolig du vil leie 
         sjekker om datoene er innenfor et intervall tilhørende bolig 
         oppdaterer så listen over ledige datoer til boligen
         */

        LocalDate rentalDateStart = LocalDate.parse(date1);
        LocalDate rentalDateEnd = LocalDate.parse(date2);

        RentalPlace wishedRented = otherRentalPlaces.get(indexOfPlace);

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
            return;

        }
        throw new IllegalArgumentException("go for it (negativt ladd)");

    }

    public void newRating(int score, String comment, Object UserOrPlace){
        currentUser.newRating(score, comment, UserOrPlace);

    }

    public User getUser(String username){

        for (User e : users){
            if (e.getUsername() == username){
                return e;
            }

        }
        throw new IllegalArgumentException("ingen bruker ved det brukernavnet");
        
    }

    public User getCurrentUser(){
        return getUser(currentUsername);
    }

    public RentalPlace getRentalPlace(int index){
        
        return otherRentalPlaces.get(index);

    }



    // mana
    public static void main(String[] args) {
        Manager manager = new Manager();

        manager.login("Jonas");
        manager.newRentalPlace("hinna", "description", "2023-02-04", "2023-02-21", "args");
        System.out.println(manager.currentUser.rentalPlaces.get("hinna").availableDates);


        manager.login("hanne");
        manager.rentPlace("2023-02-08", "2023-02-15", 0);
        manager.rentPlace("2023-02-10", "2023-02-12", 0);



        manager.login("Jonas");
        System.out.println(manager.currentUser.rentalPlaces.get("hinna").availableDates);


    }


    
}
