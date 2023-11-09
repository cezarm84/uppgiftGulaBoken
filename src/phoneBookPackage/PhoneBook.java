package phoneBookPackage;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

public class PhoneBook {
    private static User user;
    private List<Profile> profiles;

    public PhoneBook() {
        this.profiles = new ArrayList<>();
    }

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.loadDataFromFile("phonebook_data.dat");// load data from file
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("PhoneBook Menu:");
            System.out.println("Please make a choice by numbers .");
            System.out.println("1. Choose the access level (Admin / Guest)");
            System.out.println("2. Quit");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {

                System.out.println("Invalid input. Please enter a valid numeric choice.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Choose User Type (Admin / Guest):");
                    String userType = scanner.next();

                    if (userType.equalsIgnoreCase("admin")) {
                        System.out.println("Enter Admin Username:");
                        String adminUsername = scanner.next();
                        System.out.println("Enter Admin Password:");
                        String adminPassword = scanner.next();

                        if (isAdmin(adminUsername, adminPassword)) {
                            user = new AdminUser(adminUsername, adminPassword, phoneBook);
                            user.menu();
                        } else {
                            System.out.println("You are not an admin.");
                        }
                    } else if (userType.equalsIgnoreCase("guest")) {
                        user = new GuestUser(phoneBook);
                        user.menu();
                    } else {
                        System.out.println("Invalid user type.");
                    }
                    break;
                case 2:
                    System.out.println("Thank you for using the app!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    public List<Profile> getProfiles() {
        return profiles;
    }
    public void saveDataToFile(String fileName) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeObject(profiles);
        } catch (IOException e) {
            System.err.println("Error" + e.getMessage());
        }
    }

    public void loadDataFromFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                profiles = (List<Profile>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading data from file: " + e.getMessage());
            }
        } else {
            System.err.println("File not found: " + fileName);
        }
    }

    private static boolean isAdmin(String Username, String Password) {
        //  admin username och password
        String adminUsername = "admin";
        String adminPassword = "123456";

        // kolla om rätt värde
        return adminUsername.equals(Username) && adminPassword.equals(Password);
    }

    public void createProfile(Profile profile) {
        profiles.add(profile);
    }

    public void deleteProfile(Profile profile) {
        profiles.remove(profile);
    }

    public List<Profile> searchProfiles(String text) {
        // Initialize listan att spara the sök resultat
        List<Profile> searchResults = new ArrayList<>();
        // konvertera text to småbokstäv and dela ord
        String[] searchWords = text.toLowerCase().split("\\s+");
       // rotera genom all profiler in profiles
        for (Profile profile : profiles) {
            // kolla om resultat matchar texten med profile
            if (profileMatchesWords(profile, searchWords)) {
                // om det match, adda profilen till resultaten
                searchResults.add(profile);
            }
        }
        return searchResults;
    }

    private boolean profileMatchesWords(Profile profile, String[] searchWords) {
        // kolla om texten i profilen har ett samma ord i sök fält
        // i detta fallet namnet and address
        String profileText = (profile.getFullName() + " " + profile.getAddress()).toLowerCase();

        for (String word : searchWords) {
            if (!profileText.contains(word)) {
                return false;
            }
        }
        return true;
    }

}
