package tcf.com.tcp.curriculumdesign2.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tcf.com.tcp.curriculumdesign2.R;
import tcf.com.tcp.curriculumdesign2.server.model.Division;

public class ReserveDetailListViewAdapter extends ArrayAdapter<Division> {
    private List<Division> list;
    private Context context;

    public ReserveDetailListViewAdapter(@NonNull Context context, List<Division> list) {
        super(context, android.R.layout.simple_list_item_1, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v==null) v = LayoutInflater.from(context).inflate(R.layout.reserver_detail_row, null, false);
        TextView tv_name = v.findViewById(R.id.reserve_detail_name_tv);
        TextView tv_note = v.findViewById(R.id.reserve_detail_note_tv);
        Division d = list.get(position);
        tv_name.setText(d.getName());
        String note = d.getNotes();
        if(note != null){
            if(note.length()>18){
                tv_note.setText(note.substring(0,18)+"...");
            }else{
                tv_note.setText(note);
            }
        } else{
            tv_note.setVisibility(View.GONE);
        }
        return v;
    }
}
