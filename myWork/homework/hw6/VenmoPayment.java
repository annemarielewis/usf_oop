// VenmoPayment.java
public class VenmoPayment implements PaymentSystem {

    @Override
    public void pay(Person person, double amount) throws PaymentFailedException {
        // free instant payment
        System.out.println("Venmo: Paid $" + amount + " to " + person.getFullName() + " instantly--w/ $0 fee!");
    }

    @Override
    public String getSystemName() {
        return "Venmo";
    }

    @Override
    public double getTransactionFee(double amount) {
        // Venmo is free in this example
        return 0.00;
    }
}
