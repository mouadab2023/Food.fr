package model;
import model.api.call.NearbySearchRequest;
import model.api.response.PlaceResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface GooglePlacesApi {
    @Headers({
            "Content-Type: application/json",
           "X-Goog-Api-Key:" + "***REMOVED***CI-***REMOVED***",
            "X-Goog-FieldMask: places.displayName,places.businessStatus,places.formattedAddress,places.photos,places.nationalPhoneNumber,places.rating,places.primaryType,places.location"
    })
    @POST("v1/places:searchNearby")
    Call<PlaceResponse> searchNearbyPlaces(@Body NearbySearchRequest request);
}
