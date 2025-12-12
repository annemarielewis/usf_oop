import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

/**
 * TransactionAnalyzer provides comprehensive analysis of payment transactions
 * including sorting, filtering, and statistical analysis.
 *
 * This class demonstrates the use of sorting algorithms and
 * custom data analysis for transaction data.
 *
 * @author [Your Name]
 * @version 1.0
 */
public class TransactionAnalyzer {

    private ArrayList<Payment> transactions;
    private ArrayList<Person> contacts;
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Constructs a new TransactionAnalyzer and loads data from files.
     *
     * @throws IOException if file reading fails
     */
    public TransactionAnalyzer() throws IOException {
        this.transactions = new ArrayList<>();
        this.contacts = new ArrayList<>();
        loadTransactions();
        loadContacts();
    }

    /**
     * Loads all transactions from the payments.txt file.
     * Format: FirstName LastName,Amount,PaymentSystem,Timestamp
     *
     * @throws IOException if file cannot be read
     */
    private void loadTransactions() throws IOException {
        File file = new File("payments.txt");
        if (!file.exists()) {
            System.out.println("Warning: payments.txt not found.");
            return;
        }

        // TODO: Implement file reading
        // HINT: Use BufferedReader and String.split(",")
        // HINT: Parse timestamp using LocalDateTime.parse(timestamp, DATE_FORMATTER)
        // HINT: Create Payment objects and add to transactions list

    }

    /**
     * Loads all contacts from the contacts.txt file.
     * Format: ContactType,FirstName LastName,PhoneNumber,PreferredPaymentSystem
     *
     * @throws IOException if file cannot be read
     */
    private void loadContacts() throws IOException {
        File file = new File("contacts.txt");
        if (!file.exists()) {
            System.out.println("Warning: contacts.txt not found.");
            return;
        }

        // TODO: Implement file reading
        // HINT: Create Friend or Family objects based on ContactType

    }

    /**
     * Returns statistics on payment system usage.
     *
     * @return HashMap mapping payment system names to frequency count
     */
    public HashMap<String, Integer> getPaymentSystemUsageStats() {
        HashMap<String, Integer> stats = new HashMap<>();

        // TODO: Count occurrences of each payment system
        // HINT: Iterate through transactions
        // HINT: Use getOrDefault() to handle first occurrence

        return stats;
    }

    /**
     * Identifies the top N contacts by total amount spent on them.
     *
     * @param n number of top spenders to return
     * @return ArrayList of Person objects sorted by total amount (descending)
     */
    public ArrayList<Person> getTopSpenders(int n) {
        // TODO: Calculate total per contact and return top n
        // HINT: Use sortContactsByTotalSpent() and take first n elements

        return new ArrayList<>();
    }

   /**
     * Returns all transactions (for use by other classes).
     *
     * @return the complete list of transactions
     */
    public ArrayList<Payment> getAllTransactions() {
        return transactions;
    }

    /**
     * Returns all contacts (for use by other classes).
     *
     * @return the complete list of contacts
     */
    public ArrayList<Person> getAllContacts() {
        return contacts;
    }

    // =================================================================
    // INNER CLASS: ContactStats
    // =================================================================

    /**
     * Stores statistical information about a contact's transaction history.
     */
    public static class ContactStats {
        private String name;
        private String contactType;
        private int transactionCount;
        private double totalAmount;
        private String mostUsedPaymentSystem;
        private double averageAmount;

        /**
         * Constructs a ContactStats object.
         *
         * @param name the contact's full name
         * @param contactType "Friend" or "Family"
         * @param transactionCount number of transactions
         * @param totalAmount total amount paid to this contact
         * @param mostUsedPaymentSystem the payment system used most often
         */
        public ContactStats(String name, String contactType, int transactionCount,
                            double totalAmount, String mostUsedPaymentSystem) {
            this.name = name;
            this.contactType = contactType;
            this.transactionCount = transactionCount;
            this.totalAmount = totalAmount;
            this.mostUsedPaymentSystem = mostUsedPaymentSystem;
            this.averageAmount = transactionCount > 0 ? totalAmount / transactionCount : 0.0;
        }

        // Getters
        public String getName() { return name; }
        public String getContactType() { return contactType; }
        public int getTransactionCount() { return transactionCount; }
        public double getTotalAmount() { return totalAmount; }
        public String getMostUsedPaymentSystem() { return mostUsedPaymentSystem; }
        public double getAverageAmount() { return averageAmount; }

        @Override
        public String toString() {
            return String.format(
                    "Contact: %s (%s)%n" +
                            "  Transactions: %d%n" +
                            "  Total Spent: $%.2f%n" +
                            "  Average: $%.2f%n" +
                            "  Preferred System: %s",
                    name, contactType, transactionCount, totalAmount,
                    averageAmount, mostUsedPaymentSystem
            );
        }
    }
}

