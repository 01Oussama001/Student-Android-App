package com.example.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    EditText editTextFirstName, editTextLastName;
    Button searchButton, backButton;
    RecyclerView searchRecyclerView;

    MyDatabaseHelper myDB;
    CustomAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        searchButton = findViewById(R.id.searchButton);
        backButton = findViewById(R.id.backButton);
        searchRecyclerView = findViewById(R.id.searchRecyclerView);

        myDB = new MyDatabaseHelper(SearchActivity.this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editTextFirstName.getText().toString().trim();
                String lastName = editTextLastName.getText().toString().trim();

                // Perform search based on first name and last name
                Cursor searchResults = myDB.searchStudents(firstName, lastName);
                searchStudents(firstName, lastName);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the main activity
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void searchStudents(String firstName, String lastName) {
        ArrayList<String> student_id = new ArrayList<>();
        ArrayList<String> student_name = new ArrayList<>();
        ArrayList<String> phone_number = new ArrayList<>();
        ArrayList<String> birth_day = new ArrayList<>();

        Cursor cursor = myDB.searchStudents(firstName, lastName); // Implement this method in MyDatabaseHelper

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                student_id.add(cursor.getString(0));
                String fullName = cursor.getString(1) + " " + cursor.getString(2);
                student_name.add(fullName);
                birth_day.add(cursor.getString(3));
                phone_number.add(cursor.getString(4));
            }
        } else {
            // Handle case where no results are found
            Toast.makeText(this, "No matching students found", Toast.LENGTH_SHORT).show();
        }

        // Set up RecyclerView with search results
        searchAdapter = new CustomAdapter(SearchActivity.this, SearchActivity.this,
                student_id, student_name, phone_number, birth_day);
        searchRecyclerView.setAdapter(searchAdapter);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
    }
}