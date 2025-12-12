import java.time.LocalDateTime;
import java.util.*;
import java.io.*;

/**
 * TransactionAnalyzer provides analysis of payment transactions:
 * - Top receivers by total amount received
 * - Payment system usage statistics (count, total, average, percent of total)
 *
 * payments.txt expected format (CSV):
 * RecipientName,Amount,PaymentSystem,Timestamp,Fee
 *
 * Example:
 * Timothy Carter,86.0,PayPal,2025-10-15T20:51:52.433397,1.25
 */
public class TransactionAnalyzer {

    private ArrayList<Payment> transactions;
    private ArrayList<Person> contacts;

    public TransactionAnalyzer() throws IOException {
        this.transactions = new ArrayList<>();
        this.contacts = new ArrayList<>();
        loadTransactions();
        loadContacts(); // optional, but nice to have for future extensions
    }

    /**
     * Loads all transactions from payments.txt.
     * Format: RecipientName,Amount,PaymentSystem,Timestamp,Fee
     */
    private void loadTransactions() throws IOException {
        File file = new File("payments.txt");
        if (!file.exists()) {
            // No transactions yet, that's okay.
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) {
                    // skip malformed
                    continue;
                }

                String recipientName = parts[0].trim();

                double amount;
                try {
                    amount = Double.parseDouble(parts[1].trim());
                } catch (NumberFormatException e) {
                    continue;
                }

                String system = parts[2].trim();

                LocalDateTime timestamp;
                try {
                    // ISO parser handles fractional seconds automatically
                    timestamp = LocalDateTime.parse(parts[3].trim());
                } catch (Exception e) {
                    continue;
                }

                double fee;
                try {
                    fee = Double.parseDouble(parts[4].trim());
                } catch (NumberFormatException e) {
                    fee = 0.0; // if fee is malformed, default it
                }

                transactions.add(new Payment(recipientName, amount, system, timestamp, fee));
            }
        }
    }

    /**
     * Loads all contacts from contacts.txt.
     * Expected format (based on your project):
     * ContactType,FirstName LastName,PhoneNumber,PreferredPaymentSystem
     *
     * Your Friend/Family constructors require:
     * (firstName, lastName, phoneNumber, preferredPaymentSystem, relationship)
     */
    private void loadContacts() throws IOException {
        File file = new File("contacts.txt");
        if (!file.exists()) {
            // No contacts file (or not needed for Task 4)
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue;

                String type = parts[0].trim();      // Friend / Family
                String fullName = parts[1].trim();  // "First Last"
                String phone = parts[2].trim();
                String system = parts[3].trim();

                String[] nameParts = fullName.split(" ");
                if (nameParts.length < 2) continue;

                String firstName = nameParts[0].trim();
                String lastName = nameParts[1].trim();

                // relationship required by constructor — we can safely default it
                String relationship = type;

                if (type.equalsIgnoreCase("Friend")) {
                    contacts.add(new Friend(firstName, lastName, phone, system, relationship));
                } else if (type.equalsIgnoreCase("Family")) {
                    contacts.add(new Family(firstName, lastName, phone, system, relationship));
                }
            }
        }
    }

    // ============================================================
    // TASK 4A: TOP RECEIVERS
    // ============================================================

    /**
     * Returns the top N receivers by total $ received, sorted descending.
     * This is the most reliable way to do Task 4A because it uses payments.txt directly.
     */
    public ArrayList<Map.Entry<String, Double>> getTopReceivers(int n) {
        HashMap<String, Double> totals = new HashMap<>();

        for (Payment p : transactions) {
            String name = p.getRecipientName();
            totals.put(name, totals.getOrDefault(name, 0.0) + p.getAmount());
        }

        ArrayList<Map.Entry<String, Double>> entries = new ArrayList<>(totals.entrySet());
        entries.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        if (entries.size() > n) {
            return new ArrayList<>(entries.subList(0, n));
        }
        return entries;
    }

    // ============================================================
    // TASK 4B: PAYMENT SYSTEM STATS
    // ============================================================

    /**
     * Holds one row of stats for a payment system.
     */
    public static class SystemStats {
        String system;
        int transactions;
        double totalAmount;
        double avgAmount;
        double percentOfTotal; // based on totalAmount / grandTotalAmount

        public SystemStats(String system, int transactions, double totalAmount,
                           double avgAmount, double percentOfTotal) {
            this.system = system;
            this.transactions = transactions;
            this.totalAmount = totalAmount;
            this.avgAmount = avgAmount;
            this.percentOfTotal = percentOfTotal;
        }
    }

    /**
     * Builds system statistics for Task 4B.
     * Percent is based on share of total amount (matches the sample screen vibe).
     */
    public ArrayList<SystemStats> getPaymentSystemStatistics() {
        HashMap<String, Integer> counts = new HashMap<>();
        HashMap<String, Double> totals = new HashMap<>();

        double grandTotal = 0.0;

        for (Payment p : transactions) {
            String sys = p.getPaymentSystem();
            counts.put(sys, counts.getOrDefault(sys, 0) + 1);
            totals.put(sys, totals.getOrDefault(sys, 0.0) + p.getAmount());
            grandTotal += p.getAmount();
        }

        ArrayList<SystemStats> rows = new ArrayList<>();

        for (String sys : counts.keySet()) {
            int c = counts.get(sys);
            double t = totals.getOrDefault(sys, 0.0);
            double avg = (c == 0) ? 0.0 : t / c;
            double pct = (grandTotal == 0.0) ? 0.0 : (t / grandTotal) * 100.0;

            rows.add(new SystemStats(sys, c, t, avg, pct));
        }

        // Sort by percent descending (or could sort by totalAmount)
        rows.sort((a, b) -> Double.compare(b.percentOfTotal, a.percentOfTotal));
        return rows;
    }

    /**
     * Convenience: total number of loaded transactions.
     */
    public int getTotalTransactionCount() {
        return transactions.size();
    }

    /**
     * Convenience: total amount across all transactions.
     */
    public double getGrandTotalAmount() {
        double sum = 0.0;
        for (Payment p : transactions) sum += p.getAmount();
        return sum;
    }

    // Getters (if you want them elsewhere)
    public ArrayList<Payment> getAllTransactions() { return transactions; }
    public ArrayList<Person> getAllContacts() { return contacts; }

    // ============================================================
    // PRINT HELPERS (makes MyPaymentSystem case 8 super clean)
    // ============================================================

    /**
     * Prints top receivers exactly for Task 4A.
     */
    public void printTopReceivers(int n) {
        ArrayList<Map.Entry<String, Double>> top = getTopReceivers(n);

        if (top.isEmpty()) {
            System.out.println("No transactions found yet.");
            return;
        }

        for (int i = 0; i < top.size(); i++) {
            System.out.printf("%d. %s - $%.2f%n", i + 1, top.get(i).getKey(), top.get(i).getValue());
        }
    }

    /**
     * Prints system stats screen like your spec (table + bars).
     */
    public void printPaymentSystemStatisticsScreen() {
        int totalTx = getTotalTransactionCount();
        if (totalTx == 0) {
            System.out.println("No transactions found yet.");
            return;
        }

        ArrayList<SystemStats> rows = getPaymentSystemStatistics();

        System.out.println("=== Payment System Usage Statistics ===");
        System.out.println("Total Transactions: " + totalTx + " Payment System Breakdown:");

        System.out.println("┌─────────────┬──────────────┬──────────────┬──────────────┬─────────────┐");
        System.out.println("│ System      │ Transactions  │ Total Amount  │ Avg Amount    │ % of Total   │");
        System.out.println("├─────────────┼──────────────┼──────────────┼──────────────┼─────────────┤");

        for (SystemStats s : rows) {
            System.out.printf(
                    "│ %-11s │ %-12d │ $%-11.2f │ $%-11.2f │ %-10.1f%% │%n",
                    s.system, s.transactions, s.totalAmount, s.avgAmount, s.percentOfTotal
            );
        }

        System.out.println("└─────────────┴──────────────┴──────────────┴──────────────┴─────────────┘");

        System.out.println("Visual Representation:");
        for (SystemStats s : rows) {
            int stars = (int) Math.round(s.percentOfTotal / 2.0); // 1 star ≈ 2%
            String bar = "*".repeat(Math.max(0, stars));
            System.out.printf("%-9s %s (%.1f%%)%n", s.system, bar, s.percentOfTotal);
        }
    }
}
