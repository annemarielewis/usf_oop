import java.time.LocalDateTime;

/**
 * Represents a single scheduled recurring payment.
 * Stores the recipient, amount, payment system, next payment date, and
 * recurrence pattern. Comparable by nextPaymentDate so it works in a PriorityQueue.
 */
public class ScheduledPayment implements Comparable<ScheduledPayment> {

    private Person recipient;
    private double amount;
    private String paymentSystem;
    private LocalDateTime nextPaymentDate;
    private RecurrenceType recurrence;

    /**
     * Creates a new ScheduledPayment.
     *
     * @param recipient       the person to pay
     * @param amount          the payment amount
     * @param paymentSystem   the system name (Venmo, PayPal, AppleCash, etc.)
     * @param nextPaymentDate the next date/time this payment is due
     * @param recurrence      how often it repeats
     */
    public ScheduledPayment(Person recipient, double amount, String paymentSystem,
                            LocalDateTime nextPaymentDate, RecurrenceType recurrence) {
        this.recipient = recipient;
        this.amount = amount;
        this.paymentSystem = paymentSystem;
        this.nextPaymentDate = nextPaymentDate;
        this.recurrence = recurrence;
    }

    public Person getRecipient() {
        return recipient;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentSystem() {
        return paymentSystem;
    }

    public LocalDateTime getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(LocalDateTime nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public RecurrenceType getRecurrence() {
        return recurrence;
    }

    @Override
    public int compareTo(ScheduledPayment other) {
        // Earlier dates should come first
        return this.nextPaymentDate.compareTo(other.nextPaymentDate);
    }
}
