public interface PaymentSystem {
    void pay(Person person, double amount) throws PaymentFailedException;
    String getSystemName();
    double getTransactionFee(double amount);
}

