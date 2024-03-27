package com.example.examapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.examapplication.handler.DBHandler;
import com.example.examapplication.model.Country;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    DBHandler db;
    EditText et_id, et_name, et_desc;
    ListView lst_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String user = getIntent().getStringExtra("username");
        TextView txt = findViewById(R.id.textView);
        txt.setText("Welcome, " + user + "!");

        Toolbar mytoolbar = findViewById(R.id.toolbar);
        if (mytoolbar != null) {
            setSupportActionBar(mytoolbar);
        }
        db = new DBHandler(this);

        et_id = findViewById(R.id.et_id);
        et_name = findViewById(R.id.et_name);
        et_desc = findViewById(R.id.et_desc);
        lst_show = findViewById(R.id.lst_show);
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
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
        } else if (id == R.id.item2) {
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
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

                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
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
    public void Insert(View view){
        String name = et_name.getText().toString();
        long desc = Long.parseLong(et_desc.getText().toString());

        Country country = new Country();
        country.setCountryName(name);
        country.setPopulation(Long.parseLong(String.valueOf(desc)));

        if (db.Insert_Records(country)){
            Toast.makeText(this, "Inserted Successfully!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Some Error Occurred!", Toast.LENGTH_SHORT).show();
        }
        Read();
    }
    public void Read(){
        ArrayList<String> data = db.Read_Records();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        lst_show.setAdapter(adapter);
    }
    public void Update(View view){
        String id = et_id.getText().toString();
        String name = et_name.getText().toString();
        long desc = Long.parseLong(et_desc.getText().toString());

        Country country = new Country();
        country.setId(Integer.parseInt(id));
        country.setCountryName(name);
        country.setPopulation(Long.parseLong(String.valueOf(desc)));

        if (db.Update_Record(country)){
            Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Some Error Occurred!", Toast.LENGTH_SHORT).show();
        }
        Read();
    }

    public void Delete(View view){
        String id = et_id.getText().toString();

        if (db.Delete_Record(id)){
            Toast.makeText(this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Some Error Occurred!", Toast.LENGTH_SHORT).show();
        }
        Read();
    }
}

