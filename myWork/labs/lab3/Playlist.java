import java.util.ArrayList;

// Lab 3: what is the purpose of a PlayList?
// Ans: contains an ArrayList of songs
// Required Methods: add or delete songs (obj type: Song)

public class Playlist {
    //instance variable songlist for array of the songs
    private ArrayList<Song> songlist;

    //constructor
    public Playlist() {
        songlist = new ArrayList<Song>();
    }
}
