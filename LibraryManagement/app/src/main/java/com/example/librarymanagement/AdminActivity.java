package com.example.librarymanagement;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class AdminActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        databaseHelper = new DatabaseHelper(this);

        EditText titleEditText = findViewById(R.id.title);
        EditText authorEditText = findViewById(R.id.author);
        EditText quantityEditText = findViewById(R.id.code);
        Button addButton = findViewById(R.id.btnadd);
        Button log1 = findViewById(R.id.btnlogout);
        log1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,MainActivity.class));
            }
        });


        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String title1 = titleEditText.getText().toString();
                String author1 = authorEditText.getText().toString();
                int code1 = Integer.parseInt(quantityEditText.getText().toString());

                long result = databaseHelper.insertBook(title1, author1, code1);

                if (result != -1) {
                    Toast.makeText(getApplicationContext(),"Book added successfully ",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(),"Book added successfully ",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}