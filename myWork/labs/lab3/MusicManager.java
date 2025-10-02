public class MusicManager {
    public static void main(String[] args) {
        // Create an artist
        Artist artist = new Artist("The Beatles");

        // Create some Songs
        Song song1 = new Song("Help");
        Song song2 = new Song("Ticket to Ride");

        // Link songs to the artist-->
        // song1.artist refers to instance variable artist within the Song class
        song1.artist = artist; //artist referst to the artist object created above in this main method
        song2.artist = artist;

        // We also apply songs to the artist class
        //setting instance variable of an array "artistSongs" in artist class to add song1 (object created above) to the artist created above ("the beatles")
        artist.artistsSongs.add(song1);
        artist.artistsSongs.add(song2);

        // Create an Album
        Album album = new Album("Help");
        album.performer = artist;

        // We also apply the songs to the albums
        album.songs.add(song1);
        album.songs.add(song2);

        // Link album to artist
        artist.artistAlbums.add(album);

        // Print some details
        System.out.println("Artist: " + artist.name);
        System.out.println("Album: " + album.name);
        System.out.println("Songs in the album:");

        for (Song s : album.songs) {
            System.out.println(" - " + s.name);
        }
    }
}
