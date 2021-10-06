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
import com.example.attendanceqrcode.components.AppAlertDialog;
import com.example.attendanceqrcode.model.ClassRooms;
import com.example.attendanceqrcode.modelapi.Subject;

import java.util.List;


public class AdapterRecyclerSubject extends RecyclerView.Adapter<AdapterRecyclerSubject.ViewHolder> {
    List<Subject> subjectList;
    FragmentActivity activity;

    ClickDetailClass clickDetailClass;

    public AdapterRecyclerSubject(List<Subject> subjectList, FragmentActivity activity, ClickDetailClass clickDetailClass) {
        this.subjectList = subjectList;
        this.activity = activity;
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

        Subject subject = subjectList.get(position);

        holder.txtTenLop.setText(subject.getSubject_name());
        holder.txtMalop.setText(subject.getNumber_of_credits()+"");


        holder.llDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDetailClass.clickDetail(subject);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (subjectList == null)
        {
            AppAlertDialog.showTokenTimeOutDialog(activity);
        }
        return subjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenLop;
        TextView txtSoTinChi;
        TextView txtMalop;
        LinearLayout llDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTenLop = itemView.findViewById(R.id.txt_tenlop);
            txtSoTinChi = itemView.findViewById(R.id.txt_sotinchi);
            txtMalop = itemView.findViewById(R.id.txt_malop);
            llDetail = itemView.findViewById(R.id.ll_detail);

        }
    }


    public interface ClickDetailClass
    {
        void clickDetail(Subject subject);

    }
}
