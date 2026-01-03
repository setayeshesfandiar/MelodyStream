package model;

public class Artist extends User {
    private double earnings = 0.0;

    public Artist(String username, String password) {
        super(username, password);
    }

    public void addEarnings(double amount) { earnings += amount; }
    public double getEarnings() { return earnings; }

    @Override
    public boolean isArtist() { return true; }
}