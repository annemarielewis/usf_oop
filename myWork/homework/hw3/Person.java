public abstract class Person { //NOTE: online recommended I use "abstract", did research into it and like it! Exclusively used it for first push so code was a little different then, but changed here to meet requirements :)

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String preferredPaymentSystem;
    private String relationship;

    //constructor
    public Person(String firstName, String lastName, String phoneNumber, String preferredPaymentSystem, String relationship) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.preferredPaymentSystem = preferredPaymentSystem;
        this.relationship = relationship;
    }

    //getters

    public String getRelationshipMessage() {
        return "I am a type of contact!";
    }
    //methods belonging to subclasses-->using abstract
    public abstract String getContactType();

    public String getFullName() {
        return firstName + " " + lastName;
    }
    public String getPhoneNumber() { return phoneNumber; }

    public String getPreferredPaymentSystem() { return preferredPaymentSystem; }

   // public abstract String getRelationshipMessage();

// Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setPreferredPaymentSystem(String preferredPaymentSystem) { this.preferredPaymentSystem = preferredPaymentSystem; }
    public void setRelationship(String relationship) { this.relationship = relationship; }
}