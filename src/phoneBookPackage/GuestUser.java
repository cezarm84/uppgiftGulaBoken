package phoneBookPackage;

import java.util.List;
import java.util.Scanner;

public class GuestUser implements User {
        private PhoneBook phoneBook;
        private Scanner scanner;

        public GuestUser(PhoneBook phoneBook) {
                this.phoneBook = phoneBook;
                this.scanner = new Scanner(System.in);
        }

        @Override
        public void menu() {
                while (true) {
                        System.out.println("Guest User Menu:");
                        System.out.println("1. Search Profiles.");
                        System.out.println("2. Back to main menu.");
                        System.out.println("3. Quit");

                        int choice;
                        try{
                                choice=Integer.parseInt(scanner.next());}
                        catch (NumberFormatException e){
                            System.out.println("wrong input");
                            continue;
                        }

                        switch (choice) {
                                case 1:
                                        System.out.println("Write here to search for profiles:");
                                        scanner.nextLine();
                                        String text = scanner.nextLine();
                                        List<Profile> searchResults = phoneBook.searchProfiles(text);
                                        printSearchResults(searchResults, scanner);
                                        break;
                                case 2:
                                        return;
                                case 3:
                                        System.out.println("Thank you for using the app!");
                                        System.exit(0);
                                        break;
                                default:
                                        System.out.println("Invalid choice. Please select a valid option.");
                        }
                }
        }


        @Override
        public void printSearchResults(List<Profile> searchResults, Scanner scanner) {
                if (searchResults.isEmpty()) {
                        System.out.println("No matching profiles found.");
                } else if (searchResults.size() == 1) {
                        Profile profile = searchResults.get(0);
                        showProfileDetails(profile, scanner);
                } else {
                        System.out.println("Matching profiles:");
                        for (int i = 0; i < searchResults.size(); i++) {
                                Profile profile = searchResults.get(i);
                                System.out.println((i + 1) + "- Name: " + profile.getFullName());
                        }

                        System.out.print("Enter the profile number to see more details or write 'back': ");
                        String choice = scanner.next();

                        if (choice.equalsIgnoreCase("back")) {
                                // Go back to the Guest Menu
                                return;
                        }

                        try {
                                int selectedProfileIndex = Integer.parseInt(choice);
                                if (selectedProfileIndex >= 1 && selectedProfileIndex <= searchResults.size()) {
                                        Profile selectedProfile = searchResults.get(selectedProfileIndex - 1);
                                        showProfileDetails(selectedProfile, scanner);
                                } else {
                                        System.out.println("No profile details displayed.");
                                }
                        } catch (NumberFormatException e) {
                                System.out.println("No profile details displayed.");
                        }
                }

        }

        private void showProfileDetails(Profile profile, Scanner scanner) {
                System.out.println("Details for profile:");
                System.out.println("full Name: " + profile.getFullName() + ".");
                System.out.println("Age: " + profile.getAge() + ".");
                System.out.println("Address: " + profile.getAddress());
                List<PhoneNumber> phoneNumbers = profile.getPhoneNumbers();

                if (!phoneNumbers.isEmpty()) {
                        System.out.println("Phone Numbers:");
                        for (PhoneNumber phoneNumber : phoneNumbers) {
                                System.out.println("Type: " + phoneNumber.getType() + ", Number: " + phoneNumber.getNumber() + " .");
                        }
                } else {
                        System.out.println("No phone numbers available.");
                }
        }



}
