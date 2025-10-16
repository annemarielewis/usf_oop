public class PayPalPayment implements PaymentSystem {

    @Override
    public void pay(Person person, double amount) throws PaymentFailedException {
        double fee = getTransactionFee(amount);
        double total = amount + fee;
        System.out.println("PayPal: Paid $" + amount + " to " + person.getFullName() + " with $" + fee + " fee. Total charged: $" + total);
    }

    @Override
    public String getSystemName() {
        return "PayPal";
    }

    public double getTransactionFee(double amount) {
        double fee;
        if (amount > 10) {
            fee = amount * 0.025;
        } else {
            fee = 0.00;
        }
        return Math.round(fee * 100.0) / 100.0;
    }
}