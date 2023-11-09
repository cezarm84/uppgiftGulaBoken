package phoneBookPackage;

import java.util.List;
import java.util.Scanner;

public class AdminUser implements User {
    private String username;
    private String password;
    private PhoneBook phoneBook;

    public AdminUser(String username, String password, PhoneBook phoneBook) {
        this.username = username;
        this.password = password;
        this.phoneBook = phoneBook;
    }

    @Override
    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Admin menu:");
            System.out.println("1- Create profile");
            System.out.println("2- Update profile information");
            System.out.println("3- Delete profile");
            System.out.println("4- View profiles in database");
            System.out.println("5- Back to main menu");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createProfile(scanner);
                    this.phoneBook.saveDataToFile("phonebook_data.dat");
                    break;
                case 2:
                    System.out.println("Enter the first name of the profile to update:");
                    String firstNameToUpdate = scanner.next();
                    List<Profile> searchResultsForUpdate = this.phoneBook.searchProfiles(firstNameToUpdate);
                    if (!searchResultsForUpdate.isEmpty()) {
                        Profile profileToUpdate = searchResultsForUpdate.get(0);
                        updateProfile(profileToUpdate, scanner);
                    } else {
                        System.out.println("Profile not found.");
                    }
                    break;
                case 3:
                    // ta bort en profil
                    System.out.println("Enter the first name of the profile to delete:");
                    String firstNameToDelete = scanner.next();
                    List<Profile> toDelete = this.phoneBook.searchProfiles(firstNameToDelete);
                       // ifall fler profil hittades med frisökning ett val att ta bort
                    if (!toDelete.isEmpty()) {
                        System.out.println("Select a profile number to delete or type 'back' to go back:");
                        for (int i = 0; i < toDelete.size(); i++) {
                            System.out.println(i + 1 + ". " + toDelete.get(i).getFullName());
                        }

                        String input = scanner.next();

                        if (input.equalsIgnoreCase("back")) {
                            break;
                        }

                        try {
                            int selectedProfileIndex = Integer.parseInt(input);

                            if (selectedProfileIndex >= 1 && selectedProfileIndex <= toDelete.size()) {
                                Profile profileToDelete = toDelete.get(selectedProfileIndex - 1);
                                this.phoneBook.deleteProfile(profileToDelete);
                                System.out.println("Profile deleted successfully.");
                                this.phoneBook.saveDataToFile("phonebook_data.dat");
                            } else {
                                System.out.println("Invalid selection. Profile not deleted.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Profile not deleted.");
                        }
                    } else {
                        System.out.println("Profile not found.");
                    }
                    break;
                case 4:
                    viewProfiles(this.phoneBook);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    @Override
    public void printSearchResults(List<Profile> searchResults, Scanner scanner) {
        // visa sök results i console
        if (searchResults.isEmpty()) {
            System.out.println("No matching profiles found.");
        } else {
            System.out.println("Matching profiles:");
            for (Profile profile : searchResults) {
                System.out.println("Full Name: " + profile.getFullName());
                System.out.print("Do you want to see details for this profile? (yes/no): ");
                String choice = scanner.next();
                if (choice.equalsIgnoreCase("yes")) {
                    System.out.println("Age: " + profile.getAge());
                    System.out.println("Address: " + profile.getAddress());
                    List<PhoneNumber> phoneNumbers = profile.getPhoneNumbers();
                    if (!phoneNumbers.isEmpty()) {
                        System.out.println("Phone Numbers:");
                        for (PhoneNumber phoneNumber : phoneNumbers) {
                            System.out.println("  Type: " + phoneNumber.getType() + ", Number: " + phoneNumber.getNumber());
                        }
                    } else {
                        System.out.println("No phone numbers available.");
                    }
                }
            }
        }
    }
    private void createProfile(Scanner scanner) {
        // skapa profil namn
        System.out.println("Enter first name:");
        String firstName = scanner.next();
        System.out.println("Enter last name:");
        String lastName = scanner.next();
        System.out.println("Enter age:");
        int age = scanner.nextInt();
        //skapa adress data
        scanner.nextLine();
        System.out.println("Enter address details:");
        System.out.println("Street: ");
        String street = scanner.nextLine();
        System.out.println("City: ");
        String city = scanner.nextLine();
        System.out.println("Postal Code: ");
        String postCode = scanner.nextLine();
        System.out.println("Apartment Number: ");
        String apartmentNumber = scanner.nextLine();
        Address address = new Address(street, city, postCode, apartmentNumber);
        // skapa phone data
        System.out.println("Enter phone number details:");
        System.out.println("Number: ");
        String number = scanner.next();
        System.out.println("Type: home or mobile ");
        String type = scanner.next();
        PhoneNumber phoneNumber = new PhoneNumber(number, type);

        Profile profile = ProfileFactory.createProfile(firstName, lastName, age, address, phoneNumber);
        this.phoneBook.createProfile(profile);
        System.out.println("Profile created successfully and saved to database.");
    }

    private void updateProfile(Profile profileToUpdate, Scanner scanner) {
        System.out.println("Enter new first name (or press Enter to keep the current first name):");
        // hoppa over och ignorera resten av raden efter att ha läst in intger
        scanner.nextLine();
        String newFirstName = scanner.nextLine();
        if (!newFirstName.isEmpty()) {
            profileToUpdate.setFirstName(newFirstName);
        }

        System.out.println("Enter new last name (or press Enter to keep the current last name):");
        String newLastName = scanner.nextLine();
        if (!newLastName.isEmpty()) {
            profileToUpdate.setLastName(newLastName);
        }

        System.out.println("Enter new age (or press Enter to keep the current age):");
        String newAgeInput = scanner.nextLine();
        if (!newAgeInput.isEmpty()) {
            int newAge = Integer.parseInt(newAgeInput);
            profileToUpdate.setAge(newAge);
        }

        Address currentAddress = profileToUpdate.getAddress();
        System.out.println("Enter new street (or press Enter to keep the current street):");
        String newStreet = scanner.nextLine();
        if (!newStreet.isEmpty()) {
            currentAddress.setStreet(newStreet);
        }

        System.out.println("Enter new city (or press Enter to keep the current city):");
        String newCity = scanner.nextLine();
        if (!newCity.isEmpty()) {
            currentAddress.setCity(newCity);
        }

        System.out.println("Enter new post code (or press Enter to keep the current post code):");
        String newPostCode = scanner.nextLine();
        if (!newPostCode.isEmpty()) {
            currentAddress.setPostCode(newPostCode);
        }

        System.out.println("Enter new apartment number (or press Enter to keep the current apartment number):");
        String newApartmentNumber = scanner.nextLine();
        if (!newApartmentNumber.isEmpty()) {
            currentAddress.setApartmentNumber(newApartmentNumber);
        }

        // Uppdatera phone nummer
        List<PhoneNumber> phoneNumbers = profileToUpdate.getPhoneNumbers();
        if (!phoneNumbers.isEmpty()) {
            for (PhoneNumber phoneNumber : phoneNumbers) {
                System.out.println("Enter new phone number for type '" + phoneNumber.getType() + "' (or press Enter to keep the current number):");
                String newNumber = scanner.nextLine();
                if (!newNumber.isEmpty()) {
                    phoneNumber.setNumber(newNumber);
                }

                System.out.println("Enter new type for the phone number '" + phoneNumber.getNumber() + "' (or press Enter to keep the current type):");
                String newType = scanner.nextLine();
                if (!newType.isEmpty()) {
                    phoneNumber.setType(newType);
                }
            }
        }

        System.out.println("Profile updated successfully.");
    }

  // privat metod att visa profiler som sparat i listan och hur många
    private void viewProfiles(PhoneBook phoneBook) {
        List<Profile> profiles = phoneBook.getProfiles();
        for (Profile profile : profiles) {
            System.out.println("Full Name: " + profile.getFullName());
        }
        System.out.println("Number of profiles in database : " + profiles.size());
    }

}
