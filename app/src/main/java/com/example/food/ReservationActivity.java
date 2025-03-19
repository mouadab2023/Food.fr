package com.example.food;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

import adapter.ReservationAdapter;
import data.Reservation;

public class ReservationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationAdapter adapter;
    private List<Reservation> reservationList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reservationList = new ArrayList<>();
        adapter = new ReservationAdapter(reservationList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadReservations();
    }

    private void loadReservations() {
        db.collection("Reservation")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        reservationList.clear();
                        for (DocumentSnapshot document : task.getResult()) {
                            // Afficher les données récupérées pour déboguer
                            Log.d("Firestore", "Document ID: " + document.getId());
                            Log.d("Firestore", "Data: " + document.getData());

                            Reservation reservation = document.toObject(Reservation.class);

                            if (reservation != null) {
                                Log.d("Firestore", "Reservation: " + reservation.getNom() + ", " + reservation.getDate());
                            } else {
                                Log.d("Firestore", "Reservation is null");
                            }

                            reservationList.add(reservation);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("Firestore", "Erreur de chargement des réservations", task.getException());
                    }
                });
    }

}

