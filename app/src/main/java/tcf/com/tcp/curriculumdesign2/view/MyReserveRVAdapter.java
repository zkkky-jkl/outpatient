package tcf.com.tcp.curriculumdesign2.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import tcf.com.tcp.curriculumdesign2.R;
import tcf.com.tcp.curriculumdesign2.data.CommonStaticData;
import tcf.com.tcp.curriculumdesign2.data.PublicMethod;
import tcf.com.tcp.curriculumdesign2.server.model.RecyclerReserveItem;
import tcf.com.tcp.curriculumdesign2.server.model.Reserve;
import tcf.com.tcp.curriculumdesign2.server.model.Scheduling;

public class MyReserveRVAdapter extends RecyclerView.Adapter<MyReserveRVAdapter.ViewHolder> {
    private final List<RecyclerReserveItem> list;

    public MyReserveRVAdapter(List<RecyclerReserveItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_reserve_row_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Scheduling scheduling = list.get(position).getScheduling();
        Reserve reserve = list.get(position).getReserve();
        String date = PublicMethod.getDate(scheduling.getDate());
        holder.tv_date.setText(date);
        holder.tv_location.setText(scheduling.getLocation());
        String state;
        switch (reserve.getState()){
            case 0:
                if(date.compareTo(CommonStaticData.today) < 0){
                    state = "已逾期";
                } else{
                    state = "未缴费";
                }
                break;
            case 1:
                if(date.compareTo(CommonStaticData.today) < 0){
                    state = "已逾期";
                } else{
                    state = "已缴费";
                }
                break;
            case 2:
                state = "已完成";
                break;
            case 3:
                state = "已取消";
                break;
            default:
                state = "未知";
                break;
        }
        holder.tv_state.setText(state);
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout row;
        TextView tv_date, tv_location, tv_state;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            row = itemView.findViewById(R.id.my_reserve_row_view);
            tv_date = itemView.findViewById(R.id.my_reserve_tv_date);
            tv_location = itemView.findViewById(R.id.my_reserve_tv_location);
            tv_state = itemView.findViewById(R.id.my_reserve_tv_state);
        }
    }
}
