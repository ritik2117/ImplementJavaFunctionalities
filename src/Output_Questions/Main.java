package Output_Questions;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

public class Main {

    static int count() {
        try {
            return 1;
        } finally {
            System.out.print("In Finally Block ");
        }
}
    public static void main(String[] args) {
        //\u000d System.out.println("hello");
//        ----
        String s1 = "Java";
        String s2 = "Java";
        StringBuilder sb1 = new StringBuilder();
        sb1.append("Ja").append("va");
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
        System.out.println(sb1.toString() == s1);
        System.out.println(sb1.toString().equals(s1));
//        ----
        int i = 20+ +9- -12+ +4- -13+ +19;
        System.out.println(i);
//        ----
        String a_b;
//        System.out.print($);
//        System.out.print(a_b);
//        ----
//        switch(x) {
//            case x>70:
//                System.out.println("True");
//                break;
//            case 65<x<=70:
//                System.out.println("False");
//                break;
//        }
//        ----
        System.out.print(count());
        int[] array = {4, 2, 7, 1, 5, 3, 6};
        int k = 3; // Find the 3rd smallest element
        OptionalInt kthSmallest = Arrays.stream(array)
                .sorted()
                .skip(k - 1)
                .findFirst();
    }
}
