package model;

public abstract class Audio {
    private String id;
    private String title;
    private String artist;
    private int duration; // به ثانیه
    private int likes = 0;
    private long playCount = 0;

    public Audio(String id, String title, String artist, int duration) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    // پلی‌مورفیسم: هر فرزند (Song یا Podcast) رفتار خودش رو داره
    public abstract void play();

    // Getters و متدهای کمکی
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getDuration() { return duration; }
    public int getLikes() { return likes; }
    public long getPlayCount() { return playCount; }

    public void like() { likes++; }
    public void incrementPlayCount() { playCount++; }

    @Override
    public String toString() {
        return title + " - " + artist + " (❤️ " + likes + " | ▶️ " + playCount + ")";
    }
}