package com.example.task81c;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin, buttonSignup;
    private SQLClass sqlClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignup = findViewById(R.id.buttonSignup);

        // Initialize SQLClass
        sqlClass = new SQLClass(this);

        buttonLogin.setOnClickListener(v -> {
            // Retrieve entered username and password
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Validate username and password
            boolean isValid = sqlClass.validateLogin(username, password);

            if (isValid) {
                // Login successful
                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                // Proceed with the desired action, e.g., open YoutubeActivity
                openYoutubeActivity();
            } else {
                // Login failed
                Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSignup.setOnClickListener(v -> {
            // Open the SignupActivity when the signup button is clicked
            openSignupActivity();
        });
    }

    private void openSignupActivity() {
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    private void openYoutubeActivity() {
        Intent intent = new Intent(MainActivity.this, YoutubeActivity.class);
        startActivity(intent);
    }
}
