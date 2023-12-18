package com.example.student;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText first_name_input, last_name_input, phone_number_input, birth_day_input;
    String student_id, student_name, phone_number, birth_day;

    Button update_button, delete_button, back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        first_name_input = findViewById(R.id.first_name2);
        last_name_input = findViewById(R.id.last_name2);
        phone_number_input = findViewById(R.id.phone_number2);
        birth_day_input = findViewById(R.id.birth_day2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        back_button = findViewById(R.id.back_button);
        getAndSetIntentData();
        update_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String first_name_update = first_name_input.getText().toString().trim();
                String last_name_update = last_name_input.getText().toString().trim();
                String birth_day_update = birth_day_input.getText().toString().trim();
                String phone_number_update = phone_number_input.getText().toString().trim();
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.updateData(student_id, first_name_update, last_name_update, birth_day_update,
                                            phone_number_update);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the main activity
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("student_id") && getIntent().hasExtra("student_name") &&
                getIntent().hasExtra("phone_number") && getIntent().hasExtra("birth_day")) {
            // Getting Data
            student_id = getIntent().getStringExtra("student_id");
            student_name = getIntent().getStringExtra("student_name");
            phone_number = getIntent().getStringExtra("phone_number");
            birth_day = getIntent().getStringExtra("birth_day");
            update_button = findViewById(R.id.update_button);

            String[] parts = student_name.split(" ");

            // Extract the first and last names
            String firstName = parts[0];
            String lastName = parts[1];

            // Setting Data
            first_name_input.setText(firstName);
            last_name_input.setText(lastName);
            phone_number_input.setText(phone_number);
            birth_day_input.setText(birth_day);

        } else {
            Toast.makeText(UpdateActivity.this, "No data!", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + student_name +" ?");
        builder.setMessage("Are you sure you want to delete: " + student_name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(student_id);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}