package model;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

public class Listener extends User {
    // الزامی: HashSet برای favorites (۲۵ نمره کالکشن‌ها)
    private HashSet<Audio> favorites = new HashSet<>();

    // برای recommendation: امتیاز ژانرها
    private Map<String, Integer> genrePreferences = new HashMap<>();

    public Listener(String username, String password) {
        super(username, password);
    }

    public void addToFavorites(Audio audio) {
        if (audio instanceof Song song) {
            favorites.add(audio);
            genrePreferences.put(song.getGenre(),
                    genrePreferences.getOrDefault(song.getGenre(), 0) + 1);
        }
    }

    public HashSet<Audio> getFavorites() { return favorites; }
    public Map<String, Integer> getGenrePreferences() { return genrePreferences; }

    @Override
    public boolean isArtist() { return false; }
}