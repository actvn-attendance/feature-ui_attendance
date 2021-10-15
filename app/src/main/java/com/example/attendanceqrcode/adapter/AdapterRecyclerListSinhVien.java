package com.example.attendanceqrcode.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.modelapi.Account;

import java.util.List;


public class AdapterRecyclerListSinhVien extends RecyclerView.Adapter<AdapterRecyclerListSinhVien.ViewHolder> {
    List<Account> studentList;
    Context context;
    OnCLickAccount onCLickAccount;

    public AdapterRecyclerListSinhVien(List<Account> studentList, Context context,OnCLickAccount onCLickAccount) {
        this.studentList = studentList;
        this.context = context;
        this.onCLickAccount = onCLickAccount;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater  = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_item_list_sinhvien,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Account account = studentList.get(position);


        holder.tvTenSinhVien.setText(account.getFull_name());
        holder.tvMaSinhVien.setText(account.getNumber_code());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickAccount.onClickItem(account);
            }
        });


    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSinhVien;
        TextView tvMaSinhVien;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenSinhVien = itemView.findViewById(R.id.tvTenSinhVien);
            tvMaSinhVien = itemView.findViewById(R.id.tvMaSinhVien);


        }
    }

    public interface OnCLickAccount{
        void onClickItem(Account account);
    }

}
