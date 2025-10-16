import java.io.*;
import java.util.*;

public class ContactManager {

    /**
     * Loads contacts from contacts.txt into an ArrayList<Person>
     * File format: ContactType,FirstName LastName,PhoneNumber,PreferredPaymentSystem
     * Example: Friend,John Doe,1234567890,Venmo
     *
     * @param filename the path to the contacts file
     * @return ArrayList of Person objects (Friend and Family)
     * @throws IOException if file cannot be read
     */
    public static ArrayList<Person> loadContacts(String filename) throws IOException {
        ArrayList<Person> contacts = new ArrayList<>();

        // We will be using the BufferedReader class to read the File handler
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Split by comma
                String[] parts = line.split(",");

                // Validate we have all required fields
                if (parts.length < 4) {
                    System.err.println("Invalid line format: " + line);
                    continue;
                }

                // Trim the extra spaces around the different parts
                String contactType = parts[0].trim();
                String fullName = parts[1].trim();
                String phoneNumber = parts[2].trim();
                String preferredPaymentSystem = parts[3].trim();

                // Split the full name into first and last name
                //Assuming we will always have first name AND last name
                String[] nameParts = fullName.split(" ", 2);
                String firstName = nameParts[0];
                String lastName = nameParts[1];

                // Create the appropriate type of Person based on contactType
                Person person;
                if (contactType.equalsIgnoreCase("Friend")) {
                    person = new Friend(firstName, lastName, phoneNumber, preferredPaymentSystem);
                } else if (contactType.equalsIgnoreCase("Family")) {
                    person = new Family(firstName, lastName, phoneNumber, preferredPaymentSystem);
                } else {
                    System.err.println("Unknown contact type: " + contactType);
                    continue;
                }

                contacts.add(person);
            }
        }
        return contacts;
    }

    /**
     * Searches for a contact by their 10-digit telephone number
     *
     * @param contacts the list of contacts to search
     * @param phoneNumber the 10-digit phone number to search for
     * @return the Person object if found, null otherwise
     */
    public static Person searchByPhoneNumber(ArrayList<Person> contacts, String phoneNumber) {
        // Assuming we only have digits in our contacts in our contacts file
        // Validate it's exactly 10 digits
        if (phoneNumber.length() != 10) {
            System.out.println("Phone number must be exactly 10 digits.");
            return null;
        }
        // Search through contacts
        for (Person person : contacts) {
            if (person.getPhoneNumber().equals(phoneNumber)) {
                return person;
            }
        }
        // Not found - how will you handle this case?
        return null;
    }

    /**
     * Sample main that tests the loading of the contacts and search
     * by number through the ArrayList of Person/Contacts
     */
    public static void main(String[] args) {
        try {
            // Load contacts from file
            ArrayList<Person> contacts = loadContacts("contacts.txt");

            System.out.println("Loaded " + contacts.size() + " contacts.");
            System.out.println();

            // Example 1: Search by phone number
            String phoneToSearch = "1234567890";
            Person foundByPhone = searchByPhoneNumber(contacts, phoneToSearch);

            if (foundByPhone != null) {
                System.out.println("Found by phone: " + foundByPhone.getFullName());
                System.out.println("Contact Type: " + foundByPhone.getContactType());
                System.out.println("Preferred Payment: " + foundByPhone.getPreferredPaymentSystem());
            } else {
                System.out.println("No contact found with phone: " + phoneToSearch);
            }
            System.out.println();
            
            // Example 2: Display all contacts (demonstrates polymorphism)
            System.out.println("All Contacts:");
            for (Person person : contacts) {
                // This demonstrates polymorphism - calling methods on Person reference
                // but executing Friend or Family specific implementations
                System.out.println(person.getFullName() + " (" + person.getContactType() + ") - "
                        + person.getRelationshipMessage());
            }
        } catch (IOException e) {
            System.err.println("Error reading contacts file: " + e.getMessage());
        }
    }
}

