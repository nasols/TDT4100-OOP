package fire;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RentalPlace {

    private User owner;

    private String name;

    // Liste som representerer tidsintervaller hvor leiligheten er tilgjengelig på formen "startDato, sluttdato, ..., startDato, sluttDato"
    private List<LocalDate> availableDates = new ArrayList<>();

    private String description; 

    // konstruktør 
    public RentalPlace(User owner, String name, String description, CharSequence availableStart, CharSequence availableEnd){

        if (name.replaceAll(" ", "").equals("")) {
            throw new IllegalArgumentException("Vennligst gi stedet et navn");
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
        //sjekker om startdato er etter dags dato, og om sluttdato er etter startdato
    
        LocalDate today = LocalDate.now();

        if (startDate.isBefore(today)) {
            throw new IllegalArgumentException("Dato(ene) valgt er før dagsdato");
        } 
        if (endDate.isBefore(startDate) || endDate.isEqual(startDate)){
            throw new IllegalArgumentException("Sluttdatoen må være etter startdatoen");
        }
        return true;
    }

    public boolean inList(List<RentalPlace> compareList){
        return compareList.contains(this);
    }

    public String toString(boolean includeDates) {
        // Beskrivelse av stedet som skal vises til bruker i brukergrensesnittet

        String avaliableDescription = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        if (includeDates) {
            if (availableDates.size() < 2) {
                avaliableDescription = "Denne leiligheten er ikke tilgjengelig for øyeblikket";
            }
            else {
                int idx = 1;
                while (idx < availableDates.size()) {
                    String fromDate = availableDates.get(idx-1).format(formatter);
                    String toDate = availableDates.get(idx).format(formatter);
                    avaliableDescription += "Ledig fra: " + fromDate + " til " + toDate + "\n";
                    idx += 2;
                }
            }
        }
        
        return String.format(
                """
                    ---------------------------------------
                    Navn: %s
                    Eier: %s
                    %s  
                    %s""",
            name,
            owner.getUsername(),
            description,
            avaliableDescription);
    }
}
