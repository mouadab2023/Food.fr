package FragementAdapter;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.food.R;

import data.Restaurant;

public class ReservationFragment extends Fragment {

    private static final String ARG_RESTAURANT = "restaurant";

    public static ReservationFragment newInstance(Restaurant restaurant) {
        final var fragment = new ReservationFragment();
        final var args = new Bundle();
        args.putSerializable(ARG_RESTAURANT, restaurant);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        TextView infoTextView = view.findViewById(R.id.ReservationTextView);

        if (getArguments() != null) {
            final var restaurant = (Restaurant) getArguments().getSerializable(ARG_RESTAURANT);
            infoTextView.setText(restaurant.getDescription());
        }

        return view;
    }
}

