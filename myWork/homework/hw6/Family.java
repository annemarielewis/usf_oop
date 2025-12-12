public class Family extends Person {
    //extends Person class: Friend inherits everything Person has — its fields, methods, and constructor behavior
    public Family(String f, String l, String p, String pay, String r) {
        super(f, l, p, pay, r); //passes argument variables into Person constructor
    }
    public String getContactType() { return "Family"; }
    @Override
    public String getRelationshipMessage() { return "This is a family member"; }
}