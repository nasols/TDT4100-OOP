package fire;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Manager {
    
    private User currentUser; 
    private String currentUsername; 

    private List<RentalPlace> rentalPlaces = new ArrayList<>();
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
        String pattern = "([a-zA-Z])\\1*";
        if(name == "" || name.matches(pattern)){
            throw new IllegalArgumentException("kan ikke ha tomt felt i 'name' / bare repeterende karakterer, manager -> newRentalPlace");
        }
        currentUser.newRentalPlace(name, description, availableStart, availableEnd, args);
        RentalPlace newPlace = currentUser.getRentalPlace(currentUser.getAllRentalPlaces().size()-1);
        rentalPlaces.add(newPlace);

        
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

        RentalPlace wishedRented = rentalPlaces.get(indexOfPlace);

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
        throw new IllegalArgumentException("ugyldig intervall gitt inn, manager -> rentPlace");

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
        List<RentalPlace> displayList = rentalPlaces.stream().filter(e -> !e.inList(currentUser.getAllRentalPlaces())).toList();
        
        return displayList.get(index);

    }

    public List<String> toStringList(){

        List<RentalPlace> displayList = rentalPlaces.stream().filter(e -> !e.inList(currentUser.getAllRentalPlaces())).toList();

        List<String> display = new ArrayList<>();

        for (RentalPlace rentalPlace : displayList) {
            display.add(rentalPlace.toString());

        };
        
        return display; 



    }


    // mana
    public static void main(String[] args) {
        Manager manager = new Manager();

        manager.login("Jonas");
        manager.newRentalPlace( "    ", "description", "2023-02-04", "2023-02-21", "args");
        manager.newRentalPlace("olso", "kompis", "2023-02-04", "2023-02-21", "args");

        manager.login("hanne");
        manager.newRentalPlace("name", "description", "2023-02-04", "2023-02-24", "args");
        manager.rentPlace("2023-02-08", "2023-02-15", 0);
        manager.newRentalPlace("trodden", "description", "2023-05-14", "2023-05-28", "args");
        
        manager.login("Jonas");
        manager.newRentalPlace("bygd", "råtass", "2023-02-04", "2023-02-21", "args");

        //System.out.println(manager.currentUser.rentalPlaces.get("hinna").availableDates);


        



        manager.login("hanne");
        //System.out.println(manager.currentUser.rentalPlaces.get("hinna").availableDates);
    
        System.out.println(manager.getRentalPlace(2));
        System.out.println(manager.toStringList().get(2));



    }


    
}
