import java.util.ArrayList;

public class Library {
    ArrayList<Song> songs;

    public Library() {
        songs = new ArrayList<Song>();
    }

    // Task 7: search for a song in library: findSong(String songname)
    public Song findSong(String songName) {
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getName().equals(songName)) {
                return songs.get(i);
            }
        }
        return null;
    }
    //Task 7: add song to library: addSong
    public void addSong(Song s) {

        songs.add(s);
    }
}

    // FOR LAB 3
    // A) Write a method called getLiked():
    //  -- it will return an ArrayList of Song
    // -- it will look through all the songs in a library and check the isLiked()
    // -- if a Song is liked, add it to the ArrayList of likedSongs
    // -- (and return that ArrayList)

    public ArrayList<Song> getLiked() {
        ArrayList<Song> likedSongs = new ArrayList<>();
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).isLiked()) {
                likedSongs.add(songs.get(i));
            }
        }
        return likedSongs; // return an ArrayList of LikedSongs
    }



