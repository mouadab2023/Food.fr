package com.example.food;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddReviewActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 101;

    private EditText username, description;
    private RatingBar rating;
    private Button submitButton, captureImageButton;
    private ImageView reviewImageView;
    private Uri imageUri = null;
    private File imageFile;  // ✅ Declaring imageFile

    private FirebaseFirestore db;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        username = findViewById(R.id.reviewUsername);
        description = findViewById(R.id.reviewDescription);
        rating = findViewById(R.id.reviewRating);
        submitButton = findViewById(R.id.submitReview);
        captureImageButton = findViewById(R.id.captureImage);
        reviewImageView = findViewById(R.id.reviewImageView);

        captureImageButton.setOnClickListener(view -> openCamera());

        submitButton.setOnClickListener(view -> {
            submitButton.setEnabled(false); // 🔥 Disable button to prevent multiple clicks

            if (imageUri != null) {
                uploadImageAndSaveReview();
            } else {
                saveReview(null);
            }
        });
    }

    private void openCamera() {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            imageFile = File.createTempFile(
                    "review_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()),
                    ".jpg",
                    storageDir
            );
            imageUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", imageFile);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } catch (IOException e) {
            Toast.makeText(this, "Error opening camera", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (imageFile.exists()) {
                reviewImageView.setImageURI(Uri.fromFile(imageFile));
                reviewImageView.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Image file does not exist!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImageAndSaveReview() {
        if (imageFile == null || !imageFile.exists()) {
            Toast.makeText(this, "No image file found!", Toast.LENGTH_SHORT).show();
            submitButton.setEnabled(true); // ✅ Re-enable button if upload fails
            return;
        }

        Uri fileUri = Uri.fromFile(imageFile);
        StorageReference storageRef = storage.getReference().child("reviews/" + imageFile.getName());

        storageRef.putFile(fileUri)
                .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    saveReview(uri.toString());
                }))
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Image upload failed! " + e.getMessage(), Toast.LENGTH_LONG).show();
                    submitButton.setEnabled(true); // ✅ Re-enable button if upload fails
                });
    }

    private void saveReview(String imageUrl) {
        String restaurantName = getIntent().getStringExtra("restaurant_name");

        if (restaurantName == null || restaurantName.isEmpty()) {
            Toast.makeText(this, "Error: Restaurant name is missing!", Toast.LENGTH_SHORT).show();
            submitButton.setEnabled(true); // ✅ Re-enable button if error
            return;
        }

        Map<String, Object> review = new HashMap<>();
        review.put("username", username.getText().toString());
        review.put("description", description.getText().toString());
        review.put("note", (int) rating.getRating());
        review.put("restaurant", restaurantName);
        review.put("picture", imageUrl);

        db.collection("Reviews").add(review)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Review added!", Toast.LENGTH_SHORT).show();
                    finish();  // ✅ Close activity after successful submission
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to add review!", Toast.LENGTH_SHORT).show();
                    submitButton.setEnabled(true); // ✅ Re-enable button if Firestore fails
                });
    }
}
