package utils;


import android.widget.ImageView;

import com.example.food.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class Utils {
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    public static String getPhoto(String photoName) {
        if (photoName != null && !photoName.isEmpty()) {
            return "https://places.googleapis.com/v1/" + photoName +
                    "/media?maxWidthPx=1200&maxHeightPx=800&key=***REMOVED***";
        }
        return "";
    }




    public static void loadImage(ImageView imageView, String url) {
        if (url == null || url.isEmpty()) {
            imageView.setImageResource(R.drawable.poke_bowl );
            return;
        }

        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .override(600, 400)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.poke_bowl))
                .into(imageView);
    }
}
