package model.api.response;

import java.util.List;

public class PlaceResponse {
    List<Place> places;
    public List<Place> getPlaces() {
        return places;
    }
    public static class Place {
        public DisplayName getDisplayName() {
            return displayName;
        }
        public String getBusinessStatus() {
            return businessStatus;
        }
        public String getFormattedAddress() {
            return formattedAddress;
        }
        public List<Photo> getPhotos() {
            return photos;
        }
        public String getNationalPhoneNumber() {
            return nationalPhoneNumber;
        }
        public double getRating() {
            return rating;
        }
        public String getPrimaryType() {
            return primaryType;
        }
        public Location getLocation() {
            return location;
        }
        DisplayName displayName;
        String businessStatus;
        String formattedAddress;
        List<Photo> photos;
        String nationalPhoneNumber;
        double rating;
        String primaryType;
        Location location;
        public static class DisplayName {
            private String text;
            public String getText() {
                return text;
            }
        }
        public static class Photo {
            String name;
            private List<AuthorAttribution> authorAttributions;
            public String getName() {
                return name;
            }
            public List<AuthorAttribution> getAuthorAttributions() {
                return authorAttributions;
            }
            public static class AuthorAttribution {
                private String displayName;
                private String uri;
                private String photoUri;
                public String getDisplayName() {
                    return displayName;
                }
                public String getUri() {
                    return uri;
                }
                public String getPhotoUri() {
                    return photoUri;
                }
            }
        }
        public static class Location {
            private double latitude;
            private double longitude;
            public double getLatitude() {
                return latitude;
            }
            public double getLongitude() {
                return longitude;
            }

        }
    }

}