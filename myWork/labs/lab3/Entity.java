import java.util.Date;

public class Entity {
    //instance variables
    String name;
    Date dateCreated;

    //constructor 1: no name passed in
    public Entity () {
        this.name = "";
        this.dateCreated = dateFromCaller; //current time
    }
    // constructor 2: name passed in
    public Entity (String name) {
        this.name = name;
        this.dateCreated = new Date();
    }
}


