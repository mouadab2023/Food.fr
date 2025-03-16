package FragementAdapter;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import data.Restaurant;
import data.Avis;
import com.example.food.R;

import java.util.Locale;

public class InfoFragment extends Fragment {

    private static final String ARG_RESTAURANT = "restaurant";

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
        TextView ratingAverage = view.findViewById(R.id.ratingTextView);
        TextView reviewNumber = view.findViewById(R.id.reviewCountTextView);
        TextView adresse = view.findViewById(R.id.adresseRestaurant);

        if (getArguments() != null) {
            final var restaurant = (Restaurant) getArguments().getSerializable(ARG_RESTAURANT);
            titre.setText(restaurant.getName());
            description.setText(restaurant.getDescription());

            double averageRating = restaurant.getAvis().stream().mapToDouble(Avis::getNote).average().orElse(0.0);
            ratingAverage.setText(String.format(Locale.US, "%.1f", averageRating));

            reviewNumber.setText(String.format(Locale.US, "(%d)", restaurant.getAvis().size()));
            adresse.setText(String.format(Locale.US, "Adresse : %s", restaurant.getAdresse()));
        }

        return view;
    }
}
