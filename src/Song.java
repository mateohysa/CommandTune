public class Song {

    private String title;
    private String artist;
    private String genre;
    private int duration;


    public Song(String title, String artist, String genre, int duration)
    {
        setTitle(title);
        setArtist(artist);
        setGenre(genre);
        setDuration(duration);
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getArtist() {
        return artist;
    }


    public void setArtist(String artist) {
        this.artist = artist;
    }


    public String getGenre() {
        return genre;
    }


    public void setGenre(String genre) {
        this.genre = genre;
    }


    public int getDuration() {
        return duration;
    }


    public void setDuration(int duration) {
        if(duration > 0)
            this.duration = duration;
    }


    @Override
    public String toString() {
        return "Song  [ Title: " + getTitle() + " by " + getArtist() + " ( " + getDuration()/60 +" : "+getDuration()%60+ " )]";
    }





}
