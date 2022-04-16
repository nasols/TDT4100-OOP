package fire;

public class Booking {
    private RentalPlace bookedPlace;
    private CharSequence bookingStart;
    private CharSequence bookingEnd;

    public Booking(RentalPlace bookedPlace, CharSequence bookingStart, CharSequence bookingEnd) {
        this.bookedPlace = bookedPlace;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
    }

    public String toString() {
        return bookedPlace.toString(false) + "\n Booket fra " + bookingStart + " til " + bookingEnd;
    }
}
