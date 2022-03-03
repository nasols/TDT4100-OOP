package fire;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class User {
    // brukerinfo
    String firstName;
    String lastName; 
    LocalDate birthdate;
    int age;
    String mail; 
    
    // Brukerens ratings
    private List<Rating> personRatings = new ArrayList<>();

    // brukerens utleiesteder 
    List<RentalPlace> rentalPlaces = new ArrayList<>();

    // steder og datoer brukeren skal leie andre steder (egen ferie)
    List<RentalPlace> rentedPlaces = new ArrayList<>();
    List<LocalDate> rentedDates = new ArrayList<>();

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

    public List<RentalPlace> getAllRentalPlaces(){
        return rentalPlaces;
 
    }

    public void addRentalPlace(RentalPlace rentalPlace){
        rentalPlaces.add(rentalPlace);
        
    }

    public static void main(String[] args) throws ParseException {
        User Jonas = new User("jonas", "olsen", "2000-02-21", "nasbrigtols@gmail.com");
        System.out.println(Jonas.age);



    }
}