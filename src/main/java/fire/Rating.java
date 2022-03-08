package fire;

import java.text.ParseException;
import java.util.Date;

public class Rating {
    // Variabler
    private int score;
    private String comment;
    private Date ratingDate;
    private User rater;
    

    

    // Konstruktøren passer på gyldig parametre. Kommentar for ratingen er valgfritt
    // UserOrPlace kan ver av hvilken som helst type, men blir bare brukt om den er av type user eller eller rentalplace 
    public Rating(int score, String comment, User rater, Object UserOrPlace) {
        if ((!validScore(score) || rater == null) && (rater == ((User) UserOrPlace))) {
            throw new IllegalArgumentException("Feil ved oppretting av rating");
        }

        if (UserOrPlace instanceof User){
            Rating checkRating = ((User) UserOrPlace).getRating(this);

            if(checkRating == null){

                this.score = score;
                this.comment = comment;
                this.rater = rater;

                ((User) UserOrPlace).addRating(this);

            }
            else{
                throw new IllegalArgumentException("allerede eksisterende rating"); 

            }

        }

        else if(UserOrPlace instanceof RentalPlace){
            Rating checkRating = ((RentalPlace) UserOrPlace).getRating(this);

            if(checkRating == null){
                this.score = score;
                this.comment = comment;
                this.rater = rater;

                ((RentalPlace) UserOrPlace).addRating(this);

            }
            else{
                throw new IllegalArgumentException("allerede eksisterende rating");
            }


        }

        

    }

    public String getComment(){
        return comment;
        
    }



    //Score er ratingverdien som går fra 0 til og med 10
    private boolean validScore(int score) {
        if (score >= 0 && score <= 10) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws ParseException {

        User Jonas = new User("jonas", "olsen", "2000-02-21", "nasbrigtols@gmail.com");
        User Henrik = new User("henrik", "log", "2000-03-18", "henrilja@gmail.com");

        RentalPlace hinna = new RentalPlace(Jonas, "hinnna kåken", "fin og flott plass", "2023-02-02", "2023-02-10", "badebasseng", "tog like ved :)");

        Rating rating = new Rating(5, "nice", Jonas, Henrik);
        Rating rating1 = new Rating(5, "rått", Henrik, hinna);

        System.out.println(Henrik.getRating(rating).rater.firstName);
        System.out.println(Jonas.getRentalPlace(hinna).getRating(rating1).comment);
        
    }
}

