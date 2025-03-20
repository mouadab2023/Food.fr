package FragementAdapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.food.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import data.Restaurant;
import java.util.Locale;

public class InfoFragment extends Fragment {

    private static final String ARG_RESTAURANT = "restaurant";
    private TextView ratingAverage, reviewNumber;
    private FirebaseFirestore db;

    public static InfoFragment newInstance(Restaurant restaurant) {
        final var fragment = new InfoFragment();
        final var args = new Bundle();
        args.putSerializable(ARG_RESTAURANT, restaurant);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        TextView titre = view.findViewById(R.id.restaurantNameTextView);
        TextView description = view.findViewById(R.id.descriptionTextView);
        ratingAverage = view.findViewById(R.id.ratingTextView);
        reviewNumber = view.findViewById(R.id.reviewCountTextView);
        TextView adresse = view.findViewById(R.id.adresseRestaurant);

        db = FirebaseFirestore.getInstance();

        if (getArguments() != null) {
            final var restaurant = (Restaurant) getArguments().getSerializable(ARG_RESTAURANT);
            titre.setText(restaurant.getName());
            description.setText(restaurant.getDescription());
            adresse.setText(String.format(Locale.US, "Adresse : %s", restaurant.getAdresse()));

            listenForRatingUpdates(restaurant.getName());
        }

        return view;
    }

    private void listenForRatingUpdates(String restaurantName) {
        db.collection("Reviews")
                .whereEqualTo("restaurant", restaurantName)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e("FIRESTORE", "Failed to listen for updates", e);
                            return;
                        }

                        if (queryDocumentSnapshots != null) {
                            int totalReviews = queryDocumentSnapshots.size();
                            float sumRatings = 0;

                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                Long rating = doc.getLong("note");
                                if (rating != null) {
                                    sumRatings += rating;
                                }
                            }

                            float avgRating = totalReviews > 0 ? sumRatings / totalReviews : 0;

                            ratingAverage.setText(String.format(Locale.US, "%.1f", avgRating));
                            reviewNumber.setText(String.format(Locale.US, "(%d avis)", totalReviews));
                        }
                    }
                });
    }
}
