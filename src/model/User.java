package model;

import java.time.LocalDate;

public abstract class User {
    private String username;
    private String password;
    private double balance = 0.0;
    private LocalDate signupDate;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.signupDate = LocalDate.now();
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public double getBalance() { return balance; }
    public LocalDate getSignupDate() { return signupDate; }
    public void addBalance(double amount) { balance += amount; }

    // هر فرزند باید بگه آرتیسته یا نه
    public abstract boolean isArtist();
}