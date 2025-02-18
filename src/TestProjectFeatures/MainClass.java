package TestProjectFeatures;

import java.util.Arrays;

public class MainClass {
    public static void main(String[] args) {
        System.out.println(TestEnum.TEST_ONE);
        System.out.println(TestEnum.TEST_ONE.name());
        System.out.println(TestEnum.TEST_ONE.toString());
        System.out.println(TestEnum.TEST_ONE.ordinal());
        System.out.println(TestEnum.fromValue("Test one"));
        System.out.println(Arrays.toString(TestEnum.values()));
        System.out.println(TestEnum.TEST_ONE.getValue());
    }
}
