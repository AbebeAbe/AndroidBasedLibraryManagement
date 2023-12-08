package com.example.librarymanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_USERNAME + " TEXT," +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_PASSWORD + " TEXT" +
                    ")";
    private static final String TABLE_BOOKS = "books";
    private static final String COLUMN_BOOK_ID = "_id";
    private static final String COLUMN_BOOK_TITLE = "title";
    private static final String COLUMN_BOOK_AUTHOR = "author";
    private static final String COLUMN_BOOK_QUANTITY = "quantity";


    private static final String CREATE_TABLE_BOOKS =
            "CREATE TABLE " + TABLE_BOOKS + "(" +
                    COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_BOOK_TITLE + " TEXT," +
                    COLUMN_BOOK_AUTHOR + " TEXT," +
                    COLUMN_BOOK_QUANTITY + " INTEGER" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_BOOKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public long insertUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        return db.insert(TABLE_USERS, null, values);
    }
    //
    public long insertBook(String title, String author, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_TITLE, title);
        values.put(COLUMN_BOOK_AUTHOR, author);
        values.put(COLUMN_BOOK_QUANTITY, quantity);

        return db.insert(TABLE_BOOKS, null, values);
    }


    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_BOOK_ID, COLUMN_BOOK_TITLE, COLUMN_BOOK_AUTHOR, COLUMN_BOOK_QUANTITY};
        String selection = COLUMN_BOOK_QUANTITY + " > 0"; // Books with quantity greater than 0 are available

        try (Cursor cursor = db.query(TABLE_BOOKS, columns, selection, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idIndex = cursor.getColumnIndex(COLUMN_BOOK_ID);
                    int titleIndex = cursor.getColumnIndex(COLUMN_BOOK_TITLE);
                    int authorIndex = cursor.getColumnIndex(COLUMN_BOOK_AUTHOR);
                    int quantityIndex = cursor.getColumnIndex(COLUMN_BOOK_QUANTITY);

                    // Log column indices for debugging
                    Log.d("DatabaseHelper", "ID Index: " + idIndex);
                    Log.d("DatabaseHelper", "Title Index: " + titleIndex);
                    Log.d("DatabaseHelper", "Author Index: " + authorIndex);
                    Log.d("DatabaseHelper", "Quantity Index: " + quantityIndex);

                    Book book = new Book();
                    book.setId(cursor.getInt(idIndex));
                    book.setTitle(cursor.getString(titleIndex));
                    book.setAuthor(cursor.getString(authorIndex));
                    book.setQuantity(cursor.getInt(quantityIndex));
                    availableBooks.add(book);
                } while (cursor.moveToNext());
                cursor.close();


            }
        } catch (Exception e) {

            Log.e("DatabaseHelper", "Error querying available books: " + e.getMessage());
        } finally {

            db.close();
        }

        return availableBooks;
    }




    public boolean checkUser(String username, String password) {
        String[] columns = {COLUMN_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();


        return count > 0;
    }

}
