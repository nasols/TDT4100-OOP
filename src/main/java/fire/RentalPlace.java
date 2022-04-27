package fire;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class RentalPlace {
    // brukeren som leier ut stedet 
    private User owner;

    // tittel på stedet/ annonsen 
    private String name;

    // tenker availableDates alltid legger til par av datoer, startdato ledig --> sluttdato ledig, så når noen vil booke sjekkes bare om datoen de vil bo er innenfor intervall startdato ledig --> sluttdato ledig
    // availableDates vil då alltid ha 2*n element
    // liste over tilgjengelige datoer for leie
    private List<LocalDate> availableDates = new ArrayList<>();

    // beskrivelse av stedet 
    private String description; 

    // konstruktør 
    public RentalPlace(User owner, String name, String description, CharSequence availableStart, CharSequence availableEnd){

        String pattern = "([a-zA-Z])\\1*";
        if (name.replaceAll(" ", "").equals("")) {
            throw new IllegalArgumentException("Vennligst gi stedet et navn");
        }
        if (name.matches(pattern)){
            throw new IllegalArgumentException("Stedsnavnet kan ikke bestå av bare repeterende karakterer");
        }

        LocalDate availableStartDate = LocalDate.parse(availableStart);
        LocalDate availableEndDate = LocalDate.parse(availableEnd);
        if (this.owner == owner){
            return;
        }
    
        //skaper assosiasjonen mellom eier og plassen
        this.owner = owner;
        this.name = name;
        this.description = description;

        if (validateDateInterval(availableStartDate, availableEndDate)){
            this.availableDates.add(availableStartDate);
            this.availableDates.add(availableEndDate);

        }
        else {
            throw new IllegalArgumentException("feil format på dato -> rentalplace -> RentalPlace");
        }
    }

    public String getTitle() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
    public List<LocalDate> getAvaliableDates() {
        return this.availableDates;
    }

    public String getAvaliableDatesString() {
        String dateString = "";
        for (LocalDate date : availableDates) {
            dateString = dateString + date.toString() + ",";
        }
        return dateString;
    }
    
    public User getOwner(){
        return this.owner;

    }

    public boolean validateRentalDate(LocalDate startDate, LocalDate endDate){

        /*
         sjekker om input datoer er innenfor et intervall i listen over availableDates for utleiestedet. 
         hvis de er return true 
         hvis ikke return false
         */

        if (validateDateInterval(startDate, endDate)){
            for (int i = 0; i < availableDates.size(); i += 1){
                // i partall -> fra-dato
                // i oddetall -> til-dato
                if ((startDate.isBefore(availableDates.get(i))) && i%2 != 0){
                    if(endDate.isBefore(availableDates.get(i))){
                        return true;
                    }

                    throw new IllegalArgumentException("Leiligheten er ikke tilgjengelig i denne tidsperioden");
                }
                else if (startDate.isBefore(availableDates.get(i)) && i%2 == 0){
                    throw new IllegalArgumentException("Leiligheten er ikke tilgjengelig i denne tidsperioden");
                }
            }
        }
        return false;
    }

    private boolean validateDateInterval(LocalDate startDate, LocalDate endDate){
        //sjekker om dato1 er etter dags dato, og om dato 2 er etter dato 1 
    
        LocalDate today = LocalDate.now();

        if (startDate.isBefore(today)) {
            throw new IllegalArgumentException("Dato(ene) valgt er før dagsdato");
        } 
        if (endDate.isBefore(startDate)){
            throw new IllegalArgumentException("Sluttdatoen må være etter startdatoen");
        }
        return true;
    }



    // konstruktør2, "offline" konstruktør 
    // vet at datoer her vil ver gyldige, altså trenger ikke validering, denne brukes bare ved filLesning av allerede-validerte boliger. 
    public RentalPlace(User owner, String name, String description, CharSequence[] fromToDatesInput){
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

    }

    public boolean inList(List<RentalPlace> compareList){
        return compareList.contains(this);
    }

    public String toString(boolean includeDates) {
        //return owner + name + description;
        String avaliableDescription = "";

        if (includeDates) {
            for (LocalDate dat : availableDates){
            avaliableDescription = avaliableDescription + dat + ", ";
            }
        }
        
        return String.format(
                """
                    --------------------------
                    Navn: %s
                    Eier: %s
                    %s  
                    %s""",
            name,
            owner.getUsername(),
            description,
            avaliableDescription);
    }
    
    public static void main(String[] args) throws ParseException {

        
    }

    
    
}
