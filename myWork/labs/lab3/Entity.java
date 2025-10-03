import java.util.Date;

public class Entity {
    //instance variables
    String name;
    Date dateCreated;
    int entityCounter = 0; //to help give an id to each object created that's a child of this parent entity class
    int entityID; //get ID from counter

    //constructor 1: no name passed in
    public Entity () {
        this.name = "";
        this.dateCreated = new Date(); //current time
        entityCounter++;
        this.entityID = entityCounter; //different variables incase we ever delete objects, we don't overwrite their IDs
        //"Example: if you delete an artist with ID 5, the next artist created gets ID 6, not 5 again."
    }
    // constructor 2: name passed in
    public Entity (String name) {
        this.name = name;
        this.dateCreated = new Date();
        entityCounter++;
        this.entityID = entityCounter;
    }
}


