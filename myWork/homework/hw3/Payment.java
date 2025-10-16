import java.time.LocalDateTime;

public class Payment {
    private String recipientName;
    private double amount;
    private String paymentSystem;
    private LocalDateTime timestamp;
    private double transactionFee;

    public Payment(String recipientName, double amount, String paymentSystem, LocalDateTime timestamp, double transactionFee) {
        this.recipientName = recipientName;
        this.amount = amount;
        this.paymentSystem = paymentSystem;
        this.timestamp = timestamp;
        this.transactionFee = transactionFee;
    }

    // Getters
    public String getRecipientName() { return recipientName; }
    public double getAmount() { return amount; }
    public String getPaymentSystem() { return paymentSystem; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public double getTransactionFee() { return transactionFee; }

    @Override
    public String toString() {
        return "Recipient: " + recipientName +
                ", Amount: $" + amount +
                ", Payment System: " + paymentSystem +
                ", Timestamp: " + timestamp +
                ", Fee: $" + transactionFee;
    }
}
