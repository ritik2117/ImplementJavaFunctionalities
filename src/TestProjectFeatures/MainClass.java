package TestProjectFeatures;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
//        System.out.println(TestEnum.TEST_ONE);
//        System.out.println(TestEnum.TEST_ONE.name());
//        System.out.println(TestEnum.TEST_ONE.toString());
//        System.out.println(TestEnum.TEST_ONE.ordinal());
//        System.out.println(TestEnum.fromValue("Test one"));
//        System.out.println(TestEnum.fromValue(null));
//        System.out.println(Arrays.toString(TestEnum.values()));
//        System.out.println(TestEnum.TEST_ONE.getValue());

        String irnValue = "irn:intuit:datalake:dataasset:prd:hivetable:intuit_expertise_practicemanagement_dwh.ecosystem_note";
        String[] irnValueSplit = irnValue.split(":");
//        System.out.println(Arrays.toString(irnValueSplit));
//        System.out.println(irnValueSplit[irnValueSplit.length - 1]);
        String qualifiedName = irnValueSplit[irnValueSplit.length - 1];
        System.out.println(qualifiedName);
        String[] qualifiedNameSplit = qualifiedName.split("\\.");
        System.out.println(Arrays.toString(qualifiedNameSplit));

        if (Optional.ofNullable(null) == Optional.empty()) {
            System.out.println("null");
        } else {
            System.out.println("not null");
        }

//        Test input validity of enum values
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        TestOnEnum testOnEnum = new TestOnEnum();
        testOnEnum.checkInputValidity(TestEnum.valueOf(input));
    }
}
