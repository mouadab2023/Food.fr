package com.example.food;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import model.NearbyRestaurantFetcher;

public class MainActivity extends AppCompatActivity {
    Button button;
    private final View.OnClickListener listner= view -> {
        Intent intent=new Intent(MainActivity.this,RestaurantListActivity.class);
        startActivity(intent);
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.welcome_page);
        button=findViewById(R.id.loginButton);
        button.setOnClickListener(listner);
    }


}