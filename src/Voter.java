import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public static void main(String[] args) {
        List<Voter> voters = Arrays.asList(
                new Voter("Sudesh", 58, "Male"),
                new Voter("Bimlesh", 56, "Female"),
                new Voter("Varun", 32, "Male"),
                new Voter("Priyal", 31, "Female"),
                new Voter("Ritik", 27, "Male")
        );

//        List<Integer> = voters.stream()
//                .map(Voter::getAge);
//        Print all the voters in dec order of their age and name
//        voters.stream().map(Voter::getVoterName).sorted((a,b) -> b.compareTo(a));
    }
}
