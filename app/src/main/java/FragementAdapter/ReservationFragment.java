package FragementAdapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.food.R;
import com.example.food.ReservationActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import data.Restaurant;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReservationFragment extends Fragment {

    private static final String ARG_RESTAURANT = "restaurant";

    private EditText etSelectedDate;
    private EditText etSelectedTime;
    private EditText etNumberOfPeople;
    private EditText etName;
    private Button btnReserve;

    private FirebaseFirestore db;

    public static ReservationFragment newInstance(Restaurant restaurant) {
        final var fragment = new ReservationFragment();
        final var args = new Bundle();
        args.putSerializable(ARG_RESTAURANT, restaurant);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        etSelectedDate = view.findViewById(R.id.et_selected_date);
        etSelectedTime = view.findViewById(R.id.et_selected_time);
        etNumberOfPeople = view.findViewById(R.id.et_person_count);
        etName = view.findViewById(R.id.et_name);
        btnReserve = view.findViewById(R.id.btn_submit);

        db = FirebaseFirestore.getInstance();

        etSelectedDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view1, year1, month1, dayOfMonth) -> {
                        etSelectedDate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        etSelectedTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getContext(),
                    (view12, hourOfDay, minute1) -> {
                        etSelectedTime.setText(hourOfDay + ":" + (minute1 < 10 ? "0" + minute1 : minute1));
                    },
                    hour, minute, true);
            timePickerDialog.show();
        });

        btnReserve.setOnClickListener(v -> {
            String date = etSelectedDate.getText().toString();
            String time = etSelectedTime.getText().toString();
            String numberOfPeople = etNumberOfPeople.getText().toString();
            String name = etName.getText().toString();

            if (date.isEmpty() || time.isEmpty() || numberOfPeople.isEmpty() || name.isEmpty()) {
                Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            assert getArguments() != null;
            Restaurant restaurant = (Restaurant) getArguments().getSerializable(ARG_RESTAURANT);
            String restaurantName = (restaurant != null) ? restaurant.getName() : "Nom du Restaurant";



            Map<String, Object> reservation = new HashMap<>();
            reservation.put("Date", date + " " + time);
            reservation.put("Nom", name);
            reservation.put("Restaurant", restaurantName);
            reservation.put("Nombre", Integer.parseInt(numberOfPeople));

            db.collection("Reservation")
                    .add(reservation)
                    .addOnSuccessListener(documentReference -> {

                        Toast.makeText(getContext(), "Réservation réussie!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(requireContext(), ReservationActivity.class);
                        startActivity(intent);

                        requireActivity().finish();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("ReservationError", "Erreur lors de la réservation", e);
                        Toast.makeText(getContext(), "Erreur lors de la réservation", Toast.LENGTH_SHORT).show();
                    });
        });

        return view;
    }
}
