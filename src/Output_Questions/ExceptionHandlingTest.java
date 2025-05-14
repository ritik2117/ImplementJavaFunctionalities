package Output_Questions;

public class ExceptionHandlingTest {

    static boolean checkFinally() {
        try {
            System.out.println("Inside try");
            return false;
//            throw new Exception();
        }
//        catch (Exception e) {
//            System.out.println("catch");
//        }
        finally {
            System.out.println("Inside finally");
        }
//        System.out.println("end");
//        return true;
    }

    public static void main(String[] args) {
        checkFinally();
        try {
            System.out.println("try");
//            throw new Exception();
        }
        catch (Exception e) {
            System.out.println("catch");
        }
//        System.out.println("end");
    }
}
