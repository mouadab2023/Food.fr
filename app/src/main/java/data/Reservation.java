package data;

import com.google.firebase.firestore.PropertyName;

public class Reservation {
    private String date;
    private String restaurant;
    private int nombrePersonne;
    private String nom;

    // Constructeur vide pour Firestore
    public Reservation() {}

    @PropertyName("Date")
    public String getDate() {
        return date;
    }

    @PropertyName("Date")
    public void setDate(String date) {
        this.date = date;
    }

    @PropertyName("Restaurant")
    public String getRestaurant() {
        return restaurant;
    }

    @PropertyName("Restaurant")
    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    @PropertyName("Nombre")
    public int getNombrePersonne() {
        return nombrePersonne;
    }

    @PropertyName("Nombre")
    public void setNombrePersonne(int nombrePersonne) {
        this.nombrePersonne = nombrePersonne;
    }

    @PropertyName("Nom")
    public String getNom() {
        return nom;
    }

    @PropertyName("Nom")
    public void setNom(String nom) {
        this.nom = nom;
    }
}
