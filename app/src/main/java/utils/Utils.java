package utils;


import java.util.List;

import model.api.response.PlaceResponse;

public class Utils {
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in kilometers
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in kilometers
    }
    public static String getPhoto(List<PlaceResponse.Place.Photo> photoList,String name){
        for (PlaceResponse.Place.Photo p :photoList) {
            for(PlaceResponse.Place.Photo.AuthorAttribution authorAttribution:p.getAuthorAttributions()){
                if(authorAttribution.getDisplayName().equals(name)){
                    return authorAttribution.getPhotoUri();
                }
            }
        }
        return "";
    }
}
