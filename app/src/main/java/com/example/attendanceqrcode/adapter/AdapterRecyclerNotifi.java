package com.example.attendanceqrcode.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.model.ClassRooms;
import com.example.attendanceqrcode.model.Notification;
import com.example.attendanceqrcode.modelapi.Data;

import java.util.List;


public class AdapterRecyclerNotifi extends RecyclerView.Adapter<AdapterRecyclerNotifi.ViewHolder> {
    List<Data> notifications;
    FragmentActivity activity;

    ClickItemNotifi clickItemNotifi;

    public AdapterRecyclerNotifi(List<Data> notifications, FragmentActivity activity, ClickItemNotifi clickItemNotifi)
    {
        this.notifications = notifications;
        this.activity = activity;
        this.clickItemNotifi = clickItemNotifi;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater  = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_item_notification,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Data notification = notifications.get(position);

        if (notification.getType() == 1)
        {
            holder.imgNotifi.setImageResource(R.drawable.ic_logout);
        }else if (notification.getType() == 2)
        {
            holder.imgNotifi.setImageResource(R.drawable.ic_notifications);
        }else if (notification.getType() == 3)
        {
            holder.imgNotifi.setImageResource(R.drawable.ic_qr_code);
        }


        holder.tvContentNotifi.setText(notification.getBody());
        holder.tvDatenotifi.setText(notification.getUpdated_date().substring(0,10)+"-"+notification.getUpdated_date().substring(11,16));

        holder.rlItemNotifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickItemNotifi.clickItemNoti();

            }
        });

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNotifi;
        TextView tvContentNotifi;
        TextView tvDatenotifi;
        RelativeLayout rlItemNotifi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgNotifi = itemView.findViewById(R.id.imgeNotifi);
            tvContentNotifi = itemView.findViewById(R.id.tvContentNotifi);
            tvDatenotifi = itemView.findViewById(R.id.tvDateNotifi);
            rlItemNotifi = itemView.findViewById(R.id.rl_item_notifi);


        }
    }

    public interface ClickItemNotifi
    {
        void clickItemNoti();

    }
}
