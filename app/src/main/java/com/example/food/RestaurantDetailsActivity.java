package com.example.food;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;
import FragementAdapter.RestaurantPagerAdapter;
import data.Restaurant;

public class RestaurantDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_details);

        ImageView imageView = findViewById(R.id.restaurantImageView);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        Intent intent = getIntent();
        Restaurant restaurant = (Restaurant) intent.getSerializableExtra("restaurant");
        String imageUrl = intent.getStringExtra("image_url");

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(imageView);
        }

        final var adapter = new RestaurantPagerAdapter(this, restaurant);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(adapter.getPageTitle(position))
        ).attach();
    }
}
