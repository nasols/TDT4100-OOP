package fire;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DataHandler implements IDataHandler {

    @Override
    public void writeData(Manager manager) throws FileNotFoundException {
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

                List<String> bookingStrings = user.getBookingInfo();

                writer.println(bookingStrings.size());

                for (String s : bookingStrings){
                    s = s.replaceAll("\n", ";");
                    writer.println(s);
                }
            }
        }
    }

    private static File getFile(String filename) {
        return new File(DataHandler.class.getResource("data/").getFile() + filename + ".txt");
    }

    @Override
    public Manager readData() throws FileNotFoundException {
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
                    bookingFromFile(username, bookingInfo, manager);
                }
            }
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

        CharSequence bookingStart = bookingInfo.get(1);
        CharSequence bookingEnd = bookingInfo.get(2);

        manager.addBookingOffline(username, rentalPlace, bookingStart, bookingEnd);
    }
}
