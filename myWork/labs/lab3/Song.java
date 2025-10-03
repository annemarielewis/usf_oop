public class Song extends Entity {

    // 1) Instance Variables: what we will need to store from what's passed in
    Artist artist; //object type Artist
    Album album; //object type Album
    SongInterval interval;
    String genre;
    boolean liked;

    //constructor 1: just name passed in //note --> super calls parent (Entity) 's constructor
    public Song(String name) {
        super(name);
    }
    //Constructor 2: parameters that are passed in are stored in variables above
    public Song(String name, Artist artist, Album album) {
        super(name);
        this.artist = artist;
        this.album = album;
    }
    //setters+getters
    public boolean isLiked() {
        return liked;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

        public Album getAlbum() {
        return album;
    }
    public void setAlbum(Album album) {
        this.album = album;
    }
    public Artist getPerformer() {
        return artist;
    }
    public void setPerformer(Artist performer) {
        this.artist = performer;
    }

    //implement an equality check: what makes a song a equal?
    public boolean equals(Song other) {
        return (this.name.equals(other.name) &&
                this.artist.equals(other.artist) &&
                this.album.equals(other.album));
    }
}
