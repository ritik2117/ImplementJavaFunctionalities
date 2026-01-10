package InterviewPrep.streams;

import java.util.List;

public class AvgAgeInPersonsObjList {
    public static void main(String[] args) {
        List<Person> people = List.of(
                new Person("Alice", 30, "New York"),
                new Person("Bob", 25, "Los Angeles"),
                new Person("Charlie", 35, "Chicago")
        );
//        Calculate the average age of a list of Person objects using Java streams:
         double avgAge = people.stream()
                .mapToInt(Person::getAge)
                .average()
                .orElse(0);
         System.out.println("Average age: " + avgAge);
    }
}
