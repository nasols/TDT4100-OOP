package fire;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class User {
    // brukerinfo
    private String username;
    
    // Brukerens ratings
    List<Rating> personRatings = new ArrayList<>();

    // brukerens utleiesteder 
    List<RentalPlace> rentalPlaces = new ArrayList<>();

    // steder og datoer brukeren skal leie andre steder (egen ferie)
    List<Booking> bookings = new ArrayList<>();

    // validations 

    // sjekker om navn bare inneholder bokstaver 
    /*private boolean validateName(String inputName){
        if (inputName.matches("[a-zA-Z]+")){
            return true;

        }
        else{
            return false;
        }
    }
    // sjekker om input birthdate er før dags dato, eller mer enn 100 år siden
    private boolean validateBirthdate(LocalDate birthdate){
        LocalDate today = LocalDate.now();
        LocalDate cutOff = LocalDate.parse("1920-01-01");
        if (today.isBefore(birthdate) || birthdate.isBefore(cutOff)){
            return false;
        }
        else{
            return true;

        }
        
    }
    // sjekker mail på riktig format 
    // legg inn kode fra tidligere øving (log :) ) 

    // konstruktør 
    public User(String firstName, String lastName, CharSequence birthdate, String mail) throws ParseException{
        LocalDate testBirthdate = LocalDate.parse(birthdate);

        if(validateName(firstName) && validateName(lastName)){
            this.firstName = firstName;
            this.lastName = lastName;

        }
        else{
            throw new IllegalArgumentException("feil format på navn");

        }
        
        if(validateBirthdate(testBirthdate)){
            this.birthdate = testBirthdate;
            LocalDate today = LocalDate.now();
            int year = today.getYear();
            this.age = year - testBirthdate.getYear();

        }
        else{
            throw new IllegalArgumentException("feil format på bursdag"); 

        }
        // if validate mail --> set mail 
        this.mail = mail; 

    }*/
    
    // mindre konstruktør
    public User(String username){
        if (username == "") {
            throw new IllegalArgumentException("Vennligst skriv inn et brukernavn");
        }
        this.username = username; 

    }
    // getters & setters 
    public String getUsername(){
        return this.username;

    }/*
    public String getFirstName(){
        return this.firstName;

    }
    public String getLastName(){
        return this.lastName;
        
    }
    public int getAge(){
        return this.age;

    }*/



    public List<RentalPlace> getAllRentalPlaces(){
        return rentalPlaces;
 
    }
    public RentalPlace getRentalPlace(int index){

        return rentalPlaces.get(index);

    }

    public void addRentalPlace(RentalPlace rentalPlace){
        for (RentalPlace place : rentalPlaces) {
            if(place.name == rentalPlace.name){
                throw new IllegalArgumentException("allerede eksisterende bolig, user -> addRentalPlace");

            }
        }
        rentalPlaces.add(rentalPlace);
        

    }

    
    

    public Rating getRating(Rating rating){
        if(personRatings.indexOf(rating) != -1 ){
            return personRatings.get(personRatings.indexOf(rating));
        }
        else{
            return null;
        }

    }
    public Rating getRatingByIndex(int index){
        return personRatings.get(index);

    }

    public void addRating(Rating rating){
        this.personRatings.add(rating);

    }
    public void newRating(int score, String comment, Object UserOrPlace){
        if (UserOrPlace instanceof User){
            Rating newRating = new Rating(score, comment, this, UserOrPlace);
            ((User) UserOrPlace).addRating(newRating);

        }
        else if (UserOrPlace instanceof RentalPlace){
            
            Rating newRating = new Rating(score, comment, this, UserOrPlace);
            ((RentalPlace) UserOrPlace).placeRatings.add(newRating);

        }

    
    }
    

    /*public void addRentedPlace(String nameOfRentalPlace, RentalPlace rentalPlace){
        rentedPlaces.put(nameOfRentalPlace, rentalPlace);


    }
    
    public void addRentedDates(String nameOfRentalPlace, LocalDate startDate, LocalDate endDate){
        List<LocalDate> startEndDates = new ArrayList<>();
        startEndDates.add(startDate);
        startEndDates.add(endDate);
        rentedDates.put(nameOfRentalPlace, startEndDates);

    }*/

    public void addBooking(RentalPlace bookedPlace, CharSequence bookingStart, CharSequence bookingEnd) {
        bookings.add(new Booking(bookedPlace, bookingStart, bookingEnd));
    }


    public List<String> getBookingList() {
        List<String> bookingList = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingList.add(booking.toString());
        }
        return bookingList;
    }
   
    public static void main(String[] args) throws ParseException {
        //User Jonas = new User("jonas");
        //System.out.println(Jonas.bookings.get(0));



    }
}