package fire;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DataHandler implements IDataHandler {

    @Override
    public void writeData(Manager manager) throws FileNotFoundException, URISyntaxException {
        try (PrintWriter writer = new PrintWriter(getFile("dat"))) {
            for (User user : manager.getUsers()) {
                
                writer.println(user.getUsername());
                
                List<RentalPlace> rentalPlaces = user.getAllRentalPlaces();
                writer.println(rentalPlaces.size());

                for (RentalPlace place : rentalPlaces) {
                    String description = place.getDescription();
                    //Bytter ut "\n" med "<br>" for beskrivelsen av stedene
                    description = description.replaceAll("\n", "<br>");
                    writer.println(String.format("%s;%s;%s", place.getTitle(), description, place.getAvaliableDatesString()));
                }
            } 
        }
        try ( PrintWriter writer = new PrintWriter(getFile("datbookings"))){
            for (User user : manager.getUsers()) {
                writer.println(user.getUsername());
                System.out.println("Username: " + user.getUsername());

                List<String> bookingStrings = user.getBookingListFileWrite();

                System.out.println("Bookings:");
                bookingStrings.forEach(l -> System.out.println(l));

                writer.println(bookingStrings.size());

                for (String s : bookingStrings){
                    s = s.replaceAll("\n", ";");
                    writer.println(s);

                }
            }
        }
    }

    private static String getFile(String filename) throws URISyntaxException {
        //Bruker URI istedet for File slik at mapper kan ha mellomrom
        URI uri = new URI(DataHandler.class.getResource("data/").toString() + filename + ".txt");
        return uri.getPath();
    }

    @Override
    public Manager readData() throws FileNotFoundException, URISyntaxException{
        Manager manager = new Manager(); 
        try( Scanner scanner = new Scanner(new FileReader(getFile("dat")))){
            while(scanner.hasNext()){
                
                ArrayList<String> brukerInfo = new ArrayList<>(Arrays.asList(scanner.nextLine().split("\n")));
                String username = brukerInfo.get(0);
                userFromFile(brukerInfo, manager);

                int antallBoliger = Integer.parseInt(scanner.nextLine());
                
                for (int i = 0 ; i<antallBoliger ; i ++ ){

                    ArrayList<String> boligInfo = new ArrayList<>(Arrays.asList(scanner.nextLine().split(";")));
                    boligFromFile(username, boligInfo, manager);
                }
            }
        }
        try ( Scanner scanner = new Scanner(new FileReader(getFile("datbookings")))){

            while ( scanner.hasNext() ){
                String username = scanner.nextLine();
                
                int antallBookings = Integer.parseInt(scanner.nextLine());

                for (int i = 0; i < antallBookings ; i ++){
                    ArrayList<String> bookingInfo = new ArrayList<>(Arrays.asList(scanner.nextLine().split(";")));
                    bookingInfo.remove("");
                    bookingFromFile(username, bookingInfo, manager);
                }
            }
            //scanner.close();
        }
        return manager;
    }

    private void userFromFile(ArrayList<String> brukerInfo, Manager manager){
        // legger til passord her og, blir nextline igjen, altså format: username \n passord 
        manager.login(brukerInfo.get(0));
    }

    private void boligFromFile(String username, ArrayList<String> boligInfo, Manager manager){
        User owner = manager.getUser(username);
        String name = boligInfo.get(0);
        String description = boligInfo.get(1); 
        CharSequence[] dates = boligInfo.get(2).split(",");
        manager.newRentalPlaceOffline(owner, name, description, dates);
    }
    
    private void bookingFromFile(String username, ArrayList<String> bookingInfo, Manager manager){
   
        RentalPlace rentalPlace = manager.getRentalPlaceByName(bookingInfo.get(0));
        CharSequence bookingStart = bookingInfo.get(3);
        CharSequence bookingEnd = bookingInfo.get(4);
        manager.getUsers().stream().forEach(u -> System.out.println(u.getUsername()));
        // kan ikke bruke getCurrentUser()
        manager.addBookingOffline(username, rentalPlace, bookingStart, bookingEnd);
    }
    
    

}
