package adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.R;

import java.util.List;

import data.Reservation;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private List<Reservation> reservationList;

    public ReservationAdapter(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);
        holder.nomTextView.setText("Nom: " + reservation.getNom());
        holder.restaurantTextView.setText("Restaurant: " + reservation.getRestaurant());
        holder.dateTextView.setText("Date: " + reservation.getDate());
        holder.nombrePersonneTextView.setText("Personnes: " + reservation.getNombrePersonne());
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomTextView, restaurantTextView, dateTextView, nombrePersonneTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomTextView = itemView.findViewById(R.id.nomTextView);
            restaurantTextView = itemView.findViewById(R.id.restaurantTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            nombrePersonneTextView = itemView.findViewById(R.id.nombrePersonneTextView);
        }
    }
}
