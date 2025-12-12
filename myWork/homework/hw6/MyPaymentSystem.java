import java.util.*;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;



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
            printMenu();
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                try {
                    processChoice(choice, scanner);
                } catch (PaymentFailedException e) {
                    System.out.println("Operation failed: " + e.getMessage());
                }

            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // consume invalid input
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
        System.out.println("5. Schedule Recurring Payment");
        System.out.println("6. View Scheduled Payments");
        System.out.println("7. Process Due Payments");
        System.out.println("8. Transaction Analysis Menu");
        System.out.println("9. Generate Summary Report");
        System.out.println("10. Print All Contacts");
        System.out.println("0. Exit");
    }

    private static void processChoice(int choice, Scanner scanner) throws PaymentFailedException {
        //search for person who has that number using method in ContactManager
        //what does contracts parameter represent here: empty value as of right now??????
        ArrayList<Person> contacts = new ArrayList<>();

        //accessing contacts
        try {
            contacts = ContactManager.loadContacts("contacts.txt");
        } catch (IOException e) {
            throw new PaymentFailedException("Failed to load contacts: " + e.getMessage());
        }

        // Create scheduler using current contacts (for recurring payments)
        PaymentScheduler scheduler = new PaymentScheduler(contacts);
// SHARED VARIABLES (so we don't redeclare them in cases)
        String phoneNumber;
        Person contact;
        double amount;
        String confirm;

        switch (choice) {

            case 1:
                System.out.println("Pay Contact selected.");
                System.out.print("Enter the 10-digit phone number of the contact: ");
                phoneNumber = scanner.nextLine();

                contact = ContactManager.searchByPhoneNumber(contacts, phoneNumber);

                if (contact == null) {
                    System.out.println("No contact found with that number. Select option 5 to see all contacts.");
                    break;
                }

                System.out.println("Contact found:");
                System.out.println("Name: " + contact.getFullName());
                System.out.println("Phone: " + contact.getPhoneNumber());
                System.out.println("Preferred Payment: " + contact.getPreferredPaymentSystem());

                System.out.print("Enter payment amount: ");
                //amount;
                if (scanner.hasNextDouble()) {
                    amount = scanner.nextDouble();
                    scanner.nextLine(); // consume newline
                    if (amount <= 0) {
                        System.out.println("Payment must be a positive amount. Returning to menu.");
                        break;
                    }
                } else {
                    //?
                    System.out.println("Invalid amount. Returning to menu.");
                    scanner.nextLine();
                    break;
                }

                // Create PaymentSystem object based on contact's preferred system : b/c of interface, we know all methods have getTransactionfee ;0

                PaymentSystem paymentSystem;
                switch (contact.getPreferredPaymentSystem().toLowerCase()) {
                    case "venmo":
                        paymentSystem = new VenmoPayment();
                        break;
                    case "paypal":
                        paymentSystem = new PayPalPayment();
                        break;
                    case "applecash":
                        paymentSystem = new AppleCashPayment();
                        break;
                    default:
                        System.out.println("Unknown payment system. Using default Venmo.");
                        paymentSystem = new VenmoPayment();
                }

                double fee = paymentSystem.getTransactionFee(amount);
                double totalAmount = amount + fee;

                System.out.println("You are about to pay $" + amount +
                        " to " + contact.getFullName() +
                        " via " + paymentSystem.getSystemName() +
                        (fee > 0 ? " (Transaction Fee: $" + fee + ", Total: $" + totalAmount + ")" : "") + ".");
                // Display deatiled breakdown BEFORE confirmation
                System.out.println("\n--- Transaction Summary : please double-check details and confirm :)");
                System.out.printf("Recipient: %s%n", contact.getFullName());
                System.out.printf("Base Amount: $%.2f%n", amount);
                System.out.printf("Transaction Fee (%s): $%.2f%n", paymentSystem.getSystemName(), fee);
                System.out.printf("Total Charged: $%.2f%n", totalAmount);
                System.out.println("----------------------------");
                System.out.print("Confirm payment? (yes/no): ");
                confirm = scanner.nextLine();

                if (!confirm.equalsIgnoreCase("yes")) {
                    System.out.println("Payment canceled. Returning to main menu.");
                    break;
                }

                try {
                    paymentSystem.pay(contact, amount); // throwing PaymentFailedException
                } catch (PaymentFailedException e) {
                    System.out.println("Payment failed: " + e.getMessage());
                    break;
                }

                // Create Payment object to save to file
                Payment payment = new Payment(contact.getFullName(), amount, paymentSystem.getSystemName(),
                        java.time.LocalDateTime.now(), fee);

                // Save to payments.txt : note, used chatgpt pretty heavily here!
                try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("payments.txt", true)))) {
                    // Format: Recipient,Amount,PaymentSystem,Timestamp,Fee
                    String line = payment.getRecipientName() + "," + payment.getAmount() + "," +
                            payment.getPaymentSystem() + "," + payment.getTimestamp() + "," +
                            payment.getTransactionFee();
                    out.println(line);
                    System.out.println("Payment saved to payments.txt successfully!");
                } catch (IOException e) {
                    throw new PaymentFailedException("Failed to save payment: " + e.getMessage());
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

                        String[] parts = line.split(",");

                        if (parts.length >= 5) {
                            String phone = parts[0];
                            String name = parts[1];
                            String payAmount = parts[2];
                            String system = parts[3];
                            String timestamp = parts[4];

                            System.out.println("Recipient: " + phone);
                            System.out.println("Amount: $" + name);
                            System.out.println("Payment Method: " + payAmount);
                            System.out.println("Timestamp: " + system);
                            System.out.println("Transaction Fee: $" + timestamp);
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

                break;

            case 4:
                System.out.println("Print Contact Information selected.");
                System.out.print("Enter the full name of the contact (First Last): ");
                String fullNameToSearch = scanner.nextLine().trim();

                Person contactToPrint = ContactManager.searchByFullName(contacts, fullNameToSearch);

                if (contactToPrint == null) {
                    System.out.println("No contact found with that name.");
                    break;
                }

                System.out.println("\n--- Contact Details! ---");
                System.out.println("Full Name: " + contactToPrint.getFullName());
                System.out.println("Contact Type: " + contactToPrint.getContactType());
                System.out.println("Phone Number: " + contactToPrint.getPhoneNumber());
                System.out.println("Preferred Payment System: " + contactToPrint.getPreferredPaymentSystem());

                // Polymorphism: type-specific message
                System.out.println("Relationship Message: " + contactToPrint.getRelationshipMessage());
                System.out.println("---------------------------");
                break;

            //ADD CASES 5-9 HERE


            // CASE 5: Schedule Recurring Payment
            // CASE 5: Schedule Recurring Payment
            case 5:
                System.out.println("Schedule Recurring Payment selected.");

                // 1. Pick the contact by phone number
                System.out.print("Enter the 10-digit phone number of the contact: ");
                phoneNumber = scanner.nextLine().trim();

                contact = ContactManager.searchByPhoneNumber(contacts, phoneNumber);

                if (contact == null) {
                    System.out.println("No contact found with that number. Select option 10 to see all contacts.");
                    break;
                }

                System.out.println("Contact found:");
                System.out.println("Name: " + contact.getFullName());
                System.out.println("Phone: " + contact.getPhoneNumber());
                System.out.println("Preferred Payment: " + contact.getPreferredPaymentSystem());

                // 2. Prompt for the recurring payment amount
                System.out.print("Enter recurring payment amount: ");
                if (scanner.hasNextDouble()) {
                    amount = scanner.nextDouble();
                    scanner.nextLine(); // consume newline
                    if (amount <= 0) {
                        System.out.println("Payment must be a positive amount. Returning to menu.");
                        break;
                    }
                } else {
                    System.out.println("Invalid amount. Returning to menu.");
                    scanner.nextLine(); // clear invalid
                    break;
                }

                // 3. Recurrence menu
                System.out.println("\nSelect recurrence frequency:");
                System.out.println("1. Weekly");
                System.out.println("2. Biweekly");
                System.out.println("3. Monthly");
                System.out.println("4. Yearly");
                System.out.print("Enter your choice: ");

                RecurrenceType recurrenceType;
                int freqChoice;

                if (scanner.hasNextInt()) {
                    freqChoice = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                } else {
                    System.out.println("Invalid input. Returning to menu.");
                    scanner.nextLine();
                    break;
                }

                switch (freqChoice) {
                    case 1:
                        recurrenceType = RecurrenceType.WEEKLY;
                        break;
                    case 2:
                        recurrenceType = RecurrenceType.BIWEEKLY;
                        break;
                    case 3:
                        recurrenceType = RecurrenceType.MONTHLY;
                        break;
                    case 4:
                        recurrenceType = RecurrenceType.YEARLY;
                        break;
                    default:
                        System.out.println("Invalid recurrence selection. Defaulting to MONTHLY.");
                        recurrenceType = RecurrenceType.MONTHLY;
                        break;
                }

                // 4. Prompt for start date (yyyy-mm-dd)
                LocalDate startDateOnly = null;
                while (startDateOnly == null) {
                    System.out.print("Enter start date for recurring payment (yyyy-mm-dd): ");
                    String dateStr = scanner.nextLine().trim();
                    try {
                        startDateOnly = LocalDate.parse(dateStr);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please try again.");
                    }
                }

                // pick a default time (9:00 AM) so we store a LocalDateTime
                LocalDateTime startDateTime = startDateOnly.atTime(9, 0);

                // Payment system for scheduling should be the contact's preferred system
                String systemName = contact.getPreferredPaymentSystem();

                // 5. Confirmation screen
                System.out.println("\n--- Recurring Payment Summary ---");
                System.out.println("Recipient: " + contact.getFullName());
                System.out.printf("Amount: $%.2f%n", amount);
                System.out.println("Payment System: " + systemName);
                System.out.println("Recurrence: " + recurrenceType);
                System.out.println("Start Date: " + startDateTime.toLocalDate());
                System.out.println("---------------------------------");
                System.out.print("Confirm scheduling this recurring payment? (y/yes or no): ");

                confirm = scanner.nextLine().trim().toLowerCase();

                // Accept both "y" and "yes"
                if (!(confirm.equals("y") || confirm.equals("yes"))) {
                    System.out.println("Recurring payment NOT scheduled. Returning to main menu.");
                    break;
                }

                // 6. Actually schedule it (adds to PQ + writes to scheduled_payments.txt)
                scheduler.schedulePayment(contact, amount, systemName, recurrenceType, startDateTime);

                // 7. Return to main menu
                break;


            //CASE 6:
            case 6:
                System.out.println("View Scheduled Payments selected.");

                if (scheduler.isEmpty()) {
                    System.out.println("No scheduled payments.");
                } else {
                    scheduler.displaySchedule();
                }
                break;

            //CASE 7:
            // CASE 7: Process Due Payments
            case 7:
                System.out.println("Process Due Payments selected.");

                // 1. Display a list of payments due
                ArrayList<ScheduledPayment> duePayments = scheduler.getDuePayments();

                if (duePayments.isEmpty()) {
                    System.out.println("\nNo scheduled payments are due at this time.\n");
                    break;
                }

                System.out.println("\n=== Due Scheduled Payments ===\n");
                int idx = 1;
                for (ScheduledPayment sp : duePayments) {
                    System.out.println(idx + ". " + sp.getRecipient().getFullName());
                    System.out.printf("   Amount: $%.2f%n", sp.getAmount());
                    System.out.println("   System: " + sp.getPaymentSystem());
                    System.out.println("   Next payment: " + sp.getNextPaymentDate().toLocalDate());
                    System.out.println("   Recurrence: " + sp.getRecurrence());
                    System.out.println();
                    idx++;
                }

                // Optional confirmation before processing everything
                System.out.print("Process all due payments? (y/n): ");
                String answer = scanner.nextLine().trim();
                if (!answer.equalsIgnoreCase("y")) {
                    System.out.println("Canceled processing scheduled payments. Returning to main menu.");
                    break;
                }

                // 2 & 3. Process all due payments + confirm/write to payments.txt
                try {
                    scheduler.processDuePayments();
                } catch (PaymentFailedException e) {
                    System.out.println("Error while processing scheduled payments: " + e.getMessage());
                }

                // 4. Return to main menu (break)
                break;

            //CASE 8:
            case 8:
                boolean back = false;

                while (!back) {
                    System.out.println("=== Transaction Analysis ===");
                    System.out.println("1. View Top Receivers");
                    System.out.println("2. View Payment System Statistics");
                    System.out.println("0. Back to Main Menu");
                    System.out.print("Choose an option: ");

                    int analysisChoice;
                    if (scanner.hasNextInt()) {
                        analysisChoice = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                    } else {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.nextLine();
                        continue;
                    }

                    try {
                        TransactionAnalyzer analyzer = new TransactionAnalyzer();

                        switch (analysisChoice) {
                            case 1:
                                System.out.println("=== Top Receivers ===");
                                analyzer.printTopReceivers(3);
                                break;

                            case 2:
                                analyzer.printPaymentSystemStatisticsScreen();
                                break;

                            case 0:
                                back = true;
                                break;

                            default:
                                System.out.println("Invalid option. Try again.");
                        }
                    } catch (IOException e) {
                        System.out.println("Error loading transaction data: " + e.getMessage());
                    }

                    System.out.println();
                }
                break;

            //CASE 9:

            case 9:
                try {
                    TransactionAnalyzer analyzer = new TransactionAnalyzer();
                    ReportGenerator report =
                            new ReportGenerator(analyzer, contacts);
                    report.generateSummaryReport();
                } catch (IOException e) {
                    System.out.println("Error generating summary report.");
                }
                break;


            case 10: //added case 5, functionality for people who want to see who is currently in/not in their contact list
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