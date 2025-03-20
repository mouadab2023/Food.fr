package com.example.food;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Comparator;
import java.util.List;

import adapter.PlaceAdapter;
import model.NearbyRestaurantFetcher;
import model.api.response.PlaceResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Utils;

public class RestaurantListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static Location coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restaurant_list);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLastLocation();
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (coordinates != null) {
            callNearbyRestaurantsApi();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Permission denied. Cannot access location.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                coordinates = location;
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
            } else {
                coordinates = new Location("default");
                coordinates.setLatitude(43.600000);
                coordinates.setLongitude(1.433333);
            }
            callNearbyRestaurantsApi();
        }).addOnFailureListener(e -> Log.e("LOCATION_ERROR", "Failed to get location", e));
    }

    public void callNearbyRestaurantsApi() {
        NearbyRestaurantFetcher fetcher = new NearbyRestaurantFetcher();

        double latitude = coordinates.getLatitude();
        double longitude = coordinates.getLongitude();
        double radius = 1500;
        String includedType = "restaurant";
        int maxResultCount = 10;

        fetcher.searchNearbyRestaurants(latitude, longitude, radius, includedType, maxResultCount)
                .enqueue(new Callback<PlaceResponse>() {
                    @Override
                    public void onFailure(@NonNull Call<PlaceResponse> call, @NonNull Throwable t) {
                        Log.e("API_ERROR", "Request failed: " + t.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call<PlaceResponse> call, @NonNull Response<PlaceResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getPlaces() != null) {
                            List<PlaceResponse.Place> places = response.body().getPlaces();
                            if (!places.isEmpty()) {
                                places.sort(Comparator.comparingDouble(RestaurantListActivity.this::calculateDistance));
                                PlaceAdapter placeAdapter = new PlaceAdapter(RestaurantListActivity.this, places);
                                recyclerView.setAdapter(placeAdapter);
                            } else {
                                Toast.makeText(RestaurantListActivity.this, "No restaurants found.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("API_ERROR", "Request failed with code: " + response.code());
                        }
                    }
                });
    }

    private double calculateDistance(PlaceResponse.Place place) {
        return Utils.haversine(
                coordinates.getLatitude(),
                coordinates.getLongitude(),
                place.getLocation().getLatitude(),
                place.getLocation().getLongitude()
        );
    }
}
