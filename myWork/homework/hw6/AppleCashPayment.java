public class AppleCashPayment implements PaymentSystem {

    @Override
    public void pay(Person person, double amount) throws PaymentFailedException {
        double fee = getTransactionFee(amount);
        double total = amount + fee;

        System.out.println("AppleCash: Processing payment of $" + amount + " to " + person.getFullName() + "w/ no transaction fee!...");
        System.out.println("AppleCash: Transaction fee: $" + fee);
        System.out.println("AppleCash: Total charged: $" + total);
        System.out.println("AppleCash: Payment completed successfully!");
    }

    @Override
    public String getSystemName() {
        return "AppleCash";
    }

    @Override
    public double getTransactionFee(double amount) {
        // Flat $1 fee
        return 1.00;
    }
}