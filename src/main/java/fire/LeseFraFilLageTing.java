

package fire;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * LeseFraFilLageTing
 */
public class LeseFraFilLageTing {

    Manager manager = new Manager(); 

    public void henteFraFil(String filName) throws FileNotFoundException{
        try( Scanner scanner = new Scanner(new FileReader(filName))){
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
    }

    public void userFromFile(ArrayList<String> brukerInfo, Manager manager){
        // legger til passord her og, blir nextline igjen, altså format: username \n passord 
        manager.login(brukerInfo.get(0));
        manager.logout();


    }

    public void boligFromFile(String username, ArrayList<String> boligInfo, Manager manager){
    
        User owner = manager.getUser(username);
        String name = boligInfo.get(0);
        String description = boligInfo.get(1); 
        String[] fasaliteter = (boligInfo.get(2).split(", "));
        CharSequence[] dates = boligInfo.get(4).split(", ");
        manager.newRentalPlaceOffline(owner, name, description, dates, fasaliteter);


    }

    public void bookingFromFile(String username, ArrayList<String> bookingInfo, Manager manager){
        User userName = manager.getUser(username);
        String placeName = bookingInfo.get(0);
    }

    
}
/**
 * må finne ut format, sån som nå vil den måtte lese alle brukere og lage de,
 * så må alle boliger lages
 * så må alle bookings lages
 * dette er fordi vi trenger brukere for å lage boliger, og boliger for å lage bookings. 
 * trenger forsåvidt en slags id på alle ting som skrives til fil, altså alle boliger vil skrives til fil med en "owner" som første del av strengen/ listen med info så me vett ka bruker boligen høre til 
 * booking trenge det samme, så når en booking skrives til fil må me ha en linje som indikere hvilken User bookingen hører til. 
 */
/**
 * alternativt kan me lese bruker - så alle boligene t den brukeren 
 * så neste bruker og alle dens boliger 
 * osv
 * så alle bookings ittepå 
 */