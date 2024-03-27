package com.example.examapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView email = findViewById(R.id.txtemail);
        TextView phone = findViewById(R.id.txtphno);

        email.setText("xyz@abc.com");
        phone.setText("1029384756");

        Linkify.addLinks(email, Linkify.EMAIL_ADDRESSES);
        Linkify.addLinks(phone, Linkify.PHONE_NUMBERS);

        Toolbar mytoolbar = findViewById(R.id.home_menu);
        if (mytoolbar != null) {
            setSupportActionBar(mytoolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m1 = getMenuInflater();
        m1.inflate(R.menu.menu, menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            startActivity(new Intent(AboutActivity.this, HomeActivity.class));
        } else if (id == R.id.item2) {
            startActivity(new Intent(AboutActivity.this, AboutActivity.class));
        } else if (id == R.id.item3) {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Confirm logout !");
            ad.setMessage("You will be logout...");

            ad.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loggedIn", false);
                    editor.apply();

                    Intent intent = new Intent(AboutActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {}
            });
            ad.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {}
            });
            ad.create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}