import java.util.ArrayList;

// Lab 3: what is the purpose of a PlayList?
// Ans: contains an ArrayList of songs
// Required Methods: add or delete songs (obj type: Song)

public class Playlist extends Entity {
    //instance variable saving space
    private ArrayList<Song> songlist;

    //constructor
    public Playlist() {

        songlist = new ArrayList<Song>();
    }

    //add song method
    public void addSong(Song s) {

        songlist.add(s);
    }

    //delete song method
    public void deleteSong(Song s) {
        if (songlist.contains(s)) {
            songlist.remove(s);
        } else {
            System.out.printf("%s is not in the playlist\n", s.toString());
        }
    }
}

