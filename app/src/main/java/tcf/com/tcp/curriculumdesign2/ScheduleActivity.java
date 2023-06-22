package tcf.com.tcp.curriculumdesign2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tcf.com.tcp.curriculumdesign2.data.CommonStaticData;
import tcf.com.tcp.curriculumdesign2.server.model.Division;
import tcf.com.tcp.curriculumdesign2.server.model.RecyclerItemDate;
import tcf.com.tcp.curriculumdesign2.server.model.Reserve;
import tcf.com.tcp.curriculumdesign2.server.model.Scheduling;
import tcf.com.tcp.curriculumdesign2.server.model.User;
import tcf.com.tcp.curriculumdesign2.ui.reserve.ReserveFragment;
import tcf.com.tcp.curriculumdesign2.utils.OkhttpUtils;
import tcf.com.tcp.curriculumdesign2.utils.WebApiUtils;
import tcf.com.tcp.curriculumdesign2.view.ReserveDetailListViewAdapter;
import tcf.com.tcp.curriculumdesign2.view.ScheduleListViewAdapter;
import tcf.com.tcp.curriculumdesign2.view.ScheduleRecyclerViewAdapter;

public class ScheduleActivity extends AppCompatActivity {
    TextView tv_date;
    ListView lv_schedule_detail;
    Long division_id;
    String date;
    List<Scheduling> schedulingList;
    List<User> userList;
    List<String> sevenDateList;
    List<String> sevenWeekDayList;
    ScheduleListViewAdapter adapter;
    Calendar cal;
    RecyclerView schedule_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        cal = Calendar.getInstance();
        lv_schedule_detail = findViewById(R.id.schedule_detail);
        tv_date = findViewById(R.id.schedule_choose_date);
        schedule_date = findViewById(R.id.schedule_date);
        schedule_date.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        schedule_date.setLayoutManager(linearLayoutManager);
        division_id = getIntent().getLongExtra(ReserveFragment.KEY_DIVISION, 1);
        getDays();
        getDoctors();
        checkReserve();
    }

    private void checkReserve(){
        lv_schedule_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User doctor = userList.get(position);
                Scheduling scheduling = schedulingList.get(position);
                if(scheduling.getCapacity() > 0){
                    AlertDialog.Builder ab = new AlertDialog.Builder(ScheduleActivity.this);
                    ab.setTitle("确认预约");
                    StringBuilder sb = new StringBuilder();
                    String[] info = new String[3];
                    info[0] = "肺部肿瘤与呼吸介入诊断与治疗，肺部感染及呼吸危重症救治，间质性肺疾病及呼吸系统疑难疾病诊疗等。";
                    info[1] = "擅长呼吸系统常见疾病，疑难疾病以及气管镜检查，睡眠呼吸暂停疾病的诊治。";
                    info[2] = "擅长呼吸系统常见病及疑难危重症诊治;肺部感染、慢阻肺、肺部肿瘤、间质性肺病、" +
                            "肺血管病的诊治，熟练掌握支气管镜检查及活检CT定位下肺穿刺、内科胸腔镜检查等技术。";
                    switch (doctor.getName()){
                        case "张三":
                            sb.append("信息:" + info[0] + "\n");
                            break;
                        case "李四":
                            sb.append("信息:" + info[1] + "\n");
                            break;
                        case "王五":
                            sb.append("信息:" + info[2] + "\n");
                            break;
                    }
                    sb.append("医生:" + doctor.getName() + "\n");
                    sb.append("时间:" + date + "\n");
                    sb.append("地点:" + scheduling.getLocation() + "\n");
                    sb.append("收费:" + String.format("%.1f", scheduling.getCharge()));
                    ab.setMessage(sb.toString());
                    ab.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Reserve reserve = new Reserve();
                            reserve.setUserId(CommonStaticData.authUser.getId());
                            reserve.setSchedulingId(scheduling.getId());
                            reserve.setState(0);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(reserve.getClass().getSimpleName(), reserve);
                            jsonObject.put("tag", reserve.getClass().getSimpleName());
                            String params = jsonObject.toString();
                            WebApiUtils.postData(ScheduleActivity.this, params, new WebApiUtils.OnClassFinishedListener<String>() {
                                @Override
                                public void onFinished(String out) {
                                    showToast("预约成功");
                                    finish();
                                }

                                @Override
                                public void onFail(String e) {

                                }
                            });
                        }
                    });
                    ab.setNegativeButton("取消", null);
                    ab.create().show();
                } else{
                    showToast("请选择其他医生或日期");
                }
            }
        });
    }

    private void getDays(){
        getSevenDate();
        getWeekOfDate();
        List<RecyclerItemDate> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            list.add(new RecyclerItemDate(sevenDateList.get(i), sevenWeekDayList.get(i)));
        }
        ScheduleRecyclerViewAdapter adapter = new ScheduleRecyclerViewAdapter(this, list);
        adapter.setOnItemClickListener(new ScheduleRecyclerViewAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                date = sevenDateList.get(position);
                getDoctors();
            }
        });
        schedule_date.setAdapter(adapter);
    }

    private void getSevenDate() {
        sevenDateList = CommonStaticData.sevenDays;
        date = sevenDateList.get(0);
    }

    private void getWeekOfDate() {
        sevenWeekDayList = CommonStaticData.sevenWeekDay;
    }

    private void getDoctors() {
        tv_date.setText(date);
        String url = WebApiUtils.getBaseUrl() + "/get";
        @SuppressLint("DefaultLocale") String params = String.format("{\n" +
                "    \"[]\": {\n" +
                "        \"User\": {\n" +
                "            \"identity$\": \"%c%d\"\n" +
                "        },\n" +
                "        \"Scheduling\": {\n" +
                "            \"userId@\": \"/User/id\",\n" +
                "            \"date\":'%s'\n" +
                "        }\n" +
                "    }\n" +
                "}", '%', division_id, date);
        OkhttpUtils.getApiDataAsync(this, url, params, new OkhttpUtils.OnReadFinishedListener() {
            @Override
            public void onFinished(String readOutList) {
                schedulingList = new ArrayList<>();
                userList = new ArrayList<>();
                JSONObject temp = JSON.parseObject(readOutList);
                List<JSONObject> list1 = (List<JSONObject>) JSONPath.eval(temp, "$..Scheduling");
                List<JSONObject> list2 = (List<JSONObject>) JSONPath.eval(temp, "$..User");
                if(list1.size()==0){
                    adapter = new ScheduleListViewAdapter(ScheduleActivity.this, userList, schedulingList);
                    lv_schedule_detail.setAdapter(adapter);
                } else {
                    for (int i = 0; i < list1.size(); i++) {
                        Scheduling s = list1.get(i).toJavaObject(Scheduling.class);
                        User u = list2.get(i).toJavaObject(User.class);
                        while (u.getId().longValue() != s.getUserId().longValue()) {
                            list2.remove(i);
                            u = list2.get(i).toJavaObject(User.class);
                        }
                        @SuppressLint("DefaultLocale") String params2 = String.format("{\n" +
                                "    \"[]\": {\n" +
                                "        \"Reserve\": {\n" +
                                "            \"schedulingId\": %d,\n" +
                                "            \"state\": \"[0,1,2]\"\n" +
                                "        },\n" +
                                "        \"query\": 2\n" +
                                "    },\n" +
                                "    \"total@\": \"/[]/total\"\n" +
                                "}", s.getId());
                        User finalU = u;
                        int finalI = i;
                        OkhttpUtils.getApiDataAsync(ScheduleActivity.this, url, params2, new OkhttpUtils.OnReadFinishedListener() {
                            @Override
                            public void onFinished(String readOutList) {
                                JSONObject temp = JSON.parseObject(readOutList);
                                int count = temp.getIntValue("total");
                                s.setCapacity(s.getCapacity() - count);
                                userList.add(finalU);
                                schedulingList.add(s);
                                if(finalI == list1.size()-1){
                                    adapter = new ScheduleListViewAdapter(ScheduleActivity.this, userList, schedulingList);
                                    lv_schedule_detail.setAdapter(adapter);
                                }
                            }

                            @Override
                            public void onFail(String e) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onFail(String e) {

            }
        });
    }

    private int getReservedCount(Long id) {
        String url = WebApiUtils.getBaseUrl() + "/get";
        final int[] cnt = {0};
        @SuppressLint("DefaultLocale") String params = String.format("{\n" +
                "    \"[]\": {\n" +
                "        \"Reserve\": {\n" +
                "            \"schedulingId\": %d\n" +
                "        },\n" +
                "        \"query\": 2\n" +
                "    },\n" +
                "    \"total@\": \"/[]/total\"\n" +
                "}", id);
        OkhttpUtils.getApiDataAsync(this, url, params, new OkhttpUtils.OnReadFinishedListener() {
            @Override
            public void onFinished(String readOutList) {
                JSONObject temp = JSON.parseObject(readOutList);
                cnt[0] = temp.getIntValue("total");
            }

            @Override
            public void onFail(String e) {

            }
        });
        return cnt[0];
    }

    private void showToast(String e){
        Toast.makeText(this, e, Toast.LENGTH_LONG).show();
    }
}