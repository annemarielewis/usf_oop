public class Friend extends Person {
    //extends Person class: Friend inherits everything Person has — its fields, methods, and constructor behavior
    public Friend(String f, String l, String p, String pay) {
        super(f, l, p, pay); //passes argument variables into Person constructor
    }
    public String getContactType() { return "Friend"; }
    public String getRelationshipMessage() { return "This is a friend!"; }
}