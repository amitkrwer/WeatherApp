package com.example.root.weatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class City extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Button button = findViewById(R.id.button);
        editText = findViewById(R.id.edittext);
        button.setOnClickListener(new View.OnClickListener() {
            private String getString;

            @Override
            public void onClick(View view) {
                getString = editText.getText().toString();
                if (getString.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter city name", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("name", getString);
                    Toast.makeText(getApplicationContext(), getString, Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }
        });

    }
}
