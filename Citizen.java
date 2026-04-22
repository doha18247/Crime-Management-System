import java.io.Serial;
import java.io.Serializable;

public abstract class Citizen implements Serializable {

    @Serial
    private static final long serialVersionUID = -231079148946252998L;
    private String SSN;
    private String Name;
    private int Age;
    private Gender gender;
    private String PhoneNumber;
    private String Address;

    public enum Gender {
        MALE, FEMALE
    }

    public Citizen() {
    }

    public Citizen(String SSN, String Name, int Age, Gender gender, String PhoneNumber, String Address) {
        setSSN(SSN);
        setName(Name);
        setAge(Age);
        setGender(gender);
        setPhoneNumber(PhoneNumber);
        setAddress(Address);
    }

    public void setSSN(String SSN) {

        if (String.valueOf(SSN).matches("\\d{14}")) {
            this.SSN = SSN;
        } else {
            throw new IllegalArgumentException("SSN must be exactly 14 digits.");
        }
    }

    public void setName(String name) {

        if (name != null && !name.trim().isEmpty()) {
            this.Name = name;
        } else {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
    }

    public void setAge(int age) {

        if (age > 0) {
            this.Age = age;
        } else {
            throw new IllegalArgumentException("Age must be positive.");
        }
    }

    public void setGender(Gender gender) {
        // Gender cannot be null
        if (gender != null) {
            this.gender = gender;
        } else {
            throw new IllegalArgumentException("Gender cannot be null.");
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        if (String.valueOf(phoneNumber).matches("\\d{11}")) {
            this.PhoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Phone number must be exactly 11 digits.");
        }
    }

    public void setAddress(String address) {
        if (address != null && !address.trim().isEmpty()) {
            this.Address = address;
        } else {
            throw new IllegalArgumentException("Address cannot be null or empty.");
        }
    }

    public String getSSN() {
        return SSN;
    }

    public String getName() {
        return Name;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return Age;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getAddress() {
        return Address;
    }
}
