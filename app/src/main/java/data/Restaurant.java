package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable {

    private final String name;
    private final String description;
    private final String adresse;
    private final List<Avis> avis=new ArrayList<>();

    public Restaurant(String name, String description, String adresse) {
        this.name = name;
        this.description = description;
        this.adresse = adresse;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAdresse(){
        return adresse;
    }
    public List<Avis> getAvis() {
        return avis;
    }

    public void addAvis(Avis avis) {
        this.avis.add(avis);
    }

}
