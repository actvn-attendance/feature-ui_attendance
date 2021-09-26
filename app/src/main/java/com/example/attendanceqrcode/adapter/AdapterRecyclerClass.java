package com.example.attendanceqrcode.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.model.ClassRooms;

import java.util.List;


public class AdapterRecyclerClass extends RecyclerView.Adapter<AdapterRecyclerClass.ViewHolder> {
    List<ClassRooms> classList;
    FragmentActivity activity;

    ClickHistoryClass clickHistoryClass;
    ClickDetailClass clickDetailClass;

    public AdapterRecyclerClass(List<ClassRooms> classList, FragmentActivity activity, ClickHistoryClass clickHistoryClass, ClickDetailClass clickDetailClass) {
        this.classList = classList;
        this.activity = activity;
        this.clickHistoryClass = clickHistoryClass;
        this.clickDetailClass = clickDetailClass;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater  = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_item_class,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ClassRooms classRoom = classList.get(position);

        holder.txtTenLop.setText(classRoom.getName());
//        holder.txtSoTinChi.setText(classRoom.getSubject().getNumberOfCredits()+"");
        holder.txtMalop.setText(classRoom.getNameKD());

        holder.llHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickHistoryClass.clickHistory();

            }
        });

        holder.llDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDetailClass.clickDetail();
            }
        });

    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenLop;
        TextView txtSoTinChi;
        TextView txtMalop;
        LinearLayout llHistory;
        LinearLayout llDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTenLop = itemView.findViewById(R.id.txt_tenlop);
            txtSoTinChi = itemView.findViewById(R.id.txt_sotinchi);
            txtMalop = itemView.findViewById(R.id.txt_malop);
            llHistory = itemView.findViewById(R.id.ll_history);
            llDetail = itemView.findViewById(R.id.ll_detail);

        }
    }

    public interface ClickHistoryClass
    {
        void clickHistory();

    }

    public interface ClickDetailClass
    {
        void clickDetail();

    }
}
