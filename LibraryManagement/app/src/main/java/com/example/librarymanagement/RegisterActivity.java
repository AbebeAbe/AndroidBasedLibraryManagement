package com.example.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView btn=findViewById(R.id.btnRegister);
        TextView b=findViewById(R.id.btnlogin);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        databaseHelper = new DatabaseHelper(this);

        EditText usernameEditText = findViewById(R.id.inputUsername);
        EditText emailEditText = findViewById(R.id.inputEmail);
        EditText passwordEditText = findViewById(R.id.inputPassword);
        EditText confirmPasswordEditText = findViewById(R.id.inputConformPassword);
        Button registerButton = findViewById(R.id.btnRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if(username.length()==0 || email.length()==0 || password.length()==0){
                    Toast.makeText(getApplicationContext(),"Please fll the details ",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.compareTo(confirmPassword)==0){
                        long result = databaseHelper.insertUser(username, email, password);

                        if (result != -1) {
                            Toast.makeText(getApplicationContext(),"Registration successful ",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                        }

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"password and confirm password do not match",Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });
    }
}
