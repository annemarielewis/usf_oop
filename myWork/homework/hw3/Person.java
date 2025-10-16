public abstract class Person {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String preferredPaymentSystem;

    //constructor
    public Person(String firstName, String lastName, String phoneNumber, String preferredPaymentSystem) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.preferredPaymentSystem = preferredPaymentSystem;
    }

    //getters
    public String getFullName() {
        return firstName + " " + lastName;
    }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPreferredPaymentSystem() { return preferredPaymentSystem; }

    public abstract String getContactType();
    public abstract String getRelationshipMessage();

// Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setPreferredPaymentSystem(String preferredPaymentSystem) { this.preferredPaymentSystem = preferredPaymentSystem; }
}