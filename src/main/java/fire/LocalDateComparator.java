package fire;

import java.time.LocalDate;
import java.util.Comparator;

public class LocalDateComparator implements Comparator<LocalDate> {

    @Override
    public int compare(LocalDate dato1, LocalDate dato2) {
        // TODO Auto-generated method stub
        if (dato1.isBefore(dato2)){
            return -1;
        }
        else if (dato2.isBefore(dato1)){
            return 1;
        }
        return 0;
    }
    
}
