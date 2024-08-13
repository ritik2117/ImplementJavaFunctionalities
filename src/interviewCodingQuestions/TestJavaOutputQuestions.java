package interviewCodingQuestions;

public class TestJavaOutputQuestions {
    public static void main(String[] args) {
        String s1 = "Ritik";
        String s2 = "Ritik";
        String s3 = new String("Ritik");
        s3 = "Ritik Gupta";

        System.out.println(s1.equals(s2));
        System.out.println(s2.equals(s3));
        System.out.println(s1.equals(s3));
    }
}
