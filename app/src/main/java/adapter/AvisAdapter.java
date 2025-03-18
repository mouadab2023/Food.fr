package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.food.R;
import com.squareup.picasso.Picasso;
import java.util.List;
import data.Avis;

public class AvisAdapter extends RecyclerView.Adapter<AvisAdapter.AvisViewHolder> {
    private List<Avis> avisList;

    public AvisAdapter(List<Avis> avisList) {
        this.avisList = avisList;
    }

    @NonNull
    @Override
    public AvisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new AvisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvisViewHolder holder, int position) {
        Avis avis = avisList.get(position);
        holder.username.setText(avis.getUsername());
        holder.description.setText(avis.getDescription());
        holder.note.setRating(avis.getNote());

        if (avis.getPicture() != null && !avis.getPicture().isEmpty()) {
            Picasso.get().load(avis.getPicture()).into(holder.picture);
        }
    }

    @Override
    public int getItemCount() {
        return avisList.size();
    }

    static class AvisViewHolder extends RecyclerView.ViewHolder {
        TextView username, description;
        RatingBar note;
        ImageView picture;

        public AvisViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.reviewUsername);
            description = itemView.findViewById(R.id.reviewDescription);
            note = itemView.findViewById(R.id.reviewRating);
            picture = itemView.findViewById(R.id.reviewImage);
        }
    }
}
