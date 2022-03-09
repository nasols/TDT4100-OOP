package fire;

import java.util.ArrayList;
import java.util.List;

public class Test {


    public static void main(String[] args) {
        List<Integer> tall = new ArrayList<>();
        tall.add(1);
        tall.add(2);
        tall.add(3);
        System.out.println(tall.get(1));
        tall.add(tall.indexOf(2), 4);
        tall.add(tall.indexOf(2), 5);
        System.out.println(tall.get(1));
        System.out.println(tall.get(2));
        System.out.println(tall.get(3));


        
        
    }
}
