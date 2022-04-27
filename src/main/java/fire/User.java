package fire;

import java.util.ArrayList;
import java.util.List;


public class User {
    // brukerinfo
    private String username;
    
    // brukerens utleiesteder 
    private List<RentalPlace> rentalPlaces = new ArrayList<>();

    // steder og datoer brukeren skal leie andre steder (egen ferie)
    private List<Booking> bookings = new ArrayList<>();
    
    // mindre konstruktør
    public User(String username){
        if (username.replaceAll(" ", "").equals("")) {
            throw new IllegalArgumentException("Vennligst skriv inn et brukernavn");
        }
        this.username = username; 

    }
    // getters & setters 
    public String getUsername(){
        return this.username;

    }
    public List<RentalPlace> getAllRentalPlaces(){
        return rentalPlaces;
 
    }
    public RentalPlace getRentalPlace(int index){
        return rentalPlaces.get(index);
    }

    public void addRentalPlace(RentalPlace rentalPlace){
        rentalPlaces.add(rentalPlace);
        
    }

    public void addBooking(RentalPlace bookedPlace, CharSequence bookingStart, CharSequence bookingEnd) {
        
        bookings.add(new Booking(bookedPlace, bookingStart, bookingEnd));
    }

    public List<String> getBookingList() {
        List<String> bookingList = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingList.add(booking.toString());
        }
        return bookingList;
    }

    public List<String> getBookingListFileWrite() {
        List<String> bookingList = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingList.add(booking.toFileString());
        }
        return bookingList;
    }
    
    public List<Booking> getBookings(){
        return this.bookings;
    }

}