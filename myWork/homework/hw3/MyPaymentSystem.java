import java.util.*;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;


/*
   CS 514: MyPaymentSystem, HW 3
   Name: Annemarie Lewis
   Our electronic payment system that takes in a user's contacts
   and uses a payment processor to simulate payments.
 */

public class MyPaymentSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //scanning whatever user inputs into console
        System.out.println("Welcome to the CS 514 Payment System");

        // let's assign the choice to some invalid default
        int choice = -1;

        while (choice != 0) {
            // this prints out the required menu
            printMenu();

            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // get the next input (newline)

                processChoice(choice, scanner);
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // get the next (invalid)  input
            }
            System.out.println();
        }

        System.out.println("Exiting Payment System. Goodbye!");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("1. Pay Contact");
        System.out.println("2. Print all Payments");
        System.out.println("3. Update Preferred Payment System");
        System.out.println("4. Print Contact Information");
        System.out.println("5. Print All Contacts"); //added this functionality/option for this in case someone forgets who is in/not-in their contact list --AM
        System.out.println("0. Exit");
    }

    private static void processChoice(int choice, Scanner scanner) {
        //search for person who has that number using method in ContactManager
        //what does contracts parameter represent here: empty value as of right now??????
        ArrayList<Person> contacts = new ArrayList<>();

        //accessing contacts
        try {
            contacts = ContactManager.loadContacts("contacts.txt");
        } catch (IOException e) {
            System.err.println("Error loading contacts: " + e.getMessage());
            return;
        }

        switch (choice) {

            case 1:
                System.out.println("Pay Contact selected.");
                System.out.print("Enter the 10-digit phone number of the contact: ");
                String phoneNumber = scanner.nextLine();

                Person contact = ContactManager.searchByPhoneNumber(contacts, phoneNumber);

                if (contact == null) {
                    System.out.println("No contact found with that number. Select option 5 in prior step for info on who is in your contact list and their numbers! :)");
                    break; // Go back to main menu
                }

                System.out.println("Contact found:");
                System.out.println("Name: " + contact.getFullName());
                System.out.println("Phone: " + contact.getPhoneNumber());
                System.out.println("Preferred Payment: " + contact.getPreferredPaymentSystem());

                System.out.print("Enter payment amount: ");
                double amount = 0;
                if (scanner.hasNextDouble()) {
                    amount = scanner.nextDouble();
                    scanner.nextLine(); // consume newline
                } else {
                    System.out.println("Invalid amount. Returning to menu.");
                    scanner.nextLine(); // consume invalid input
                    break;
                }

                System.out.println("You are about to pay $" + amount + " to " + contact.getFullName() +
                        " via " + contact.getPreferredPaymentSystem() + ".");
                System.out.print("Confirm payment? (yes/no): ");
                String confirm = scanner.nextLine();

                if (!confirm.equalsIgnoreCase("yes")) {
                    System.out.println("Payment canceled. Returning to main menu.");
                    break;
                }

                System.out.println("Processing payment...");
                System.out.println("Payment of $" + amount + " to " + contact.getFullName() +
                        " completed successfully via " + contact.getPreferredPaymentSystem() + "!");

            //add writing payment info to file code here (*used chat-gpt heavily for this step)
            try (FileWriter writer = new FileWriter("payments.txt", true);
                 BufferedWriter bw = new BufferedWriter(writer);
                 PrintWriter out = new PrintWriter(bw)) {

                // Format: PhoneNumber,FullName,Amount,PaymentSystem,DateTime
                String paymentLine = contact.getPhoneNumber() + "," +
                        contact.getFullName() + "," +
                        amount + "," +
                        contact.getPreferredPaymentSystem() + "," +
                        java.time.LocalDateTime.now();

                out.println(paymentLine);

                System.out.println("Payment saved to payments.txt successfully!");

            } catch (IOException e) {
                System.err.println("Error saving payment: " + e.getMessage());
            }
                break;

            case 2:
                System.out.println("Print all Payments selected.");
                File paymentsFile = new File("payments.txt");
                if (!paymentsFile.exists()) {
                    System.out.println("No payments found yet.");
                    break;
                }

                try (Scanner fileScanner = new Scanner(paymentsFile)) {
                    System.out.println("\n-All Payments-");
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        // Each line format: PhoneNumber,FullName,Amount,PaymentSystem,DateTime
                        String[] parts = line.split(",");

                        if (parts.length >= 5) {
                            String phone = parts[0];
                            String name = parts[1];
                            String payAmount = parts[2];
                            String system = parts[3];
                            String timestamp = parts[4];

                            System.out.println("Recipient: " + name + " 📞 " + phone);
                            System.out.println("Amount: $" + payAmount);
                            System.out.println("Payment Method: " + system);
                            System.out.println("Timestamp: " + timestamp);
                            System.out.println("-----------");
                        } else {
                            System.out.println("Malformed payment entry: " + line);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error reading payments file: " + e.getMessage());
                }
                break;

            case 3:
                System.out.println("Update Preferred Payment System: ");

                // search based on #
                System.out.print("Enter the 10-digit number of the contact: ");
                phoneNumber = scanner.nextLine();

                // Search for the contact
                contact = ContactManager.searchByPhoneNumber(contacts, phoneNumber);
                if (contact == null) {
                    System.out.println("No contact found with that number. Select 5 on prior step if you need a reminder of all your current contacts and their numbers! :)");
                    break;
                }

                // Current payment system
                System.out.println("Current preferred payment system: " + contact.getPreferredPaymentSystem());
                //this uses the getter in the Person class! since contact is a person object-->inheritance!

                // Prompt for new payment system
                System.out.print("Enter new preferred payment system: ");
                String newPaymentSystem = scanner.nextLine();
                contact.setPreferredPaymentSystem(newPaymentSystem); // Updates in memory

                // Rewriting the contacts.txt file with the updated list
                try (PrintWriter writer = new PrintWriter(new FileWriter("contacts.txt"))) {
                    for (Person p : contacts) {
                        writer.println(p.getContactType() + "," +
                                p.getFullName() + "," +
                                p.getPhoneNumber() + "," +
                                p.getPreferredPaymentSystem());
                    }
                    System.out.println("Preferred payment system updated successfully!");
                } catch (IOException e) {
                    System.err.println("Error updating contacts file: " + e.getMessage());
                }
                // new current payment system
                System.out.println("Updated preferred payment system: " + contact.getPreferredPaymentSystem());

                break; // Return to main menu

            case 4:
                System.out.println("Print Contact Information selected.");
                System.out.print("Enter the full name of the contact (First Last): ");
                String fullNameToSearch = scanner.nextLine().trim();

                Person contactToPrint = ContactManager.searchByFullName(contacts, fullNameToSearch);

                if (contactToPrint == null) {
                    System.out.println("No contact found with that name.");
                    break;
                }

                System.out.println("\n--- Contact Details ---");
                System.out.println("Full Name: " + contactToPrint.getFullName());
                System.out.println("Contact Type: " + contactToPrint.getContactType());
                System.out.println("Phone Number: " + contactToPrint.getPhoneNumber());
                System.out.println("Preferred Payment System: " + contactToPrint.getPreferredPaymentSystem());

                // Polymorphism: type-specific message
                System.out.println("Relationship Message: " + contactToPrint.getRelationshipMessage());
                System.out.println("---------------------------");
                break;


            case 5: //added case 5+functionality for people who want to see who is currently in/not in their contact list
                System.out.println("Print all contacts from my contact list:");

                if (contacts.isEmpty()) {
                    System.out.println("No contacts found!");
                } else {
                    System.out.println("\n--- Contact List ---");
                    for (Person person : contacts) {
                        System.out.println(person.getFullName() + " (" + person.getContactType() + ")");
                        System.out.println("📞 Phone: " + person.getPhoneNumber());
                        System.out.println("💳 Preferred Payment: " + person.getPreferredPaymentSystem());
                        System.out.println("💬 " + person.getRelationshipMessage());
                        System.out.println("----------------------------");
                    }
                }
                break;


            case 0:
                System.out.println("Exit selected.");
                break;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
                break;
        }
    }
}

