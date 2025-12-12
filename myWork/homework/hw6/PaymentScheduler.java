import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.io.*;


/**
 * PaymentScheduler manages recurring payments using a priority queue.
 * Handles scheduling, processing, and rescheduling of automatic payments.
 *
 * This class demonstrates the use of PriorityQueue for efficient scheduling
 * and file I/O for persistence of scheduled payments.
 *
 * @author [Your Name]
 * @version 1.0
 */
public class PaymentScheduler {
//instance variables
    private PriorityQueue<ScheduledPayment> upcomingPayments;
    // Keep a reference to contacts so we can look up Person by name when loading.
    private ArrayList<Person> contacts;
    //static variables:
    private static final String SCHEDULE_FILE = "scheduled_payments.txt";
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Constructs a new PaymentScheduler and loads existing scheduled payments.
     * @throws IOException if file reading fails
     */
    //constructor:
    public PaymentScheduler(ArrayList<Person> contacts) {
        // TODO: Initialize the PriorityQueue
        // HINT: The PriorityQueue will automatically sort ScheduledPayment objects
        // HINT: by their compareTo method (which compares by nextPaymentDate)
        this.contacts = contacts;

        //upcoming payments:
        this.upcomingPayments = new PriorityQueue<>();

        // TODO: Load any existing scheduled payments from file
        //this load method being in constructor: automatically loads previously saved recurring payments the moment a new PaymentScheduler object is created.
        //->program automatically resumes scheduled payments from last run
        try {
            //helper method:
            loadScheduledPayments();
        } catch (IOException e) {
            System.out.println("Error loading scheduled payments: " + e.getMessage());
        }
    }

    /**
     * Loads scheduled payments from the schedule file.
     * Format: ContactName,Amount,PaymentSystem,NextPaymentDate,RecurrenceType
     * Example: Alice Smith,50.00,Venmo,2025-12-01T09:00:00,MONTHLY
     *
     * @throws IOException if file cannot be read
     */

    // Running the program multiple times with loadScheduledPayments() does NOT produce duplicates in scheduled_payments.txt because:
//You load once at startup
//You overwrite (not append) when saving
//Loading does not automatically save
//The queue only contains what’s in the file unless the user adds more


    private void loadScheduledPayments() throws IOException {
        File file = new File(SCHEDULE_FILE);

        // If file doesn't exist, just return (no scheduled payments yet)
        if (!file.exists()) {
            System.out.println("No existing scheduled payments found.");
            return;
        }
        // Read the file and parse each line
        // HINT: Use BufferedReader to read line by line
        // HINT: Split each line by comma to get the fields
        // HINT: Parse the date using LocalDateTime.parse(dateString, DATE_FORMATTER)
        // HINT: Parse the recurrence using RecurrenceType.valueOf(recurrenceString)
        // HINT: You'll need to find the Person object by name (may need contacts list)

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            //above libe does this:
            //parts[0] → "Alice Smith"
            //parts[1] → "50.00"
            //parts[2] → "Venmo"
            //parts[3] → "2025-12-01T09:00:00"
            //parts[4] → "MONTHLY"
            if (parts.length != 5) {
                System.out.println("Skipping malformed scheduled payment line: " + line);
                continue;
            }

            String contactName = parts[0];

            double amount;
            try {
                amount = Double.parseDouble(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount in schedule: " + line);
                continue;
            }

            String system = parts[2];

            LocalDateTime nextPaymentDate;
            try {
                nextPaymentDate = LocalDateTime.parse(parts[3], DATE_FORMATTER);
            } catch (Exception e) {
                System.out.println("Invalid date in schedule: " + line);
                continue;
            }

            RecurrenceType recurrence;
            try {
                recurrence = RecurrenceType.valueOf(parts[4]);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid recurrence in schedule: " + line);
                continue;
            }

            // Find the Person object in your "contacts" list by name
            Person recipient = null;

            if (contacts != null) {
                for (Person p : contacts) {
                    if (p.getFullName().equals(contactName)) {
                        recipient = p;
                        break;
                    }
                }
            }
            if (recipient == null) {
                System.out.println("Warning: could not find contact for scheduled payment: " + contactName);
                continue;
            }

            // Create ScheduledPayment object and add to queue
            ScheduledPayment sp = new ScheduledPayment(recipient, amount, system, nextPaymentDate, recurrence);
            //add to upcoming payments priority queque
            upcomingPayments.add(sp);
        }

        reader.close();
        System.out.println("Loaded " + upcomingPayments.size() + " scheduled payments.");
    }

    //so far ^ , our code:
    //Stores all upcoming recurring payments in a PriorityQueue.
    //Knows about your contacts so it can attach scheduled payments to real Person objects.
    //Loads scheduled payments from a file when it’s constructed, so the system “remembers” them.
    //Parses each line of that file carefully with error handling, skipping anything malformed.
    //Rebuilds ScheduledPayment objects and puts them back into the queue.

