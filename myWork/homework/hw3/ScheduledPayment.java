import java.time.LocalDateTime;

/**
 * Represents a scheduled recurring payment.
 * Implements Comparable to allow automatic sorting by payment date in PriorityQueue.
 *
 * @author [Your Name]
 * @version 1.0
 */
public class ScheduledPayment implements Comparable<ScheduledPayment> {

    private Person recipient;
    private double amount;
    private String paymentSystem;
    private LocalDateTime nextPaymentDate;
    private RecurrenceType recurrence;

    /**
     * Constructs a new ScheduledPayment.
     *
     * @param recipient the person to receive the payment
     * @param amount the payment amount (must be positive)
     * @param paymentSystem the payment system to use
     * @param nextPaymentDate when the next payment is due
     * @param recurrence how often the payment repeats
     */
    public ScheduledPayment(Person recipient, double amount, String paymentSystem,
                            LocalDateTime nextPaymentDate, RecurrenceType recurrence) {
        this.recipient = recipient;
        this.amount = amount;
        this.paymentSystem = paymentSystem;
        this.nextPaymentDate = nextPaymentDate;
        this.recurrence = recurrence;
    }

    /**
     * Compares this payment to another based on next payment date.
     * Used by PriorityQueue to maintain sorted order (earliest dates first).
     *
     * @param other the ScheduledPayment to compare to
     * @return negative if this payment is due earlier, positive if later, 0 if same time
     */
    @Override
    //Compare nextPaymentDate of this payment with other's
    public int compareTo(ScheduledPayment other) {
        return this.nextPaymentDate.compareTo(other.nextPaymentDate);
    }
    //LocalDateTime.compareTo() returns an int:
    //Negative if this date is earlier
    //Zero if equal
    //Positive if this date is later
    //-->(nextpaymentdate is a type of localdatetime, so can access it)
    //

      // Getters
    public Person getRecipient() { return recipient; }
    public double getAmount() { return amount; }
    public String getPaymentSystem() { return paymentSystem; }
    public LocalDateTime getNextPaymentDate() { return nextPaymentDate; }
    public RecurrenceType getRecurrence() { return recurrence; }

    // Setters
    /**
     * Sets a new next payment date (used when rescheduling).
     * @param nextPaymentDate the new due date
     */
    public void setNextPaymentDate(LocalDateTime nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    @Override
    public String toString() {
        return String.format("ScheduledPayment[recipient=%s, amount=%.2f, system=%s, next=%s, recurrence=%s]",
                recipient.getFullName(), amount, paymentSystem, nextPaymentDate, recurrence);
    }
}