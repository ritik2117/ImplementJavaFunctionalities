import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Hello world!");
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);
        System.out.println(integerList.indexOf(4));

        Iterator<Integer> iterator = integerList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}