package model;

import java.util.List;

public class Restaurant {

    private final String name;
    private final String description;
    private List<Avis> avis;

    public Restaurant(String name, String description, List<Avis> avis) {
        this.name = name;
        this.description = description;
        this.avis = avis;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Avis> getAvis() {
        return avis;
    }

    public void setAvis(List<Avis> avis) {
        this.avis = avis;
    }
}
