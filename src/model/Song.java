package model;

public class Song extends Audio {
    private String genre;
    private String lyrics;

    public Song(String id, String title, String artist, int duration, String genre, String lyrics) {
        super(id, title, artist, duration);
        this.genre = genre;
        this.lyrics = lyrics;
    }

    @Override
    public void play() {
        incrementPlayCount();
        System.out.println("♪ در حال پخش آهنگ: " + getTitle() + " از " + getArtist());
    }

    public String getGenre() { return genre; }
    public String getLyrics() { return lyrics; }
}