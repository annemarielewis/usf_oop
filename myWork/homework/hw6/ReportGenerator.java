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

        ArrayList<Payment> allTransactions = analyzer.getAllTransactions();

        if (allTransactions.isEmpty()) {
            System.out.println("\nNo transactions to report.");
            return;
        }

        // Overview
        System.out.println("\nOverview:");
        System.out.println("  Total Transactions: " + allTransactions.size());

        double totalAmount = 0.0;
        for (Payment p : allTransactions) {
            totalAmount += p.getAmount();
        }
        System.out.println("  Total Amount Spent: $" + String.format("%.2f", totalAmount));

        // Sections
        displayPaymentSystemBreakdown(allTransactions);
        displayContactTypeBreakdown(allTransactions);
        displayTopRecipients(5);

        System.out.println("\n" + "=".repeat(70));
    }

    /**
     * Displays breakdown by payment system.
     */
    private void displayPaymentSystemBreakdown(ArrayList<Payment> transactions) {
        System.out.println("\nBy Payment System:");

        HashMap<String, Integer> systemCount = new HashMap<>();
        HashMap<String, Double> systemTotals = new HashMap<>();

        for (Payment p : transactions) {
            String system = p.getPaymentSystem();
            systemCount.put(system, systemCount.getOrDefault(system, 0) + 1);
            systemTotals.put(system, systemTotals.getOrDefault(system, 0.0) + p.getAmount());
        }

        for (String system : systemCount.keySet()) {
            System.out.printf(
                    "  %s: %d transactions | $%.2f%n",
                    system,
                    systemCount.get(system),
                    systemTotals.get(system)
            );
        }
    }

    /**
     * Displays breakdown by contact type (Friend vs Family).
     */
    private void displayContactTypeBreakdown(ArrayList<Payment> transactions) {
        System.out.println("\nBy Contact Type:");

        HashMap<String, Integer> typeCount = new HashMap<>();
        HashMap<String, Double> typeTotal = new HashMap<>();

        double grandTotal = 0.0;

        for (Payment p : transactions) {
            Person contact = findContactByName(p.getRecipientName());
            String type = (contact == null) ? "Unknown" : contact.getContactType();

            typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
            typeTotal.put(type, typeTotal.getOrDefault(type, 0.0) + p.getAmount());
            grandTotal += p.getAmount();
        }

        for (String type : typeCount.keySet()) {
            int count = typeCount.get(type);
            double total = typeTotal.get(type);
            double percent = (grandTotal == 0.0) ? 0.0 : (total / grandTotal) * 100.0;

            System.out.printf(
                    "  %s: %d transactions | $%.2f (%.1f%%)%n",
                    type, count, total, percent
            );
        }
    }

    /**
     * Displays top N recipients by total amount received.
     */
    private void displayTopRecipients(int n) {
        System.out.println("\nTop " + n + " Recipients:");

        HashMap<String, Integer> txCount = new HashMap<>();
        HashMap<String, Double> totals = new HashMap<>();

        for (Payment p : analyzer.getAllTransactions()) {
            String name = p.getRecipientName();
            txCount.put(name, txCount.getOrDefault(name, 0) + 1);
            totals.put(name, totals.getOrDefault(name, 0.0) + p.getAmount());
        }

        ArrayList<String> recipients = new ArrayList<>(totals.keySet());
        recipients.sort((a, b) -> Double.compare(totals.get(b), totals.get(a)));

        for (int i = 0; i < Math.min(n, recipients.size()); i++) {
            String name = recipients.get(i);
            Person contact = findContactByName(name);
            String type = (contact == null) ? "Unknown" : contact.getContactType();

            System.out.printf(
                    "  %d. %s (%s) - $%.2f (%d transactions)%n",
                    i + 1,
                    name,
                    type,
                    totals.get(name),
                    txCount.get(name)
            );
        }
    }

    // =================================================================
    // HELPER METHODS
    // =================================================================

    /**
     * Finds a contact by full name.
     */
    private Person findContactByName(String name) {
        for (Person contact : contacts) {
            if (contact.getFullName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }
}
