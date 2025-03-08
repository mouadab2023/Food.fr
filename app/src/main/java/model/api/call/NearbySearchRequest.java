package model.api.call;

import java.util.List;

public class NearbySearchRequest {
    public List<String> includedTypes;
    public int maxResultCount;
    public LocationRestriction locationRestriction;

    static public class LocationRestriction {
        public Circle circle;

        public static class Circle {
            public Location center;
            public double radius;
        }

        public static class Location {
            public double latitude;
            public double longitude;
        }
    }
}