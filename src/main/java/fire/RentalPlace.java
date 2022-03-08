package fire;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalPlace {
    // brukeren som leier ut stedet 
    User owner;

    // tittel på stedet/ annonsen 
    String name;

    // tenker availableDates alltid legger til par av datoer, startdato ledig --> sluttdato ledig, så når noen vil booke sjekkes bare om datoen de vil bo er innenfor intervall startdato ledig --> sluttdato ledig
    // availableDates vil då alltid ha 2*n element
    // liste over tilgjengelige datoer for leie
    List<LocalDate> availableDates = new ArrayList<>();

    // liste over ratings plassen har fått
    public List<Rating> placeRatings = new ArrayList<>();

    // liste over fasaliteter til boligen, av type badebasseng, wifi, o.l 
    List<String> facilities = new ArrayList<>();

    // beskrivelse av stedet 
    String description; 


    // validations 
    // validerer bookingdatoene er etter dagens dato
    private boolean validateAvailableDate(LocalDate availableDate){

        LocalDate today = LocalDate.now();
        if (today.isBefore(availableDate)){
            return true;
        }
        else{
            return false;

        }
        
    }

    // konstruktør 
    public RentalPlace(User owner, String name, String description, CharSequence availableStart, CharSequence availableEnd, String ... args){

        LocalDate availableStartDate = LocalDate.parse(availableStart);
        LocalDate availableEndDate = LocalDate.parse(availableEnd);
        if (this.owner == owner){
            return;
        }
    
        //skaper assosiasjonen mellom eier og plassen
        this.owner = owner;
        this.name = name;
        this.description = description;
        owner.addRentalPlace(this);
        
        for (String e: args){
            facilities.add(e);

        }

        if (validateAvailableDate(availableStartDate) && validateAvailableDate(availableEndDate) && availableStartDate.isBefore(availableEndDate)){
            this.availableDates.add(availableStartDate);
            this.availableDates.add(availableEndDate);

        }
        else{
            throw new IllegalArgumentException("feil format på dato");

        }

 
    }
    public void addRating(Rating rating){
        placeRatings.add(rating);

    }
    public Rating getRating(Rating rating){
        if(placeRatings.indexOf(rating) != -1 ){
            return placeRatings.get(placeRatings.indexOf(rating));
        }
        else{
            return null;
        }
    }




    public static void main(String[] args) throws ParseException {
        User Jonas = new User("jonas", "olsen", "2000-02-21", "nasbrigtzifsoæmaofs.com");
        RentalPlace hinna = new RentalPlace(Jonas, "hinnna kåken", "fin og flott plass", "2023-02-02", "2023-02-10", "badebasseng", "tog like ved :)");
        Jonas.addRentalPlace(hinna);
        
        System.out.println(Jonas.rentalPlaces.get(0).name);

        
    }
    
}
