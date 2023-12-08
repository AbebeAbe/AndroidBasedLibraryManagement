package com.example.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView btn=findViewById(R.id.textViewSignUp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        databaseHelper = new DatabaseHelper(this);

        EditText username1 = findViewById(R.id.inputPassword1);
        EditText passwordEditText = findViewById(R.id.inputPassword);
        Button loginButton = findViewById(R.id.btnlogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username1.getText().toString();
                String password = passwordEditText.getText().toString();
                if(username.length()==0 || password.length()==0) {
                    Toast.makeText(getApplicationContext(),"please fill either username or password",Toast.LENGTH_SHORT).show();

                }
                else{
                    if (databaseHelper.checkUser(username, password)) {
                        Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_SHORT).show();
                        if(username.equals("admin") ){

                            startActivity(new Intent(LoginActivity.this,AdminActivity.class));
                        }
                        else{
                            startActivity(new Intent(LoginActivity.this,UserActivity.class));

                        }

                        // You can add further actions or navigate to another screen
                    } else {
                        Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT).show();

                        // Handle the error
                    }

                }


            }
        });
    }
}
