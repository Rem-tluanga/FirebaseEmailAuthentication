package com.example.firebaseemailauthentication;

import android.content.Context;
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

        holder.Email.setText(user.Email);
        holder.Name.setText(user.Name);
        holder.Phone_No.setText(String.valueOf(user.Phone_No));
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Email, Name, Phone_No;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Email = itemView.findViewById(R.id.Email);
            Name = itemView.findViewById(R.id.Name);
            Phone_No = itemView.findViewById(R.id.Phone);
        }
    }
}
