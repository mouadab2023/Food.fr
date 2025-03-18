package data;

public class Avis {
    private String username;
    private String description;
    private String picture;
    private int note;
    private String restaurant;

    public Avis() {
        // Empty constructor for Firestore
    }

    public Avis(String username, String description, String picture, int note, String restaurant) {
        this.username = username;
        this.description = description;
        this.picture = picture;
        this.note = note;
        this.restaurant = restaurant;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public int getNote() { return note; }
    public void setNote(int note) { this.note = note; }

    public String getRestaurant() { return restaurant; }
    public void setRestaurant(String restaurant) { this.restaurant = restaurant; }
}
