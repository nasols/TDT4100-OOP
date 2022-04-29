package fire;

import java.time.LocalDate;

public class Booking {
    private RentalPlace bookedPlace;
    private LocalDate bookingStart;
    private LocalDate bookingEnd;

    public Booking(RentalPlace bookedPlace, LocalDate bookingStart, LocalDate bookingEnd) {
        this.bookedPlace = bookedPlace;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
    }

    public String toString() {
        return bookedPlace.toString(false) + "\n Booket fra " + bookingStart + " til " + bookingEnd;
    }

    //Streng som beskriver en booking og skal lagres i fil
    public String toFileString() {
        return bookedPlace.getTitle() + ";" + this.bookingStart + ";" + this.bookingEnd;
    }

    public RentalPlace getBookedPlace() {
        return bookedPlace;
    }
}
