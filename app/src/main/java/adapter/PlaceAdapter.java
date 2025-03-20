package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.R;
import com.example.food.RestaurantDetailsActivity;
import com.example.food.RestaurantListActivity;

import java.util.List;
import java.util.Locale;

import data.Restaurant;
import model.api.response.PlaceResponse;
import utils.Utils;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private final List<PlaceResponse.Place> places;
    private final Context context;

    public PlaceAdapter(Context context, List<PlaceResponse.Place> places) {
        this.context = context;
        this.places = places;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final var view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        PlaceResponse.Place place = places.get(position);

        holder.nameText.setText(place.getDisplayName().getText());
        holder.typeText.setText(place.getPrimaryType().replace('_', ' '));
        holder.distanceText.setText(calculateDistance(place));

        // Extract the photo name from the API response
        final var photoName = place.getPhotos() != null && !place.getPhotos().isEmpty()
                ? place.getPhotos().get(0).getName()
                : null;

        // Get High-Quality Photo URL
        final var photoUrl = Utils.getPhoto(photoName);

        // Load Image with Glide (better quality than Picasso)
        Utils.loadImage(holder.imageView, photoUrl);

        holder.itemView.setOnClickListener(v -> openRestaurantDetails(v, place, photoUrl));
    }


    private String calculateDistance(PlaceResponse.Place place) {
        double distance = Utils.haversine(
                RestaurantListActivity.coordinates.getLatitude(),
                RestaurantListActivity.coordinates.getLongitude(),
                place.getLocation().getLatitude(),
                place.getLocation().getLongitude()
        );
        return String.format(Locale.US, "%.2f km", distance);    }

    private void openRestaurantDetails(View v, PlaceResponse.Place place, String photoUrl) {
        final var intent = new Intent(v.getContext(), RestaurantDetailsActivity.class);

        final var restaurant = createRestaurantFromPlace(place);
        intent.putExtra("restaurant", restaurant);
        intent.putExtra("image_url", photoUrl);
        v.getContext().startActivity(intent);
    }

    private Restaurant createRestaurantFromPlace(PlaceResponse.Place place) {
        final var description = place.getEditorialSummary() != null ?
                place.getEditorialSummary().getText() :
                context.getString(R.string.no_description_available);

        return new Restaurant(
                place.getDisplayName().getText(),
                description,
                place.getFormattedAddress()
        );
    }

    @Override
    public int getItemCount() {
        return places != null ? places.size() : 0;
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView distanceText;
        TextView nameText;
        TextView typeText;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            distanceText = itemView.findViewById(R.id.distanceText);
            nameText = itemView.findViewById(R.id.nameText);
            typeText = itemView.findViewById(R.id.typeText);
        }
    }
}

