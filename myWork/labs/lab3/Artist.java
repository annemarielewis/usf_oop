import java.util.ArrayList;

public class Artist extends Entity{
//instance variables:
    ArrayList<Song> artistsSongs = new ArrayList<>(); //using our Song class as an instance variable type (array stores objects of type Song)
    ArrayList<Album> artistAlbums = new ArrayList<>(); //using our Album class to specify variable type (array stores objects of type Album)

    // 1) Constructor 1: initializes the instance variable 'name' with the parametern name that's passed in from main
    public Artist(String name) {
        super(name);
    }

    //Constructor 2: initializes all three instance variables with the parameters that're passed in from main
    public Artist(String name, ArrayList<Song> songs, ArrayList<Album> albums)  {
        super(name);
        this.artistsSongs = songs;
        this.artistAlbums = albums;
    }
//for printing object as string in Music Manager (printed wonky object # without)
    @Override
    public String toString() {
        return name; // or return "Artist: " + name;
    }
}
