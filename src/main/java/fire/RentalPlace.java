package fire;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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


    public boolean validateRentalDate(LocalDate ... checkdates){

        /**
         sjekker om input datoer er innenfor et intervall i listen over availableDates for utleiestedet. 
         hvis de er return true 
         hvis ikke return false
         */
        List<LocalDate> checkDatesList = new ArrayList<>(Arrays.asList(checkdates));

        LocalDate leieFra = checkDatesList.get(0);
        LocalDate leieTil = checkDatesList.get(1);


        if (validateAvailableDate(checkdates)){

            for (int i = 0; i < availableDates.size(); i += 1){
                // i partall -> fra-dato
                // i oddetall -> til-dato
                if ((leieFra.isBefore(availableDates.get(i))) && i%2 != 0){
                    if(leieTil.isBefore(availableDates.get(i))){
                        return true;
                    }
                    return false;
                }
                else if ((leieFra.isBefore(availableDates.get(i))) && i%2 == 0){
                    return false;
                }

            }
        }
        return false;
    }


    
    private boolean validateAvailableDate(LocalDate ... checkdates){
        /** 
         Input 1 eller 2 datoer, 
         Hvis 1 dato; sjekker om datoen er etter dags dato, dette brukes feks når du lage ny utleie plass og legger inn en "Ledig fra" dato
         Hvis 2 datoer; sjekker om dato1 er etter dags dato, og om dato 2 er etter dato 1 

        */
        LocalDate today = LocalDate.now();

        List<LocalDate> checkhDatesList = new ArrayList<>(Arrays.asList(checkdates));


        if(checkhDatesList.size() == 1){
            LocalDate dato1 = checkhDatesList.get(0);
            return today.isBefore(dato1);
        }

        else{
            LocalDate dato1 = checkhDatesList.get(0);
            LocalDate dato2 = checkhDatesList.get(1);

            

            if ((today.isBefore(dato1) || today.isEqual(dato1)) && (dato1.isBefore(dato2))){
                return true;

            }
            return false;

        }
        
        
    
        
    }

    // konstruktør 
    public RentalPlace(User owner, String name, String description, CharSequence availableStart, CharSequence availableEnd, String ... fasiliteter){

        LocalDate availableStartDate = LocalDate.parse(availableStart);
        LocalDate availableEndDate = LocalDate.parse(availableEnd);
        if (this.owner == owner){
            return;
        }
    
        //skaper assosiasjonen mellom eier og plassen
        this.owner = owner;
        this.name = name;
        this.description = description;
        
        
        for (String e: fasiliteter){
            facilities.add(e);

        }

        if (validateAvailableDate(availableStartDate, availableEndDate)){
            this.availableDates.add(availableStartDate);
            this.availableDates.add(availableEndDate);

        }
        else{
            throw new IllegalArgumentException("feil format på dato");

        }

 
    }

    // konstruktør2, "offline" konstruktør 
    // vet at datoer her vil ver gyldige, altså trenger ikke validering, denne brukes bare ved filLesning av allerede-validerte boliger. 
    public RentalPlace(User owner, String name, String description, CharSequence[] fromToDatesInput, String[] fasiliteter){
        ArrayList<LocalDate> fromToDates = new ArrayList<>();
        ArrayList<CharSequence> chars = new ArrayList<>(Arrays.asList(fromToDatesInput));
        chars.stream().forEach(e -> fromToDates.add(LocalDate.parse(e)));
        if (this.owner == owner){
            return;
        }
    
        //skaper assosiasjonen mellom eier og plassen
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.availableDates = fromToDates;
        
        
        
        for (String e: fasiliteter){
            facilities.add(e);

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

    public boolean inList(List<RentalPlace> compareList){
        return compareList.contains(this);
    }

    public String toString(boolean includeDates) {
        //return owner + name + description;
        String fas = ""; 
        String date = "";

        for (String e : facilities){
            fas = fas + e + ", ";
        }

        if (includeDates) {
            for (LocalDate dat : availableDates){
            date = date + dat + ", ";
            }
        }
        
        return this.name + "\n" + description + "\n" + fas + "\n" + this.owner.getUsername() + "\n" + date;
    }


    public static void main(String[] args) throws ParseException {
        User Jonas = new User("Jonas");
        CharSequence[] dates = {"2023-02-03", "2023-02-20"};
        String[] fasaliteter = {"badebasseng", "tog like ved :)", "fugletitting", "internett"};
        RentalPlace hinna = new RentalPlace(Jonas, "hinnna kåken", "fin og flott plass", dates, fasaliteter);
        
        LocalDate dato1 = LocalDate.parse("2023-02-05");
        LocalDate dato2 = LocalDate.parse("2023-02-18");

        System.out.println(hinna.validateRentalDate(dato1, dato2));
        System.out.println(hinna.toString(false));

        
        
    }
    
}
