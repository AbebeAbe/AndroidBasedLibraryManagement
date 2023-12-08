package com.example.librarymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private TextView booksTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        databaseHelper = new DatabaseHelper(this);
        booksTextView = findViewById(R.id.booksTextView);
        Button abe=findViewById(R.id.btnlogout);
        abe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this,MainActivity.class));
            }
        });



        Button viewBooksButton = findViewById(R.id.btnsee);
        viewBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAvailableBooks();
            }
        });
    }

    private void displayAvailableBooks() {
        List<Book> availableBooks = databaseHelper.getAvailableBooks();

        StringBuilder booksText = new StringBuilder("Available Books:\n");
        for (Book book : availableBooks) {
            booksText.append("Title: ").append(book.getTitle()).append("\n");
            booksText.append("Author: ").append(book.getAuthor()).append("\n");
            booksText.append("Quantity: ").append(book.getQuantity()).append("\n\n");
        }

        booksTextView.setText(booksText.toString());
    }
}