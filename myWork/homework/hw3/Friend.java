public class Friend extends Person {
    //extends Person class: Friend inherits everything Person has — its fields, methods, and constructor behavior
    public Friend(String firstName, String lastName, String phoneNumber, String preferredPaymentSystem, String relationship) {
        super(firstName, lastName, phoneNumber, preferredPaymentSystem, relationship);
    }

//used abstract here :)
    public String getContactType() {
        return "Friend";
    }

    @Override
    public String getRelationshipMessage() {
        return "This is a friend!";
    }
}