    /**
     * Schedules a new recurring payment.
     *
     * @param recipient the person to pay
     * @param amount the payment amount
     * @param paymentSystem the payment system to use (Venmo, PayPal, AppleCash)
     * @param recurrence how often to repeat (WEEKLY, BIWEEKLY, MONTHLY, YEARLY)
     * @param startDate when to start the recurring payments
     */
    public void schedulePayment(Person recipient, double amount, String paymentSystem,
                                RecurrenceType recurrence, LocalDateTime startDate) {
        // TODO: Create a new ScheduledPayment object with the provided parameters
        ScheduledPayment sp = new ScheduledPayment(recipient, amount, paymentSystem, startDate, recurrence);

        // TODO: Add it to the upcomingPayments PriorityQueue
        // NOTE: PriorityQueue will automatically keep payments sorted by date
        upcomingPayments.add(sp);

        // TODO: Save all scheduled payments to file
        try {
            saveScheduledPayments();
        } catch (IOException e) {
            System.out.println("Error saving scheduled payments: " + e.getMessage());
        }

        System.out.println(" Payment scheduled successfully!");
        System.out.println("  Next payment: " + startDate.format(DATE_FORMATTER));
    }
    /**
     * Saves all scheduled payments to the file.
     * Overwrites the existing file with current queue contents.
     *
     * @throws IOException if file cannot be written
     */
    private void saveScheduledPayments() throws IOException {
        // TODO: Open the file for writing
        // HINT: Use PrintWriter with FileWriter
        PrintWriter writer = new PrintWriter(new FileWriter(SCHEDULE_FILE));

        // TODO: iterate through upcomingPayments queue and write each scheduled payment to the file
        // TODO: Write line in format: name,amount,system,date,recurrence
        // Example: writer.println(sp.getRecipient().getFullName() + "," + ...)
        for (ScheduledPayment sp : upcomingPayments) {
            writer.println(
                    sp.getRecipient().getFullName() + "," +
                            sp.getAmount() + "," +
                            sp.getPaymentSystem() + "," +
                            sp.getNextPaymentDate().format(DATE_FORMATTER) + "," +
                            sp.getRecurrence().name()
            );
        }

        // TODO: Close the writer
        writer.close();
    }

    /**
     * Returns all scheduled payments as a sorted list.
     *
     * @return ArrayList of all scheduled payments, sorted by next payment date
     */
    public ArrayList<ScheduledPayment> getAllScheduledPayments() {
        // TODO: Convert the PriorityQueue to an ArrayList
        // HINT: Create new ArrayList and pass the queue to its constructor
        // HINT: return new ArrayList<>(upcomingPayments);
        // NOTE: This creates a copy, so original queue is not modified

        return new ArrayList<>(upcomingPayments);
    }

    /**
     * Returns payments that are due today or earlier.
     * A payment is "due" if its nextPaymentDate is today or in the past.
     *
     * @return ArrayList of payments due for processing
     */
    public ArrayList<ScheduledPayment> getDuePayments() {
        ArrayList<ScheduledPayment> duePayments = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (ScheduledPayment sp : upcomingPayments) {
            LocalDateTime next = sp.getNextPaymentDate();
            if (!next.isAfter(now)) { // next <= now
                duePayments.add(sp);
            }
        }
        return duePayments;
    }
    public ScheduledPayment getNextPayment() {
        return upcomingPayments.peek();
    }

    /**
     * Calculates the next payment date based on recurrence type.
     *
     * @param currentDate the current payment date
     * @param recurrence the recurrence type
     * @return the next payment date
     */
    private LocalDateTime calculateNextPaymentDate(LocalDateTime currentDate,
                                                   RecurrenceType recurrence) {
        switch (recurrence) {
            case WEEKLY:
                return currentDate.plusWeeks(1);
            case BIWEEKLY:
                return currentDate.plusWeeks(2);
            case MONTHLY:
                return currentDate.plusMonths(1);
            case YEARLY:
                return currentDate.plusYears(1);
            default:
                    return currentDate.plusDays(1);
        }
    }




    /**
     * Checks if the scheduler has any scheduled payments.
     *
     * @return true if there are scheduled payments, false otherwise
     */
    public boolean isEmpty() {
        // TODO: Check if the queue is empty
        return upcomingPayments.isEmpty();
    }

