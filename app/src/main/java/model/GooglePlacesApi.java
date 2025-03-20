package model;
import model.api.call.NearbySearchRequest;
import model.api.response.PlaceResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface GooglePlacesApi {

    //TODO remove api key from repo before making project public
    @Headers({
            "Content-Type: application/json",
            "X-Goog-Api-Key: ***REMOVED***",
            "X-Goog-FieldMask: places.displayName,places.businessStatus,places.formattedAddress,places.photos.name,places.nationalPhoneNumber,places.rating,places.primaryType,places.location,places.editorialSummary"
    })
    @POST("v1/places:searchNearby")
    Call<PlaceResponse> searchNearbyPlaces(@Body NearbySearchRequest request);
}
