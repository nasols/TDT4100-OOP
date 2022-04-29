package fire;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

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
        throw new NoSuchElementException("Brukeren " + username + " finnes ikke");
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
        throw new NoSuchElementException("Stedet " + nameOfPlace + " finnes ikke");
    }

    public void login(String username){
        User newUser = new User(username);        

        if (!users.stream().anyMatch(e -> e.getUsername().equals(username))){

            users.add(newUser);
            currentUser = newUser;
            currentUsername = username;
        }

        else {
            for ( User e : users ){
                if ( e.getUsername().equals(username)){
                    this.currentUser = users.get(users.indexOf(e));
                    this.currentUsername = username;
                }
            }
        }
    }


    public void newRentalPlace(String name, String description, CharSequence availableStart, CharSequence availableEnd, String ... args){

        if ( this.currentUser.getAllRentalPlaces().stream().anyMatch(r -> r.getTitle().equals(name)) ){
            throw new IllegalArgumentException("Du kan ikke leie ut to steder med samme navn");
        }

        RentalPlace newPlace = new RentalPlace(this.currentUser, name, description, availableStart, availableEnd);
        rentalPlaces.add(newPlace);
        currentUser.addRentalPlace(newPlace);
    }

    // Brukes bare ved fillesning, trenger ikke validering pga allerede validert bolig
    public void newRentalPlaceOffline(User owner, String name, String description, CharSequence[] fromToDatesInput){
        
        RentalPlace place = new RentalPlace(owner, name, description, fromToDatesInput);
        rentalPlaces.add(place);
        owner.addRentalPlace(place);
    }

    
    public void addBookingOffline(String username, RentalPlace bookedPlace, CharSequence bookingStart, CharSequence bookingEnd){
        this.login(username);
        User user = this.currentUser;
        user.addBooking(bookedPlace, LocalDate.parse(bookingStart), LocalDate.parse(bookingEnd));
    }

    public void rentPlace(CharSequence date1, CharSequence date2, int indexOfPlace){

        if (indexOfPlace == -1) {
            throw new IllegalArgumentException("Velg et sted å leie");
        }

        LocalDate rentalDateStart = LocalDate.parse(date1);
        LocalDate rentalDateEnd = LocalDate.parse(date2);

        RentalPlace wishedRented = getRentalList().get(indexOfPlace);

        List<LocalDate> availableDates = wishedRented.getAvaliableDates();

        // Skjekker om leiligheten er tilgjengelig i ønsket leieperiode, og oppdaterer leilighetens tilgjengelighet
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

            currentUser.addBooking(wishedRented, rentalDateStart, rentalDateEnd);
        }
    }


    public List<String> getRentalStringList(){
        // Returnerer en liste med beskrivende strenger av alle leiligheter
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

    public List<RentalPlace> getAllRentalplaces() {
        return rentalPlaces;
    } 
}
