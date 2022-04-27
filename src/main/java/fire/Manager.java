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
    public RentalPlace getRentalPlaceByName(String nameOfPlace){
        for ( RentalPlace place : rentalPlaces ){
            if ( place.getTitle().equals(nameOfPlace)){
                return place;
            }
            
        }
        throw new IllegalArgumentException("ingen steder med dette navnet");


    }

    public void login(String username){
        User newUser = new User(username);        

        if(!users.stream().anyMatch(e -> e.getUsername().equals(username))){

            users.add(newUser);
            currentUser = newUser;
            currentUsername = username;
        }

        else{
            for ( User e : users ){
                if ( e.getUsername().equals(username)){
                    this.currentUser = users.get(users.indexOf(e));
                    this.currentUsername = username;

                }
            }

        }

    }


    public void newRentalPlace(String name, String description, CharSequence availableStart, CharSequence availableEnd, String ... args){
        
        //Både manager og user har en newRentalPlace metode?
        // fikset 
        if ( this.currentUser.getAllRentalPlaces().stream().anyMatch(r -> r.getTitle().equals(name)) ){

            throw new IllegalArgumentException("allerede eksisterende bolig, Manager -> newRentalPlace");
            
        }
        RentalPlace newPlace = new RentalPlace(this.currentUser, name, description, availableStart, availableEnd);
        rentalPlaces.add(newPlace);
        currentUser.addRentalPlace(newPlace);


        
    }

    // brukes bare ved fillesning, trenger ikke validering pga allerede-validert bolig
    public void newRentalPlaceOffline(User owner, String name, String description, CharSequence[] fromToDatesInput){
        
        RentalPlace place = new RentalPlace(owner, name, description, fromToDatesInput);
        rentalPlaces.add(place);
        owner.addRentalPlace(place);

        
    }

    public void addBookingOffline(String username, RentalPlace bookedPlace, CharSequence bookingStart, CharSequence bookingEnd){
        this.login(username);
        User user = this.currentUser;
        user.addBooking(bookedPlace, bookingStart, bookingEnd);
 
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

        RentalPlace wishedRented = getRentalList().get(indexOfPlace);

        List<LocalDate> availableDates = wishedRented.getAvaliableDates(); //Ikkje lov 

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

            currentUser.addBooking(wishedRented, rentalDateStart.toString(), rentalDateEnd.toString());
            return;

        }
        throw new IllegalArgumentException("ugyldig intervall gitt inn, manager -> rentPlace");

    }


    public List<String> getRentalStringList(){

        List<RentalPlace> displayList = rentalPlaces.stream().filter(e -> !e.inList(currentUser.getAllRentalPlaces())).toList();

        List<String> display = new ArrayList<>();

        for (RentalPlace rentalPlace : displayList) {
            display.add(rentalPlace.toString(true));

        };
        
        return display; 
    }

    public List<RentalPlace> getRentalList(){

        List<RentalPlace> displayList = rentalPlaces.stream().filter(e -> !e.inList(currentUser.getAllRentalPlaces())).toList();

        return displayList; 
    }

    public List<String> getBookingStringList() {
        return currentUser.getBookingList();
    }

    public List<RentalPlace> getAllRentalplaces (){
        return rentalPlaces;
    }

    // mana
    public static void main(String[] args) {

        
    }


    
}
