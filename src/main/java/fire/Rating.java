package fire;

import java.util.Date;

public class Rating {
    // Variabler
    private int score;
    private String comment;
    private Date ratingDate;
    private User rater;
    

    

    // Konstruktøren passer på gyldig parametre. Kommentar for ratingen er valgfritt
    public Rating(int score, String comment, User rater, User ratedUser) {
        if ((!validScore(score) || rater == null) && (rater == ratedUser)) {
            throw new IllegalArgumentException("Feil ved oppretting av rating");
        }

        this.score = score;
        this.comment = comment;
        this.rater = rater;
        
        if(ratedUser.personRatings.indexOf(this) != -1){

            ratedUser.personRatings.add(this);

        }

    }

    //konstruktør om man vil skrive rating om et sted. 
    public Rating(int score, String comment, User rater, RentalPlace ratedPlace) {
        if ((!validScore(score) || rater == null) && (rater == ratedPlace.owner)) {
            throw new IllegalArgumentException("Feil ved oppretting av rating");
        }

        this.score = score;
        this.comment = comment;
        this.rater = rater;

        if(ratedPlace.placeRatings.indexOf(this) != -1){

            ratedPlace.placeRatings.add(this);

        }
        
        
    }

    //Score er ratingverdien som går fra 0 til og med 10
    private boolean validScore(int score) {
        if (score >= 0 && score <= 10) {
            return true;
        }
        return false;
    }
}

