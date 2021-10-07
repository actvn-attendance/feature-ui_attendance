package com.example.attendanceqrcode.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceqrcode.MainActivity;
import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.components.AppAlertDialog;
import com.example.attendanceqrcode.modelapi.Schedule;

import java.util.List;


public class AdapterRecyclerEventCalendar extends RecyclerView.Adapter<AdapterRecyclerEventCalendar.ViewHolder> {

    List<Schedule> scheduleList;
    FragmentActivity fragmentActivity;

    public AdapterRecyclerEventCalendar(List<Schedule> scheduleList, FragmentActivity fragmentActivity)
    {
        this.scheduleList = scheduleList;
        this.fragmentActivity = fragmentActivity;
    }
    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_item_event,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {
        if (position == scheduleList.size() - 1)
        {
            holder.view.setVisibility(View.GONE);
        }

        Schedule schedule = scheduleList.get(position);
        if (schedule != null)
        {
            holder.txtTBatdau.setText(schedule.getStart_time().substring(11,16));
            holder.txtTKetthuc.setText(schedule.getEnd_time().substring(11,16));
            holder.txtTenlopCalendar.setText(schedule.getSubject_name());
            holder.txtMalopCalendar.setText(schedule.getAddress_name());
            holder.txtNoData.setVisibility(View.GONE);
        }else {
            holder.txtNoData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        if(scheduleList == null)
        {
            AppAlertDialog.showTokenTimeOutDialog(fragmentActivity);

        }
        return scheduleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTBatdau;
        TextView txtTKetthuc;
        TextView txtTenlopCalendar;
        TextView txtMalopCalendar;
        TextView txtNoData;
        View view;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            txtTBatdau = itemView.findViewById(R.id.txt_tBatdau);
            txtTKetthuc = itemView.findViewById(R.id.txt_tKetthuc);
            txtTenlopCalendar = itemView.findViewById(R.id.txt_tenlopCalendar);
            txtMalopCalendar = itemView.findViewById(R.id.txt_malopCalendar);
            txtNoData = itemView.findViewById(R.id.tvNoData);
            view = itemView.findViewById(R.id.view_line);
        }
    }
}
