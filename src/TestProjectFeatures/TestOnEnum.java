package TestProjectFeatures;

public class TestOnEnum {
    public void checkInputValidity(TestEnum testEnum) {
        if (testEnum == TestEnum.TEST_ONE) {
            System.out.println("Test one");
        } else if (testEnum == TestEnum.TEST_TWO) {
            System.out.println("Test two");
        } else {
            System.out.println("Invalid input");
        }
    }
}
