package service;

import model.Audio;
import model.Song;

import java.util.*;
import java.util.stream.Collectors;

public class Library {
    private static Library instance;

    // کالکشن ۱: HashMap – دیتابیس اصلی با دسترسی O(1)
    private HashMap<String, Audio> audioMap = new HashMap<>();

    // کالکشن ۵: PriorityQueue – Trending بر اساس playCount (بیشترین اول)
    private PriorityQueue<Audio> trending = new PriorityQueue<>(
            (a, b) -> Long.compare(b.getPlayCount(), a.getPlayCount())
    );

    private Library() {

        loadTestData();
    }

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    private void loadTestData() {
        Song s1 = new Song("s1", "Hallelujah", "Leonard Cohen", 270, "Folk",
                "I've heard there was a secret chord...\nThat David played and it pleased the Lord...\nHallelujah\nEND");
        Song s2 = new Song("s2", "Bohemian Rhapsody", "Queen", 355, "Rock",
                "Is this the real life?\nIs this just fantasy?\nCaught in a landslide...\nEND");
        Song s3 = new Song("s3", "Skyfall", "Adele", 280, "Pop",
                "This is the end\nHold your breath and count to ten...\nEND");
        Song s4 = new Song("s4", "Numb", "Linkin Park", 185, "Rock",
                "I've become so numb\nI can't feel you there...\nEND");

        addAudio(s1);
        addAudio(s2);
        addAudio(s3);
        addAudio(s4);
    }

    public void addAudio(Audio audio) {
        audioMap.put(audio.getId(), audio);
        trending.offer(audio); // آپدیت خودکار trending
    }

    public Audio getAudioById(String id) {
        return audioMap.get(id);
    }

    public List<Audio> getAllAudios() {
        return new ArrayList<>(audioMap.values());
    }

    // برای نمایش Trending در Listener Home
    public List<Audio> getTrending(int count) {
        List<Audio> sorted = new ArrayList<>(trending);
        sorted.sort((a, b) -> Long.compare(b.getPlayCount(), a.getPlayCount()));
        return sorted.subList(0, Math.min(count, sorted.size()));
    }

    // جستجوی پیشرفته (فیلتر همزمان artist + genre + keyword)
    public List<Audio> advancedSearch(String keyword, String artistFilter, String genreFilter) {
        return audioMap.values().stream()
                .filter(audio -> keyword == null || keyword.isEmpty() ||
                        audio.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        audio.getArtist().toLowerCase().contains(keyword.toLowerCase()))
                .filter(audio -> artistFilter == null || artistFilter.isEmpty() ||
                        audio.getArtist().equalsIgnoreCase(artistFilter))
                .filter(audio -> genreFilter == null || genreFilter.isEmpty() ||
                        !(audio instanceof Song) || ((Song) audio).getGenre().equalsIgnoreCase(genreFilter))
                .collect(Collectors.toList());
    }
}