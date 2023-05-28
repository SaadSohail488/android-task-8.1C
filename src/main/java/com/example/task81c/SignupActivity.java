package com.example.task81c;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    private EditText editTextFullName, editTextUsername, editTextPassword, editTextConfirmPassword;
    private Button buttonSignup;
    private SQLClass sqlClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        // Initialize views
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSignup = findViewById(R.id.buttonSignup);

        // Initialize SQLClass
        sqlClass = new SQLClass(this);

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve signup details
                String fullName = editTextFullName.getText().toString().trim();
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();

                // Perform validation (e.g., check for empty fields, password match)
                if (password.equals(confirmPassword)) {
                    // Passwords match, proceed with signup
                    boolean isSignupSuccessful = sqlClass.signupUser(fullName, username, password);

                    if (isSignupSuccessful) {
                        // Signup successful, display a success message
                        Toast.makeText(SignupActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                        // Redirect to the login page (MainActivity)
                        redirectToLogin();
                    } else {
                        // Signup failed, display an error message
                        Toast.makeText(SignupActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Passwords do not match, display an error message
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to redirect to the login page (MainActivity)
    private void redirectToLogin() {
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish the SignupActivity so that the user cannot navigate back to it
    }
}
