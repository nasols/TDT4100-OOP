package fire;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        try (PrintWriter writer = new PrintWriter(getDataFile())) {
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
    }

    private static String getDataFile() throws URISyntaxException {
        URI uri = new URI(DataHandler.class.getResource("data/").toString() + "dat.txt");
        return uri.getPath();
    }

    public Manager readData() throws FileNotFoundException, URISyntaxException{
        Manager manager = new Manager(); 
        try( Scanner scanner = new Scanner(new FileReader(getDataFile()))){
            while(scanner.hasNext()){
                ArrayList<String> brukerInfo = new ArrayList<>(Arrays.asList(scanner.nextLine().split(", ")));
                String username = brukerInfo.get(0);
                userFromFile(brukerInfo, manager);
                

                int antallBookinger = scanner.nextInt();
                for (int i = 0; i<antallBookinger; i ++){

                    ArrayList<String> bookingInfo = new ArrayList<>(Arrays.asList(scanner.nextLine().split("\n")));
                    bookingFromFile(username, bookingInfo, manager);
                }

                int antallBoliger = scanner.nextInt();
                for (int i = 0 ; i<antallBoliger ; i ++ ){

                    ArrayList<String> boligInfo = new ArrayList<>(Arrays.asList(scanner.nextLine().split("\n")));
                    boligFromFile(username, boligInfo, manager);
                    
                }

            }
        }
        return manager;
    }

    private void userFromFile(ArrayList<String> brukerInfo, Manager manager){
        // legger til passord her og, blir nextline igjen, altså format: username \n passord 
        manager.login(brukerInfo.get(0));
        manager.logout();


    }

    private void boligFromFile(String username, ArrayList<String> boligInfo, Manager manager){
    
        User owner = manager.getUser(username);
        String name = boligInfo.get(0);
        String description = boligInfo.get(1); 
        String[] fasaliteter = (boligInfo.get(2).split(", "));
        CharSequence[] dates = boligInfo.get(4).split(", ");
        manager.newRentalPlaceOffline(owner, name, description, dates, fasaliteter);


    }

    private void bookingFromFile(String username, ArrayList<String> bookingInfo, Manager manager){
        User userName = manager.getUser(username);
        String placeName = bookingInfo.get(0);
    }
}
