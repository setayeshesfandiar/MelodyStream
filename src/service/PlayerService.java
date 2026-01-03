package service;
import service.AuthService;
import repository.DataManager;
import exception.EmptyQueueException;
import model.Audio;
import exception.EmptyQueueException;

import java.util.*;

public class PlayerService {
    private static PlayerService instance;

    // کالکشن ۲: LinkedList – صف پخش
    private LinkedList<Audio> playQueue = new LinkedList<>();

    // کالکشن ۳: Stack – تاریخچه برای Previous
    private Stack<Audio> history = new Stack<>();

    private Audio currentAudio;
    private boolean isPlaying = false;
    private boolean shuffleMode = false;

    private PlayerService() {}

    public static PlayerService getInstance() {
        if (instance == null) {
            instance = new PlayerService();
        }
        return instance;
    }

    public void enqueue(Audio audio) {
        playQueue.addLast(audio);
    }

    public void play() throws EmptyQueueException {
        if (currentAudio == null && playQueue.isEmpty()) {
            throw new EmptyQueueException("صف پخش خالی است! ابتدا آهنگی اضافه کنید.");
        }
        if (currentAudio == null) {
            currentAudio = playQueue.pollFirst();
        }
        isPlaying = true;
        currentAudio.play();


        AuthService auth = AuthService.getInstance();
        auth.addEarningsToArtist(currentAudio.getArtist(), 0.5);

        // لاگ تراکنش
        repository.DataManager.logTransaction(currentAudio.getArtist(), "PlayEarning", 0.5);
        // پلی‌مورفیسم در عمل!

        // درآمد آرتیست: هر پلی ۰.۵ دلار (بعداً کامل‌تر می‌کنیم)
        // TODO: add earnings
    }

    public void next() throws EmptyQueueException {
        if (playQueue.isEmpty()) {
            throw new EmptyQueueException("آهنگ بعدی وجود ندارد!");
        }
        if (currentAudio != null) {
            history.push(currentAudio);
        }
        currentAudio = playQueue.pollFirst();
        play();
    }

    public void previous() throws EmptyQueueException {
        if (history.isEmpty()) {
            throw new EmptyQueueException("آهنگ قبلی وجود ندارد!");
        }
        if (currentAudio != null) {
            playQueue.addFirst(currentAudio);
        }
        currentAudio = history.pop();
        play();
    }

    public void toggleShuffle() {
        shuffleMode = !shuffleMode;
        if (shuffleMode) {
            Collections.shuffle(playQueue);
        }
    }

    public Audio getCurrent() { return currentAudio; }
    public LinkedList<Audio> getQueue() { return playQueue; }
    public Stack<Audio> getHistory() { return history; }
    public boolean isShuffleMode() { return shuffleMode; }
}