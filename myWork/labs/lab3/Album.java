import java.util.ArrayList;

public class Album extends Entity{
    // 1) Instance variables that will be stored as what's passed in from constructor
    ArrayList<Song> songs = new ArrayList<>(); //using our Song class to specify instance variable's object type (array stores object type Song)
    Artist performer;  //using our Artist class to specify instance variable's object type (performer has object type Artist)

    //constructor 1: incoming parameters stored as instance variables
    public Album(String name) {
        super(name);
    }

    //constructor 2: incoming parameters stored as instance variables
    public Album(String name, ArrayList<Song> songs, Artist performer) //passing in objects (-->really, variables that point to objects)
    {
        super(name);
        this.songs = songs;
        this.performer = performer;
    }
}
