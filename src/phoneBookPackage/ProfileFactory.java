package phoneBookPackage;

public class ProfileFactory {
    public static Profile createProfile(String firstName, String lastName, int age, Address address, PhoneNumber phoneNumber) {
        Profile profile = new Profile(firstName, lastName, age, address);
        profile.addPhoneNumber(phoneNumber);
        return profile;
    }
}
