import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Handles calculation and display of payment system usage statistics.
 * Analyzes transaction data to provide insights about payment system pres,
 * costs, and usage patterns.
 *
 * @author [Your Name]
 * @version 1.0
 */
public class PaymentSystemStatistics {

    private ArrayList<Payment> transactions;
    private HashMap<String, Integer> usageCounts;
    private HashMap<String, Double> totalAmounts;

    /**
     * Constructs a new PaymentSystemStatistics analyzer.
     *
     * @param transactions list of all payment transactions to analyze
     */
    public PaymentSystemStatistics(ArrayList<Payment> transactions) {
        this.transactions = transactions;
        this.usageCounts = new HashMap<>();
        this.totalAmounts = new HashMap<>();

        // TODO: Call calculateStatistics() to populate the maps
    }

    /**
     * Calculates all statistics for each payment system.
     * Populates usageCounts and totalAmounts maps.
     */
    private void calculateStatistics() {
        // TODO: Iterate through all transactions
        // HINT: For each transaction, get the payment system name
        // HINT: Update usageCounts - use getOrDefault(system, 0) + 1
        // HINT: Update totalAmounts - accumulate the payment amounts

        for (Payment payment : transactions) {
            // TODO: get the payment system
            // TODO: Update usage count
            // TODO: Update total amount
            // HINT: Similar to usage count but add the amount instead of 1
        }
    }


    /**
     * Gets the usage count for a specific payment system.
     *
     * @param system the payment system name
     * @return number of transactions using this system
     */
    public int getUsageCount(String system) {
        // TODO: Return the count from usageCounts map
        // HINT: Use getOrDefault(system, 0) to handle missing systems
        return 0;
    }

    /**
     * Gets the total amount transacted through a specific payment system.
     *
     * @param system the payment system name
     * @return total amount in dollars
     */
    public double getTotalAmount(String system) {
        // TODO: Return the total from totalAmounts map
        return 0.0;
    }

    /**
     * Calculates the average transaction amount for a payment system.
     *
     * @param system the payment system name
     * @return average amount per transaction
     */
    public double getAverageAmount(String system) {
        // TODO: Calculate average
        // HINT: Check for division by zero (count could be 0)
        return 0.0;
    }

    /**
     * Calculates the market share percentage for a payment system.
     *
     * @param system the payment system name
     * @return percentage of total transactions (0-100), assuming we see at most 100 transactions
     */
    public double getMarketShare(String system) {
        // TODO: Calculate and return percentage
        // HINT: (systemCount / totalTransactions) * 100
        return 0.0;
    }

    /**
     * Gets a list of all payment systems found in the transactions.
     *
     * @return Set of payment system names
     */
    public Set<String> getAllPaymentSystems() {
        // TODO: Return the key set from usageCounts map
        return null;
    }

    /**
     * Calculates total amount across all payment systems.
     *
     * @return total amount transacted
     */
    public double getTotalAllAmounts() {
        double total = 0.0;

        // TODO: Sum all values in totalAmounts map

        return total;
    }

    /**
     * Displays formatted statistics for all payment systems.
     */
    public void displayStatistics() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("=== Payment System Usage Statistics ===");
        System.out.println("=".repeat(70));

        System.out.println("\nTotal Transactions: " + transactions.size());
        System.out.println();

        // Display table header
        // Using String.format() for aligned columns
        // Start of special formatting for HW6
        System.out.println(String.format("%-15s %-12s %-15s %-12s %-12s",
                "System", "Count", "Total Amount", "Avg Amount", "% of Total"));
        System.out.println("-".repeat(70));

        // TODO: For each payment system, display its statistics
        // HINT: Iterate through getAllPaymentSystems()
            // TODO: Print formatted row
            // Example format: "Venmo          22           $850.00         $38.64       46.8%"

        // End of special formatting for HW6
        System.out.println("-".repeat(70));
        System.out.println();

        // Display visual representation
        System.out.println("Visual Representation:");

        // TODO: Create bar chart using '*'
        // HINT: For each system, print bars proportional to market share
        // HINT: Use █ character (or asterisks) to create bars

        // TODO: iterate through all payment systems
            // TODO: get the market share % per system (replace the '0.0')
            double marketShare = 0.0;

            // Using special formating for each system
            System.out.print(String.format("%-12s ", system));

            // TODO: Print stars - one star for every 2% market share
            // HINT: int stars = (int)(marketShare / 2);
            // HINT: Use a loop to print stars

            // TODO: Print percentage
            System.out.printf(" (%.1f%%)%n", marketShare);
        }

        System.out.println();

        System.out.println("\n" + "=".repeat(70));
    }

}
