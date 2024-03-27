package com.example.examapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText user, pass;
    Button log;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        user = findViewById(R.id.etUsername);
        pass = findViewById(R.id.etPassword);
        log = findViewById(R.id.button);

        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
    }

    public void login(View view) {
        String User = user.getText().toString();
        String Pass = pass.getText().toString();

        if (User.equals("admin") && Pass.equals("password")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("loggedIn", true);
            editor.apply();

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("username", User);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}