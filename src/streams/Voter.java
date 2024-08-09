package streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Voter {
    private String voterName;
    private int age;
    private String gender;

    public Voter(String voterName, int age, String gender) {
        this.voterName = voterName;
        this.age = age;
        this.gender = gender;
    }

    public String getVoterName() {
        return voterName;
    }

    public void setVoterName(String voterName) {
        this.voterName = voterName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "streams.Voter{" +
                "voterName='" + voterName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }

    public static void main(String[] args) {
        List<Voter> voters = Arrays.asList(
                new Voter("Sudesh", 58, "Male"),
                new Voter("Bimlesh", 56, "Female"),
                new Voter("Varun", 31, "Male"),
                new Voter("Priyal", 31, "Female"),
                new Voter("Ritik", 27, "Male")
        );

        /**
         * Print all the list of voters
         */
        Stream<Voter> voterStream1 = voters.stream();
        voterStream1.forEach((voter) -> System.out.println(voter.toString()));

        /**
         * Print all the voters in dec order of their age and name
         */
        /*voters.stream()
                .sorted((v1, v2) -> v2.getVoterName().compareTo(v1.getVoterName()))
                .sorted((v1, v2) -> v1.age - v2.age)
                .forEach(voter -> System.out.println(voter.getVoterName()));*/
        voters.stream()
                .sorted(Comparator.comparing((Voter voter) -> voter.getAge()).reversed().thenComparing((Voter voter) -> voter.getVoterName()))
                .forEach(System.out::println);
    }
}
