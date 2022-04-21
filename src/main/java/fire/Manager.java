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
    LocalDate testDato; 

    public List<User> getUsers() {
        return users;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }
    public User getCurrentUser(){
        return getUser(currentUsername);
    }
    public User getUser(String username){

        for (User e : users){
            if (e.getUsername() == username){
                return e;
            }

        }
        throw new IllegalArgumentException("ingen bruker ved det brukernavnet");
        
    }
    public RentalPlace getRentalPlace(int index){
        List<RentalPlace> displayList = rentalPlaces.stream().filter(e -> !e.inList(currentUser.getAllRentalPlaces())).toList();
        
        return displayList.get(index);

    }

    public void login(String username){

        User newUser = new User(username);
        
        if(!users.stream().anyMatch(e -> e.getUsername().equals(username))){
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

    public void newRentalPlace(String name, String description, CharSequence availableStart, CharSequence availableEnd, String ... args){
        String pattern = "([a-zA-Z])\\1*";
        if(name == "" || name.matches(pattern)){
            throw new IllegalArgumentException("kan ikke ha tomt felt i 'name' / bare repeterende karakterer, manager -> newRentalPlace");
        }
        //Både manager og user har en newRentalPlace metode?
        // fikset 
        RentalPlace newPlace = new RentalPlace(this.currentUser, name, description, availableStart, availableEnd, args);
        rentalPlaces.add(newPlace);
        currentUser.addRentalPlace(newPlace);


        
    }

    // brukes bare ved fillesning, trenger ikke validering pga allerede-validert bolig
    public void newRentalPlaceOffline(User owner, String name, String description, CharSequence[] fromToDatesInput, String[] fasaliteter){
        
        RentalPlace place = new RentalPlace(owner, name, description, fromToDatesInput, fasaliteter);
        rentalPlaces.add(place);
        owner.addRentalPlace(place);

        
    }


    // leier plass, fra dato til dato, samt navnet på stedet du vil leie
    public void rentPlace(CharSequence date1, CharSequence date2, int indexOfPlace){

        if (indexOfPlace == -1) {
            throw new IllegalArgumentException("Velg et sted å leie");
        }
        /**
         tar inn 2 datoer, fra og til dato, og hvilken bolig du vil leie 
         sjekker om datoene er innenfor et intervall tilhørende bolig 
         oppdaterer så listen over ledige datoer til boligen
         */

        LocalDate rentalDateStart = LocalDate.parse(date1);
        LocalDate rentalDateEnd = LocalDate.parse(date2);
        this.testDato = rentalDateStart;


        RentalPlace wishedRented = rentalPlaces.get(indexOfPlace);

        List<LocalDate> availableDates = wishedRented.availableDates; //Ikkje lov 

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
            // Tror d e her bookingen ska bli adda. Charsequence-Localdate
            // passende
            currentUser.addBooking(wishedRented, rentalDateStart.toString(), rentalDateEnd.toString());
            return;

        }
        throw new IllegalArgumentException("ugyldig intervall gitt inn, manager -> rentPlace");

    }

    public void newRating(int score, String comment, Object UserOrPlace){
        currentUser.newRating(score, comment, UserOrPlace);

    }

    public List<String> getRentalStringList(){

        List<RentalPlace> displayList = rentalPlaces.stream().filter(e -> !e.inList(currentUser.getAllRentalPlaces())).toList();

        List<String> display = new ArrayList<>();

        for (RentalPlace rentalPlace : displayList) {
            display.add(rentalPlace.toString(true));

        };
        
        return display; 
    }

    public List<String> getBookingStringList() {
        return currentUser.getBookingList();
    }

    public void logout(){
        this.currentUser = null; 
        this.currentUsername = null;
    }


    // mana
    public static void main(String[] args) {
        Manager manager = new Manager();

        manager.login("Jonas");
        //manager.newRentalPlace("hinna", "description", "2023-02-04", "2023-02-21", "hei", "og", "hopp", "din fluesopp");
        //manager.newRentalPlace("olso", "kompis", "2023-02-04", "2023-02-21", "internett", "badebasseng");
        //System.out.println(manager.getCurrentUser().rentalPlaces.get(0).availableDates);


        manager.login("hanne");
        //manager.newRentalPlace("name", "description", "2023-02-04", "2023-02-24", "args");
        //manager.rentPlace("2023-02-08", "2023-02-15", 0);
        //manager.newRentalPlace("trodden", "description", "2023-05-14", "2023-05-28", "args");
        
        manager.login("Jonas");
        //manager.newRentalPlace("bygd", "råtass", "2023-02-04", "2023-02-21", "args");

        manager.login("papsen");
        //manager.newRentalPlace("nmr17", "råtass", "2023-02-04", "2023-02-21", "args");

        manager.login("hanne");
        //manager.rentPlace("2023-02-05", "2023-02-10", 5);
        //System.out.println(manager.getCurrentUser().rentalPlaces.get(0).availableDates);
        
        //System.out.println(manager.rentalPlaces.get(5).name);
    
        //System.out.println(manager.getRentalPlace(2));
        //System.out.println(manager.toStringList().get(2));

        //System.out.println(manager.getCurrentUser().bookings.get(1).toFileString());
        manager.login("Jonas");
        System.out.println(manager.getUsers().size());

    }


    
}
