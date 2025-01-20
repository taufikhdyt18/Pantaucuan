package com.example.pantaucuan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String USERNAME = "user";
    private static final String PASSWORD = "666";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        EditText textUser = findViewById(R.id.etUserName);
        EditText textPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnlogin);
        btnLogin.setBackgroundColor(getResources().getColor(R.color.primary));

        btnLogin.setOnClickListener(v -> {
            String enteredUsername = textUser.getText().toString();
            String enteredPassword = textPassword.getText().toString();

            if (validateLogin(enteredUsername, enteredPassword)) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Username atau Password Salah", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        return USERNAME.equals(username) && PASSWORD.equals(password);
    }
}
