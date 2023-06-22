package tcf.com.tcp.curriculumdesign2.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import tcf.com.tcp.curriculumdesign2.R;
import tcf.com.tcp.curriculumdesign2.server.model.Scheduling;
import tcf.com.tcp.curriculumdesign2.server.model.User;
import tcf.com.tcp.curriculumdesign2.utils.OkhttpUtils;
import tcf.com.tcp.curriculumdesign2.utils.WebApiUtils;

public class ScheduleListViewAdapter extends ArrayAdapter<Scheduling> {
    private List<Scheduling> schedulingList;
    private List<User> userList;
    private Context context;
    User user;
    public ScheduleListViewAdapter(@NonNull Context context, List<User> userList, List<Scheduling> schedulingList) {
        super(context, android.R.layout.simple_list_item_1, schedulingList);
        this.schedulingList = schedulingList;
        this.userList = userList;
        this.context = context;
        user = new User();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v==null) v = LayoutInflater.from(context).inflate(R.layout.schedule_doctor_row_view, null, false);
        Scheduling scheduling = schedulingList.get(position);
        User user = userList.get(position);
        ImageView iv_photo = v.findViewById(R.id.iv_photo);
        TextView tv_name = v.findViewById(R.id.schedule_name);
        TextView tv_identity = v.findViewById(R.id.schedule_identity);
        TextView tv_date = v.findViewById(R.id.schedule_date);
        TextView tv_location = v.findViewById(R.id.schedule_location);
        TextView tv_cost = v.findViewById(R.id.schedule_cost);
        TextView tv_rest = v.findViewById(R.id.schedule_rest);
        tv_name.setText(user.getName());
        switch (user.getName()){
            case "张三":
                iv_photo.setImageResource(R.drawable.jiebrother);
                break;
            case "李四":
                iv_photo.setImageResource(R.drawable.anpeibrother);
                break;
            case "王五":
                iv_photo.setImageResource(R.drawable.kunbrother);
                break;
        }
        switch (user.getIdentity().charAt(0)){
            case '1':
                tv_identity.setText("主治医师");
                break;
            case '2':
                tv_identity.setText("副主任医师");
                break;
            case '3':
                tv_identity.setText("主任医师");
                break;
        }
        tv_date.setText(getDate(scheduling.getDate()));
        tv_location.setText(scheduling.getLocation());
        tv_cost.setText(String.format("%.1f", scheduling.getCharge()));
        tv_rest.setText(String.format("%d", scheduling.getCapacity()));
        return v;
    }

    private String getDate(Long date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(c.getTime());
    }

    private void showToast(String e){
        Toast.makeText(context, e, Toast.LENGTH_LONG).show();
    }
}
