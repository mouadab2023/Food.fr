package model;






import java.util.List;

import model.api.call.NearbySearchRequest;
import model.api.response.PlaceResponse;
import retrofit2.Call;


public class NearbyRestaurantFetcher {
        private final GooglePlacesApi api;

        public NearbyRestaurantFetcher() {
            this.api = RetrofitClient.getApi();
        }

        public Call<PlaceResponse> searchNearbyRestaurants(double latitude, double longitude, double radius, String includedType, int maxResultCount) {
            NearbySearchRequest request = new NearbySearchRequest();
            request.includedTypes = List.of(includedType);
            request.maxResultCount = maxResultCount;
            NearbySearchRequest.LocationRestriction locationRestriction = new NearbySearchRequest.LocationRestriction();
            NearbySearchRequest.LocationRestriction.Circle circle = new NearbySearchRequest.LocationRestriction.Circle();
            NearbySearchRequest.LocationRestriction.Location center = new NearbySearchRequest.LocationRestriction.Location();
            center.latitude = latitude;
            center.longitude = longitude;
            circle.center = center;
            circle.radius = radius;
            locationRestriction.circle = circle;
            request.locationRestriction = locationRestriction;

            System.out.println(request);

            return api.searchNearbyPlaces(request);
        }
    }
