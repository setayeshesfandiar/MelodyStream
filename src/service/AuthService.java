package service;

import model.User;
import model.Listener;
import model.Artist;
import exception.UserNotFoundException;
import exception.WrongPasswordException;

import java.util.HashMap;

public class AuthService {
    private static AuthService instance;

    private HashMap<String, User> users = new HashMap<>();
    private User currentUser;

    private AuthService() {
        // داده تستی برای تست راحت
        users.put("listener", new Listener("listener", "123"));
        users.put("artist", new Artist("artist", "123"));
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public void registerListener(String username, String password) {
        users.put(username, new Listener(username, password));
    }

    public void registerArtist(String username, String password) {
        users.put(username, new Artist(username, password));
    }

    public void login(String username, String password) throws UserNotFoundException, WrongPasswordException {
        User user = users.get(username);
        if (user == null) throw new UserNotFoundException("کاربر یافت نشد");
        if (!user.getPassword().equals(password)) throw new WrongPasswordException("رمز اشتباه");
        currentUser = user;
    }

    public User getCurrentUser() { return currentUser; }
    public boolean isLoggedIn() { return currentUser != null; }
    public boolean isArtist() { return currentUser != null && currentUser.isArtist(); }

    // این متد برای درآمد آرتیست
    public void addEarningsToArtist(String artistName, double amount) {
        User user = users.get(artistName);
        if (user instanceof Artist) {
            ((Artist) user).addEarnings(amount);
        }
    }

    public HashMap<String, User> getAllUsers() { return users; }
}