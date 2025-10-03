public class MusicManager {
    public static void main(String[] args) {

        // Created an artist
        Artist beatles = new Artist("The Beatles");
        System.out.println("artist : " + beatles.name);

        // Created some Songs
        Song abbey = new Song("Abbey Road");
        Song lucy = new Song("Lucy in the Sky");
        System.out.println("songs created: " + abbey.name + " + " + lucy.name);

        // Created an Album
        Album abbeyR = new Album("Abbey Road Album");
        Album misc = new Album("MISC");
        System.out.println("albums created: " + abbeyR.name + " + " + misc.name);

        // Link songs to the artist-->
        // help.artist refers to instance variable artist within the Song class + help object
        abbey.artist = beatles; //beatles refers to the beatles object created above in this main method (^artist refers to instance variable artist in help object)
        lucy.artist = beatles;
        System.out.println("artists of these songs : " + abbey.artist + " + " + lucy.artist);
        //added override to make this work in Artist class! :)

        // Link artist to songs-->
        //setting instance variable of an array "artistSongs" in artist class to add song1 (object created above) to the artist created above ("the beatles")
        beatles.artistsSongs.add(abbey);
        beatles.artistsSongs.add(lucy);
        System.out.println("beatles songs : ");
        for (Song s : beatles.artistsSongs) {
            System.out.println(" - " + s.name);
        }

        // linked album to performer
        abbeyR.performer = beatles;
        System.out.println("performer of " + abbey.name + " " + abbeyR.performer);

        // linked songs to the albums
        abbeyR.songs.add(abbey);
        abbeyR.songs.add(lucy);
        System.out.println("Songs in " + abbeyR.name + " :");
        for (Song s : abbeyR.songs) {
            System.out.println(" - " + s.name);
        }

        // Linked artist to their albums
        beatles.artistAlbums.add(abbeyR);
        beatles.artistAlbums.add(misc);
        System.out.println("albumns under beatles: ");
        for (Album a : beatles.artistAlbums) {
            System.out.println("- " + a.name);
        }
    }
}
