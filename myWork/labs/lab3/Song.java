public class Song extends Entity {

    // 1) Instance Variables: what we will need to store from what's passed in
    Artist artist; //object type Artist
    Album album; //object type Album


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

}