    /**
     * Displays all scheduled payments in a formatted view.
     * Shows payment details and calculates days until due.
     */
    /**
     * Displays all scheduled payments in a formatted view.
     * Shows payment details and calculates days until due.
     */
    public void displaySchedule() {
        //TODO: display the schedule
        System.out.println("\n=== Scheduled Payments ===\n");

        if (upcomingPayments.isEmpty()) {
            System.out.println("No scheduled payments.\n");
            return;
        }

        // Get sorted list of payments
        LocalDateTime now = LocalDateTime.now();
        ArrayList<ScheduledPayment> list = new ArrayList<>(upcomingPayments);
        Collections.sort(list); // uses compareTo (nextPaymentDate)

        System.out.println("Total scheduled payments:");
        System.out.println();

        int count = 1;
        for (ScheduledPayment sp : list) {
            String name = sp.getRecipient().getFullName();
            double amount = sp.getAmount();
            String system = sp.getPaymentSystem();
            LocalDateTime date = sp.getNextPaymentDate();
            RecurrenceType recurrence = sp.getRecurrence();

            System.out.println(count + ". " + name);
            System.out.println("   Amount: $" + String.format("%.2f", amount));
            System.out.println("   System: " + system);
            // print just the date part, like 2025-11-20
            System.out.println("   Next payment: " + date.toLocalDate());
            System.out.println("   Recurrence: " + recurrence.toString().substring(0,1)
                    + recurrence.toString().substring(1).toLowerCase());

            // (Optional extra info: days until due – not required by screenshot)
            long daysUntil = ChronoUnit.DAYS.between(now.toLocalDate(), date.toLocalDate());
            if (daysUntil == 0) {
                System.out.println("   Status: DUE TODAY");
            } else if (daysUntil < 0) {
                System.out.println("   Status: OVERDUE (" + Math.abs(daysUntil) + " day(s))");
            } else {
                System.out.println("   Status: Due in " + daysUntil + " day(s)");
            }

            System.out.println();
            count++;
        }
    }
    public void processDuePayments() throws PaymentFailedException {
        // 1. Get all payments that are due now or earlier
        ArrayList<ScheduledPayment> duePayments = getDuePayments();

        if (duePayments.isEmpty()) {
            System.out.println("\nNo scheduled payments are due at this time.\n");
            return;
        }

        System.out.println("\n=== Processing Next-Due Scheduled Payments ===\n");

        for (ScheduledPayment sp : duePayments) {

            Person recipient = sp.getRecipient();
            double amount = sp.getAmount();

            // Pick the correct payment system based on stored string
            PaymentSystem paymentSystem;
            switch (sp.getPaymentSystem().toLowerCase()) {
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
                    System.out.println("Unknown payment system for "
                            + recipient.getFullName() + ". Using default Venmo.");
                    paymentSystem = new VenmoPayment();
            }

            double fee = paymentSystem.getTransactionFee(amount);
            double totalAmount = amount + fee;

            // Confirm to screen
            System.out.printf("Processing payment to %s%n", recipient.getFullName());
            System.out.printf("   Amount: $%.2f (Fee: $%.2f, Total: $%.2f)%n",
                    amount, fee, totalAmount);
            System.out.println("   System: " + paymentSystem.getSystemName());
            System.out.println();

            // 2. Actually perform the payment
            paymentSystem.pay(recipient, amount);  // may throw PaymentFailedException

            // 3. Record this processed payment in payments.txt
            Payment payment = new Payment(
                    recipient.getFullName(),
                    amount,
                    paymentSystem.getSystemName(),
                    LocalDateTime.now(),
                    fee
            );

            try (PrintWriter out =
                         new PrintWriter(new BufferedWriter(new FileWriter("payments.txt", true)))) {

                String line = payment.getRecipientName() + "," +
                        payment.getAmount() + "," +
                        payment.getPaymentSystem() + "," +
                        payment.getTimestamp() + "," +
                        payment.getTransactionFee();
                out.println(line);

            } catch (IOException e) {
                throw new PaymentFailedException(
                        "Failed to save processed scheduled payment: " + e.getMessage());
            }

            // 4. RESCHEDULE the next occurrence ✨
            LocalDateTime currentDate = sp.getNextPaymentDate();
            RecurrenceType recurrence = sp.getRecurrence();
            LocalDateTime nextDate = calculateNextPaymentDate(currentDate, recurrence);

            // Remove the old scheduled payment from the queue
            upcomingPayments.remove(sp);

            // Add a new ScheduledPayment with the updated next date
            ScheduledPayment nextPayment = new ScheduledPayment(
                    recipient,
                    amount,
                    sp.getPaymentSystem(),
                    nextDate,
                    recurrence
            );
            upcomingPayments.add(nextPayment);
        }

        // 5. Save the updated schedule (with new nextPaymentDate values)
        try {
            saveScheduledPayments();
        } catch (IOException e) {
            System.out.println("Error saving updated scheduled payments: " + e.getMessage());
        }

        System.out.println("\nAll due scheduled payments processed.\n");
    }


}
