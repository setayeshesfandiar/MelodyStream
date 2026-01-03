package service;

import model.Audio;
import model.Song;
import model.Listener;

import java.util.*;
import java.util.stream.Collectors;

public class RecommendationService {
    private static RecommendationService instance;

    private RecommendationService() {}

    public static RecommendationService getInstance() {
        if (instance == null) {
            instance = new RecommendationService();
        }
        return instance;
    }

    // پیشنهاد ۵ آهنگ بر اساس ژانر مورد علاقه کاربر
    public List<Audio> getRecommendations(Listener listener) {
        Map<String, Integer> genreScores = listener.getGenrePreferences();

        if (genreScores.isEmpty()) {
            // اگر favorites خالی بود، trending پیشنهاد بده
            return Library.getInstance().getTrending(5);
        }

        // پیدا کردن ژانر محبوب‌ترین
        String topGenre = genreScores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Rock"); // پیش‌فرض

        // پیشنهاد آهنگ‌های همان ژانر که هنوز favorite نشده
        return Library.getInstance().getAllAudios().stream()
                .filter(audio -> audio instanceof Song)
                .map(audio -> (Song) audio)
                .filter(song -> song.getGenre().equalsIgnoreCase(topGenre))
                .filter(song -> !listener.getFavorites().contains(song))
                .limit(5)
                .collect(Collectors.toList());
    }
}