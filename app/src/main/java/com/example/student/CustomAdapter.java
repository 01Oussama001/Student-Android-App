package com.example.student;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList student_id, student_name, phone_number, birth_day;

    CustomAdapter(Activity activity, Context context, ArrayList student_id,
                  ArrayList student_name,
                  ArrayList phone_number,ArrayList birth_day){
        this.activity = activity;
        this.context = context;
        this.student_id = student_id;
        this.student_name = student_name;
        this.phone_number = phone_number;
        this.birth_day = birth_day;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.student_id_text.setText(String.valueOf(student_id.get(position)));
        holder.student_name_text.setText(String.valueOf(student_name.get(position)));
        holder.phone_number_text.setText(String.valueOf(phone_number.get(position)));
        holder.birth_day_text.setText(String.valueOf(birth_day.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("student_id", String.valueOf(student_id.get(position)));
                intent.putExtra("student_name", String.valueOf(student_name.get(position)));
                intent.putExtra("phone_number", String.valueOf(phone_number.get(position)));
                intent.putExtra("birth_day", String.valueOf(birth_day.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return student_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView student_id_text, student_name_text, phone_number_text, birth_day_text;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            student_id_text = itemView.findViewById(R.id.student_id_text);
            student_name_text = itemView.findViewById(R.id.student_name_text);
            phone_number_text = itemView.findViewById(R.id.phone_number_text);
            birth_day_text = itemView.findViewById(R.id.birth_day_text);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
