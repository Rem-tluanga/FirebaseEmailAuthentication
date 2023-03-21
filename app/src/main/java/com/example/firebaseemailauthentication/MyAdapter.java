package com.example.firebaseemailauthentication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    ArrayList<User> userArrayList;

    public MyAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.items,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        User user = userArrayList.get(position);

        holder.blood_group.setText(user.Blood_Group);
        holder.Name.setText(user.Name);
        holder.district.setText(String.valueOf(user.District));

        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context,DetailsActivity.class);
            intent.putExtra("name",user.Name);
            intent.putExtra("blood_group", user.Blood_Group);intent.putExtra("blood_g", user.Blood_Group);
            intent.putExtra("email", user.Email);
            intent.putExtra("phone_no", user.Phone_No);
            intent.putExtra("age", user.Age);
            intent.putExtra("locality", user.Locality);
            intent.putExtra("district", user.District);
            intent.putExtra("state", user.State);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView blood_group, Name, district;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            blood_group = itemView.findViewById(R.id.bloodgrp);
            Name = itemView.findViewById(R.id.Name);
            district = itemView.findViewById(R.id.districta);
        }
    }
}
