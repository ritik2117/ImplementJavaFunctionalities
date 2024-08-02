package overrideMethods;

import java.util.Objects;

/**
 * Overriding equals and hashCode methods
 * Define the Class
 * Override equals Method: This method should check
 * if two Person objects are considered equal based on their id, name, and age.
 * Override hashCode Method: This method should return a hash code
 * that is consistent with the equals method.
 */

public class Person {
    private int id;
    private String name;
    private int age;

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object obj) {
//        If the object is compared with itself then return true
        if (this == obj)
            return true;

//        Check if obj is an instance of Person or not
//        Ensures that the object is not null and of the same class.
        if (obj == null || this.getClass() != obj.getClass())
            return false;

//        Typecast obj to Person so that we can compare data members
        Person person = (Person) obj;
        /**
         * The fields of the Person object are compared using the == operator for primitive types
         * and Objects.equals for objects.
         */

//        Compare the data members and return accordingly
//        return id == person.getId() && age == person.getAge() && name.equals(person.getName()); // need top handle name == null
//        This internally handles null check and use (a == b) || (a != null && a.equals(b))
        return id == person.id && age == person.age && Objects.equals(name, person.name);
    }

    /**
     * returns a hash code value for the object,
     * which is generated using Objects.hash(id, name, age).
     * This ensures that the hash code is consistent with the equals method.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

    @Override
    public String toString() {
        return "Person {" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", age = " + age +
                '}';
    }
}
