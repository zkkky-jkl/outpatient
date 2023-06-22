package tcf.com.tcp.curriculumdesign2.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tcf.com.tcp.curriculumdesign2.R;
import tcf.com.tcp.curriculumdesign2.server.model.RecyclerItemDate;

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    private List<RecyclerItemDate> list;
    private Context context;
    private onRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private RecyclerView rvParent;
    public void setOnItemClickListener(onRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public ScheduleRecyclerViewAdapter(@NonNull Context context, List<RecyclerItemDate> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.schedule_date_item, null, false);
        rvParent = (RecyclerView) parent;
        v.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyclerItemDate item = list.get(position);
        holder.tv_date.setText(item.getDate().substring(5));
        holder.tv_week.setText(item.getWeek());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        int position = rvParent.getChildAdapterPosition(v);
        if(onRecyclerViewItemClickListener != null){
            onRecyclerViewItemClickListener.onItemClick(rvParent, v, position);
        }
    }

    public interface onRecyclerViewItemClickListener{
        void onItemClick(RecyclerView parent, View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_date, tv_week;
        LinearLayout linearLayout_date;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tv_date = itemView.findViewById(R.id.schedule_date);
            tv_week = itemView.findViewById(R.id.schedule_week);
            linearLayout_date = itemView.findViewById(R.id.linearLayout_date);
        }
    }
}
