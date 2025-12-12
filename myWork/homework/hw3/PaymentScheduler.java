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

    private PriorityQueue<ScheduledPayment> upcomingPayments;
    private static final String SCHEDULE_FILE = "scheduled_payments.txt";
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // We'll keep a reference to contacts so we can look up Person by name when loading.
    private ArrayList<Person> contacts;

    /**
     * Constructs a new PaymentScheduler and loads existing scheduled payments.
     *
     * @throws IOException if file reading fails
     */
    public PaymentScheduler(ArrayList<Person> contacts) {
        // TODO: Initialize the PriorityQueue
        // HINT: The PriorityQueue will automatically sort ScheduledPayment objects
        // HINT: by their compareTo method (which compares by nextPaymentDate)
        this.contacts = contacts;
        this.upcomingPayments = new PriorityQueue<>(); // Replace with: new PriorityQueue<>();

        // TODO: Load any existing scheduled payments from file
        try {
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
    private void loadScheduledPayments() throws IOException {
        File file = new File(SCHEDULE_FILE);

        // If file doesn't exist, just return (no scheduled payments yet)
        if (!file.exists()) {
            System.out.println("No existing scheduled payments found.");
            return;
        }

        // TODO: Read the file and parse each line
        // HINT: Use BufferedReader to read line by line
        // HINT: Split each line by comma to get the fields
        // HINT: Parse the date using LocalDateTime.parse(dateString, DATE_FORMATTER)
        // HINT: Parse the recurrence using RecurrenceType.valueOf(recurrenceString)
        // HINT: You'll need to find the Person object by name (may need contacts list)

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = reader.readLine()) != null) {
            // TODO: Split line by comma
            String[] parts = line.split(",");
            if (parts.length != 5) {
                System.out.println("Skipping malformed scheduled payment line: " + line);
                continue;
            }

            // TODO: Extract fields (contact, amt, system, date, recurrence)
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

            // TODO: Find the Person object (you may need to pass contacts list to constructor)
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

            // TODO: Create ScheduledPayment and add to queue
            ScheduledPayment sp = new ScheduledPayment(recipient, amount, system, recurrence, nextPaymentDate);
            upcomingPayments.add(sp);
        }
        reader.close();
        System.out.println("Loaded " + upcomingPayments.size() + " scheduled payment(s).");
    }

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
        ScheduledPayment sp = new ScheduledPayment(recipient, amount, paymentSystem, recurrence, startDate);

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
                            sp.getRecurrenceType().name()
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

        return new ArrayList<>(upcomingPayments); // Replace with actual ArrayList
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

        // TODO: Iterate through the queue (upcomingPayments) and find payments where nextPaymentDate <= now
        // HINT: Use a for-each loop to check each payment
        // HINT: Use nextPaymentDate.isBefore(now) or isEqual(now) to check if due
        // HINT: Don't remove from queue yet - just collect in the duePayments list
        for (ScheduledPayment sp : upcomingPayments) {
            LocalDateTime next = sp.getNextPaymentDate();
            if (!next.isAfter(now)) { // next <= now
                duePayments.add(sp);
            }
        }

        return duePayments;
    }

    /**
     * Returns the next upcoming payment (earliest due date).
     *
     * @return the next ScheduledPayment, or null if queue is empty
     */
    public ScheduledPayment getNextPayment() {
        // TODO: Return the payment at the head of the queue without removing it
        // HINT: peek() returns null if queue is empty, so this is safe

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
        // TODO: Calculate next date based on recurrence type
        // HINT: Use currentDate.plus...() methods
        // HINT: WEEKLY = currentDate.plusWeeks(1)
        // HINT: BIWEEKLY = currentDate.plusWeeks(2)
        // HINT: MONTHLY = currentDate.plusMonths(1)
        // HINT: YEARLY = currentDate.plusYears(1)

        switch (recurrence) {
            case WEEKLY:
                // TODO: Add 1 week
                return currentDate.plusWeeks(1);
            case BIWEEKLY:
                // TODO: Add 2 weeks
                return currentDate.plusWeeks(2);
            case MONTHLY:
                // TODO: Add 1 month
                return currentDate.plusMonths(1);
            case YEARLY:
                // TODO: Add 1 year
                return currentDate.plusYears(1);
            default:
                return currentDate; // Should never happen
        }
    }



    /**
     * Checks if the scheduler has any scheduled payments.
     *
     * @return true if there are scheduled payments, false otherwise
     */
    public boolean isEmpty() {
        // TODO: Check if the queue is empty

        return upcomingPayments.isEmpty(); // Replace with actual check
    }

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

        System.out.println("Total: " + upcomingPayments.size() + " payment(s)");
        System.out.println();

        // Get sorted list of payments
        // HINT: you can use LocalDateTime now = LocalDateTime.now();
        LocalDateTime now = LocalDateTime.now();
        ArrayList<ScheduledPayment> list = new ArrayList<>(upcomingPayments);
        Collections.sort(list); // just in case

        int count = 1;
        for (ScheduledPayment sp : list) {
            String name = sp.getRecipient().getFullName();
            double amount = sp.getAmount();
            String system = sp.getPaymentSystem();
            LocalDateTime date = sp.getNextPaymentDate();
            RecurrenceType recurrence = sp.getRecurrenceType();

            //TODO: As you iterate through the payments, use the folloing formatting
            // TODO: Display each payment with formatting, replace with the correct varibles
            System.out.println(count + ". " + name);
            System.out.println("   Amount: $" + String.format("%.2f", amount));
            System.out.println("   System: " + system);
            System.out.println("   Next: " + date.format(DATE_FORMATTER));
            System.out.println("   Recurrence: " + recurrence);

            // TODO: Calculate days until payment
            // HINT: Use ChronoUnit.DAYS.between(now.toLocalDate(), sp.getNextPaymentDate().toLocalDate())
            long daysUntil = ChronoUnit.DAYS.between(now.toLocalDate(), date.toLocalDate()); // Replace with actual calculation

            // TODO: Display status based on days until payment
            // HINT: If daysUntil == 0, print "DUE TODAY"
            // HINT: If daysUntil < 0, print "OVERDUE (X days)"
            // HINT: If daysUntil > 0, print "Due in X day(s)"
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
}
