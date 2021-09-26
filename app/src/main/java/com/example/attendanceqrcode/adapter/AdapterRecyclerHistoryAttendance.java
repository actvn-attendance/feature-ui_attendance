package com.example.attendanceqrcode.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.model.ClassRooms;
import com.example.attendanceqrcode.model.HistoryAttendance;

import java.util.List;


public class AdapterRecyclerHistoryAttendance extends RecyclerView.Adapter<AdapterRecyclerHistoryAttendance.ViewHolder> {
    List<HistoryAttendance> historyAttendances;
    Context context;

    public AdapterRecyclerHistoryAttendance(List<HistoryAttendance> historyAttendances, Context context) {
        this.historyAttendances = historyAttendances;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater  = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_item_history_attendance,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HistoryAttendance historyAttendance = historyAttendances.get(position);


        holder.txtNgayhoc.setText(historyAttendance.getDateAttendance());

        switch (historyAttendance.getTypeStatus())
        {
            case 0:
                holder.txtStatus.setText("Chưa điểm danh");
                holder.txtStatus.setTextColor(Color.RED);
                holder.imageFail.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder.txtStatus.setText("Đã điểm danh");
                holder.txtStatus.setTextColor(Color.GREEN);
                holder.imageSuccess.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.txtStatus.setText("Đi muộn");
                holder.txtStatus.setTextColor(Color.BLUE);
                holder.imageLate.setVisibility(View.VISIBLE);
                break;
            default:
                holder.txtStatus.setText("Chưa điểm danh");
                holder.txtStatus.setTextColor(Color.RED);
                holder.imageFail.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return historyAttendances.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNgayhoc;
        TextView txtStatus;
        ImageView imageSuccess,imageFail,imageLate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNgayhoc = itemView.findViewById(R.id.txt_ngayhoc);
            txtStatus = itemView.findViewById(R.id.txt_status);
            imageSuccess = itemView.findViewById(R.id.image_success);
            imageFail = itemView.findViewById(R.id.image_fail);
            imageLate = itemView.findViewById(R.id.image_late);

        }
    }

}
