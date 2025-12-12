import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Generates comprehensive reports about payment activity.
 * Creates formatted summaries, exports to files, and provides insights.
 *
 * @author [Your Name]
 * @version 1.0
 */
public class ReportGenerator {

    private TransactionAnalyzer analyzer;
    private ArrayList<Person> contacts;
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructs a new ReportGenerator.
     *
     * @param analyzer the TransactionAnalyzer with loaded data
     * @param contacts list of all contacts
     */
    public ReportGenerator(TransactionAnalyzer analyzer, ArrayList<Person> contacts) {
        this.analyzer = analyzer;
        this.contacts = contacts;
    }

    // =================================================================
    // SUMMARY REPORT
    // =================================================================

    /**
     * Generates and displays a comprehensive summary report of all payment activity.
     * Includes overview, payment system breakdown, contact type breakdown, and top recipients.
     */
    public void generateSummaryReport() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("=== Payment System Summary Report ===");
        System.out.println("Generated: " + LocalDateTime.now().format(DATE_FORMATTER));
        System.out.println("=".repeat(70));

        // Get all transactions
        ArrayList<Payment> allTransactions = analyzer.getAllTransactions();

        if (allTransactions.isEmpty()) {
            System.out.println("\nNo transactions to report.");
            return;
        }

        // Overview Section
        System.out.println("\nOverview:");
        System.out.println("  Total Transactions: " + allTransactions.size());

        // TODO: Calculate total amount spent
        // HINT: Loop through all transactions and sum the amounts
        double totalAmount = 0.0;
        for (Payment p : allTransactions) {
            // TODO: Add payment amount to totalAmount
        }
        System.out.println("  Total Amount Spent: $" + String.format("%.2f", totalAmount));

        // By Payment System Section
        displayPaymentSystemBreakdown(allTransactions);

        // By Contact Type Section
        displayContactTypeBreakdown(allTransactions);

        // Top Recipients Section
        displayTopRecipients(5);

        System.out.println("\n" + "=".repeat(70));
    }

    /**
     * Displays breakdown by payment system.
     *
     * @param transactions list of all transactions
     */
    private void displayPaymentSystemBreakdown(ArrayList<Payment> transactions) {
        System.out.println("\nBy Payment System:");

        // TODO: Get payment system statistics
        // HINT: Use analyzer.getPaymentSystemUsageStats()
        HashMap<String, Integer> systemStats = null; // Replace with actual call

        // TODO: Create maps to store totals and fees per system
        HashMap<String, Double> systemTotals = new HashMap<>();

        // TODO: Calculate totals for each system
        // HINT: Iterate through transactions
        // HINT: Group by payment system
        for (Payment p : transactions) {
            String system = p.getPaymentSystem();
            double amount = p.getAmount();
            // TODO: Add to systemTotals
        }
        // TODO: Display each payment system's statistics
        // HINT: Iterate through systemStats.keySet()
        // Format: "  Venmo:     22 transactions | $850.00 "
    }

    /**
     * Displays breakdown by contact type (Friend vs Family).
     *
     * @param transactions list of all transactions
     */
    private void displayContactTypeBreakdown(ArrayList<Payment> transactions) {
        System.out.println("\nBy Contact Type:");

        // TODO: Create maps to count transactions and total amounts by contact type
        HashMap<String, Integer> typeCount = new HashMap<>();
        HashMap<String, Double> typeTotal = new HashMap<>();

        // TODO: Iterate through transactions
        for (Payment p : transactions) {
            // TODO: Find the contact by recipient name
            // HINT: Create helper method findContactByName()

            // TODO: Get contact type (Friend or Family)

            // TODO: Update count and total for this type
        }

        // TODO: Calculate grand total for percentage calculation
        double grandTotal = 0.0;


        // TODO: Display each contact type
        // Format: "  Family: 28 transactions | $1,650.00 (67.3%)"
        for (String type : typeCount.keySet()) {
            int count = typeCount.get(type);
            double total = typeTotal.get(type);
            // TODO: Calculate percentage
            // double percent = (total / grandTotal) * 100;

            // TODO: Print formatted line
        }
    }

    /**
     * Displays top N recipients by total amount.
     *
     * @param n number of top recipients to show
     */
    private void displayTopRecipients(int n) {
        System.out.println("\nTop " + n + " Recipients:");

        // TODO: Get top spenders from analyzer
        // HINT: Use analyzer.getTopSpenders(n)
        ArrayList<Person> topSpenders = null; // Replace with actual call

        // TODO: Display each recipient with their stats
        int rank = 1;
        for (Person person : topSpenders) {
            // TODO: Get stats for this contact
            // HINT: Use analyzer.getContactStats(person)

            // TODO: Display formatted line
            // Format: "  1. Alice Smith (Family)    - $650.00 (12 transactions)"

            rank++;
        }
    }
    
    // =================================================================
    // HELPER METHODS
    // =================================================================

    /**
     * Helper method to find a contact by name.
     *
     * @param name the full name to search for
     * @return the Person object if found, null otherwise
     */
    private Person findContactByName(String name) {
        // TODO: Search through contacts list
        for (Person contact : contacts) {
            if (contact.getFullName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }
}
