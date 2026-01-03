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
        // داده تستی اولیه (برای تست راحت بدون ثبت‌نام دستی)
        users.put("listener", new Listener("listener", "123"));
        users.put("artist", new Artist("artist", "123"));
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    // ثبت‌نام شنونده
    public void registerListener(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("⚠️ نام کاربری قبلاً استفاده شده است!");
            return;
        }
        users.put(username, new Listener(username, password));
        System.out.println("✅ شنونده با موفقیت ثبت‌نام شد.");
    }

    // ثبت‌نام آرتیست
    public void registerArtist(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("⚠️ نام کاربری قبلاً استفاده شده است!");
            return;
        }
        users.put(username, new Artist(username, password));
        System.out.println("✅ آرتیست با موفقیت ثبت‌نام شد.");
    }

    // ورود به حساب
    public void login(String username, String password) throws UserNotFoundException, WrongPasswordException {
        User user = users.get(username);
        if (user == null) {
            throw new UserNotFoundException("کاربر با این نام یافت نشد!");
        }
        if (!user.getPassword().equals(password)) {
            throw new WrongPasswordException("رمز عبور اشتباه است!");
        }
        currentUser = user;
        System.out.println("✅ خوش آمدید " + username + "!");
    }

    // خروج از حساب
    public void logout() {
        if (currentUser != null) {
            System.out.println("👋 " + currentUser.getUsername() + " با موفقیت خارج شد.");
            currentUser = null;
        }
    }

    // افزودن درآمد به آرتیست (هر بار پخش آهنگ)
    public void addEarningsToArtist(String artistName, double amount) {
        User user = users.get(artistName);
        if (user instanceof Artist) {
            ((Artist) user).addEarnings(amount);
        }
    }

    // دسترسی به کاربر فعلی
    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public boolean isArtist() {
        return currentUser != null && currentUser.isArtist();
    }

    // برای ذخیره‌سازی کاربران
    public HashMap<String, User> getAllUsers() {
        return users;
    }
}