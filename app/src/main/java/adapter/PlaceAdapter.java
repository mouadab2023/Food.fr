package adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.R;
import com.example.food.RestaurantListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.api.response.PlaceResponse;
import utils.Utils;


public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private List<PlaceResponse.Place> places;

    public PlaceAdapter(List<PlaceResponse.Place> places) {
        this.places = places;
    }

    // Method to set the list of places and notify the adapter
    @SuppressLint("NotifyDataSetChanged")
    public void setPlaces(List<PlaceResponse.Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each item (restaurant/ place)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        // Bind the data from the placeList to the views in each item
        PlaceResponse.Place place = places.get(position);
        holder.nameText.setText(place.getDisplayName().getText());
        holder.typeText.setText(place.getPrimaryType().replace('_', ' '));
        double distance = Utils.haversine(
                RestaurantListActivity.coordinates.getLatitude(),
                RestaurantListActivity.coordinates.getLongitude(),
                place.getLocation().getLatitude(),
                place.getLocation().getLongitude()
        );
        holder.distanceText.setText(String.format("%.2f", distance) + " km");
        String photoUri = Utils.getPhoto(place.getPhotos(), place.getDisplayName().getText());
        if (!photoUri.isEmpty())
            Picasso.get().load(photoUri).resize(200, 100).into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return places != null ? places.size() : 0;  // Return the number of items in the list
    }
    // ViewHolder class to hold the references to the views in each item
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

