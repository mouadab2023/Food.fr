package FragementAdapter;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.food.AddReviewActivity;
import com.example.food.R;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.List;

import adapter.AvisAdapter;
import data.Avis;
import data.Restaurant;

public class ReviewsFragment extends Fragment {

    private static final String ARG_RESTAURANT = "restaurant";
    private RecyclerView reviewsRecyclerView;
    private AvisAdapter avisAdapter;
    private List<Avis> avisList = new ArrayList<>();
    private FirebaseFirestore db;
    private Button addReviewButton;

    public static ReviewsFragment newInstance(Restaurant restaurant) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESTAURANT, restaurant);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        db = FirebaseFirestore.getInstance();
        reviewsRecyclerView = view.findViewById(R.id.reviewsRecyclerView);
        addReviewButton = view.findViewById(R.id.addReviewButton);

        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        avisAdapter = new AvisAdapter(avisList);
        reviewsRecyclerView.setAdapter(avisAdapter);

        if (getArguments() != null) {
            Restaurant restaurant = (Restaurant) getArguments().getSerializable(ARG_RESTAURANT);
            loadAvis(restaurant.getName());

            addReviewButton.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AddReviewActivity.class);
                intent.putExtra("restaurant_name", restaurant.getName());
                startActivity(intent);
            });
        } else {
            Toast.makeText(getContext(), "Error: Restaurant data is missing!", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void loadAvis(String restaurantName) {
        db.collection("Reviews")
                .whereEqualTo("restaurant", restaurantName)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(getContext(), "Error loading reviews", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    avisList.clear();
                    for (DocumentSnapshot doc : value.getDocuments()) {
                        avisList.add(doc.toObject(Avis.class));
                    }
                    avisAdapter.notifyDataSetChanged();
                });
    }
}
