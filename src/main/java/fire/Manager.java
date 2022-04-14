package fire;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

public class Manager {
    
    private User currentUser; 
    private String currentUsername; 

    private LinkedHashMap<String, RentalPlace> rentalPlaces = new LinkedHashMap<>();
    private LinkedHashMap<String, User> users = new LinkedHashMap<>();




    public String getCurrentUsername() {
        return currentUsername;
    }
    
    // sjekker om user allerede ligger i listen over brukere
    private boolean validateUsername(String username){
        if(users.containsKey(username) == true){
            return true;

        }
        else{
            return false; 

        }
        
    }

    public void login(String username){
        User newUser = new User(username);

        if(validateUsername(username) == false){
            users.put(username, newUser);
            currentUser = newUser;
            currentUsername = username;

        }
        else{
            currentUser = users.get(username); 
            currentUsername = username;

        }

    }
/* 
    public List rentalDescriptionList() {
        for (i in rentalplaces) {
            List.append(rentalPlaces[i].toString());
        }
        return List
    } */

    public void newRentalPlace(String name, String description, CharSequence availableStart, CharSequence availableEnd, String ... args){

        currentUser.newRentalPlace(name, description, availableStart, availableEnd, args);
        RentalPlace newPlace = currentUser.getRentalPlace(name);
        if (rentalPlaces.containsKey(name) == false){
            rentalPlaces.put(name, newPlace);
        }


    }
    // leier plass, input er dato og hvor mange dager du vil leie, samt navnet på stedet du vil leie
    public void rentPlace(CharSequence date, int numberOfDays, String nameOfPlace){

        LocalDate rentalDateStart = LocalDate.parse(date);
        LocalDate rentalDateEnd = rentalDateStart.plusDays(numberOfDays);

        RentalPlace wishedRented = rentalPlaces.get(nameOfPlace);

        List<LocalDate> availableDates = wishedRented.availableDates;

        int counter = 0;

        for(int i = 0; i<availableDates.size();  i+=2){
            LocalDate availableStart = availableDates.get(i);
            LocalDate availableEnd = availableDates.get(i+1);

            

            if((availableStart.isBefore(rentalDateStart) || availableStart.isEqual(rentalDateStart)) && (availableEnd.isAfter(rentalDateEnd) || availableEnd.isEqual(rentalDateEnd))){
                currentUser.addRentedPlace(nameOfPlace, wishedRented);
                currentUser.addRentedDates(nameOfPlace, rentalDateStart, rentalDateEnd);
                
                

                // sjekker om datoene som du vil leie fra - til er innenfor et intervall av ledige datoer, isåfall gjør det riktige
                if(availableStart.isEqual(rentalDateStart) && availableEnd.isEqual(rentalDateEnd)){
                    wishedRented.availableDates.remove(rentalDateEnd);
                    wishedRented.availableDates.remove(rentalDateStart);
                    counter ++;

                }
                else if (availableStart.isEqual(rentalDateStart) && !(availableEnd.isEqual(rentalDateEnd))){
                    wishedRented.availableDates.add(availableDates.indexOf(availableStart), rentalDateEnd);
                    wishedRented.availableDates.remove(rentalDateStart);
                    counter ++;
                    
                }
                else if (!(availableStart.isEqual(rentalDateStart)) && availableEnd.isEqual(rentalDateEnd)){
                    wishedRented.availableDates.add(availableDates.indexOf(availableEnd), rentalDateStart);
                    wishedRented.availableDates.remove(rentalDateEnd);
                    counter ++;
                    
                }
                else if(!(availableStart.isEqual(rentalDateStart) && availableEnd.isEqual(rentalDateEnd))){
                    wishedRented.availableDates.add(availableDates.indexOf(availableEnd), rentalDateStart);
                    wishedRented.availableDates.add(availableDates.indexOf(availableEnd), rentalDateEnd);
                    counter ++; 

                }

            

            }

        }
        if (counter == 0){
            throw new IllegalArgumentException("neineinei");

        }



    }

    public void newRating(int score, String comment, Object UserOrPlace){
        currentUser.newRating(score, comment, UserOrPlace);

    }

    public User getUser(String username){

        if(users.containsKey(username)){
            return users.get(username);

        }
        else{
            throw new IllegalArgumentException("ingen bruker ved det brukernavnet");

        }
    
        
    }
    public User getCurrentUser(){
        return users.get(currentUsername);

    }



    // mana
    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.login("Jonas");

        System.out.println(manager.currentUsername);
        manager.newRentalPlace("name", "description", "2022-05-01", "2022-05-28", "masse kult");

        manager.login("Henrik");
        manager.rentPlace("2022-05-05", 10, "name");

        System.out.println(manager.currentUsername);
        System.out.println(manager.currentUser.rentedDates);

        manager.login("Jonas");
        manager.rentPlace("2022-05-16", 4, "name");

        System.out.println(manager.currentUser.rentedDates);

        System.out.println(manager.users);



        

    


    }


    
}
