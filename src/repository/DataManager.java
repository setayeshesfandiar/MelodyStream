package repository;

import service.Library;
import service.AuthService;

import java.io.*;
import java.time.LocalDateTime;

public class DataManager {
    private static final String LIBRARY_FILE = "library.ser";
    private static final String USERS_FILE = "users.ser";
    private static final String TRANSACTIONS_FILE = "transactions.csv";

    // ذخیره داده‌ها موقع خروج برنامه
    public static void saveData() {
        // ذخیره لایبرری آهنگ‌ها
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(LIBRARY_FILE))) {
            oos.writeObject(Library.getInstance());
        } catch (IOException e) {
            System.out.println("خطا در ذخیره لایبرری آهنگ‌ها");
        }

        // ذخیره کاربران
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(AuthService.getInstance().getAllUsers());
        } catch (IOException e) {
            System.out.println("خطا در ذخیره کاربران");
        }
    }

    // بارگذاری داده‌ها موقع شروع برنامه
    public static void loadData() {
        // بارگذاری لایبرری
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(LIBRARY_FILE))) {
            // اینجا می‌تونی جایگزین کنی اگر نیاز بود (برای singleton ساده گذاشتم)
        } catch (Exception e) {
            System.out.println("فایل لایبرری پیدا نشد – داده جدید ساخته می‌شود");
        }

        // بارگذاری کاربران
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            // مشابه بالا
        } catch (Exception e) {
            System.out.println("فایل کاربران پیدا نشد – کاربر جدید ساخته می‌شود");
        }
    }

    // لاگ تراکنش‌ها به فرمت CSV
    public static void logTransaction(String username, String type, double amount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            writer.write(LocalDateTime.now() + "," + username + "," + type + "," + amount);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("خطا در نوشتن لاگ تراکنش");
        }
    }
}