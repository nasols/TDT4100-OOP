package fire;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class User {
    // brukerinfo
    private String username;
    private String firstName;
    private String lastName; 
    LocalDate birthdate;
    private int age;
    String mail; 
    
    // Brukerens ratings
    List<Rating> personRatings = new ArrayList<>();

    // brukerens utleiesteder 
    LinkedHashMap<String, RentalPlace> rentalPlaces = new LinkedHashMap<>();

    // steder og datoer brukeren skal leie andre steder (egen ferie)
    LinkedHashMap<String, RentalPlace> rentedPlaces = new LinkedHashMap<>();
    LinkedHashMap<String, List<LocalDate>> rentedDates = new LinkedHashMap<>();

    // validations 

    // sjekker om navn bare inneholder bokstaver 
    private boolean validateName(String inputName){
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

    }
    
    // mindre konstruktør
    public User(String username){
        this.username = username; 

    }
    // getters & setters 

    public String getUsername(){
        return this.username;

    }
    public String getFirstName(){
        return this.firstName;

    }
    public String getLastName(){
        return this.lastName;
        
    }
    public int getAge(){
        return this.age;

    }



    public List<RentalPlace> getAllRentalPlaces(){
        return null;
 
    }
    public RentalPlace getRentalPlace(String name){

        return rentalPlaces.get(name);

    }

    public void addRentalPlace(String name, RentalPlace rentalPlace){
        rentalPlaces.put(name, rentalPlace);
        
    }
    public void newRentalPlace(String name, String description, CharSequence availableStart, CharSequence availableEnd, String ... args){
        if(rentalPlaces.containsKey(name) == false){
            RentalPlace newPlace = new RentalPlace(this, name, description, availableStart, availableEnd, args);
            addRentalPlace(name, newPlace);

        }
       
        else{
            throw new IllegalArgumentException("allerede eksisterende boplass"); 
            
        }
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

    public void addRentedPlace(String nameOfRentalPlace, RentalPlace rentalPlace){
        rentedPlaces.put(nameOfRentalPlace, rentalPlace);


    }
    public void addRentedDates(String nameOfRentalPlace, LocalDate startDate, LocalDate endDate){
        List<LocalDate> startEndDates = new ArrayList<>();
        startEndDates.add(startDate);
        startEndDates.add(endDate);
        rentedDates.put(nameOfRentalPlace, startEndDates);

    }


   
    public static void main(String[] args) throws ParseException {
        User Jonas = new User("jonas", "olsen", "2000-02-21", "nasbrigtols@gmail.com");
        System.out.println(Jonas.age);



    }
}