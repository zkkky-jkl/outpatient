package tcf.com.tcp.curriculumdesign2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tcf.com.tcp.curriculumdesign2.data.CommonStaticData;
import tcf.com.tcp.curriculumdesign2.server.model.RecyclerItemDate;
import tcf.com.tcp.curriculumdesign2.server.model.Scheduling;
import tcf.com.tcp.curriculumdesign2.utils.OkhttpUtils;
import tcf.com.tcp.curriculumdesign2.utils.WebApiUtils;
import tcf.com.tcp.curriculumdesign2.view.ScheduleRecyclerViewAdapter;

public class DoctorScheduleActivity extends AppCompatActivity {
    List<String> sevenDateList;
    List<String> sevenWeekDayList;
    RecyclerView schedule_date;
    NumberPicker picker_human, picker_charge;
    Button bt_cancel, bt_release;
    EditText et_location;
    TextView tv_date, release;
    String date;
    Scheduling scheduling;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_schedule);
        getSupportActionBar().setTitle("我的排班");
        initView();
        getDays();
        getSchedule();
    }

    private void initView() {
        tv_date = findViewById(R.id.schedule_choose_date);
        release = findViewById(R.id.have_release);
        schedule_date = findViewById(R.id.schedule_date);
        schedule_date.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        schedule_date.setLayoutManager(linearLayoutManager);
        et_location = findViewById(R.id.et_location);
        picker_human = findViewById(R.id.picker_human);
        picker_charge = findViewById(R.id.picker_charge);
        bt_cancel = findViewById(R.id.bt_cancel);
        bt_release = findViewById(R.id.bt_release);
        picker_human.setMinValue(30);
        picker_human.setMaxValue(80);
        picker_charge.setMinValue(0);
        picker_charge.setMaxValue(200);
        bt_cancel.setOnClickListener(v -> {
            if (scheduling == null) {
                showToast("你尚未发布当天的排班");
            } else{
                Scheduling s = new Scheduling();
                s.setId(scheduling.getId());
                s.setCharge(scheduling.getCharge());
                s.setCapacity(scheduling.getCapacity());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(s.getClass().getSimpleName(), s);
                jsonObject.put("tag", s.getClass().getSimpleName());
                String params = jsonObject.toString();
                WebApiUtils.deleteData(DoctorScheduleActivity.this, params, new WebApiUtils.OnClassFinishedListener<String>() {
                    @Override
                    public void onFinished(String out) {
                        showToast("取消成功");
                        getSchedule();
                    }

                    @Override
                    public void onFail(String e) {

                    }
                });
            }
        });
        bt_release.setOnClickListener(v -> {
            if(TextUtils.isEmpty(et_location.getText())){
                showToast("请输入门诊地点");
            } else{
                if(scheduling == null){
                    @SuppressLint("DefaultLocale") String params = String.format("{\n" +
                                    "    \"Scheduling\": {\n" +
                                    "        \"capacity\": %d,\n" +
                                    "        \"charge\": %d,\n" +
                                    "        \"date\": \"%s\",\n" +
                                    "        \"location\": \"%s\",\n" +
                                    "        \"userId\": %d\n" +
                                    "    },\n" +
                                    "    \"tag\": \"Scheduling\"\n" +
                                    "}", picker_human.getValue(), picker_charge.getValue(),
                            date, et_location.getText().toString(), CommonStaticData.authUser.getId());
                    WebApiUtils.postData(DoctorScheduleActivity.this, params, new WebApiUtils.OnClassFinishedListener<String>() {
                        @Override
                        public void onFinished(String out) {
                            showToast("发布成功");
                            getSchedule();
                        }

                        @Override
                        public void onFail(String e) {

                        }
                    });
                } else{
                    Scheduling s = scheduling.clone();
                    s.setCapacity(picker_human.getValue());
                    s.setLocation(et_location.getText().toString());
                    s.setCharge(picker_charge.getValue());
                    s.setDate(null);
                    s.setUserId(null);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(s.getClass().getSimpleName(), s);
                    jsonObject.put("tag", s.getClass().getSimpleName());
                    String params = jsonObject.toString();
                    WebApiUtils.modifyData(DoctorScheduleActivity.this, params, new WebApiUtils.OnClassFinishedListener<String>() {
                        @Override
                        public void onFinished(String out) {
                            showToast("更改成功");
                        }

                        @Override
                        public void onFail(String e) {

                        }
                    });
                }
            }
        });
    }

    private void getDays() {
        sevenDateList = CommonStaticData.sevenDays;
        date = sevenDateList.get(0);
        sevenWeekDayList = CommonStaticData.sevenWeekDay;
        List<RecyclerItemDate> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            list.add(new RecyclerItemDate(sevenDateList.get(i), sevenWeekDayList.get(i)));
        }
        ScheduleRecyclerViewAdapter adapter = new ScheduleRecyclerViewAdapter(this, list);
        adapter.setOnItemClickListener((parent, view, position) -> {
            date = sevenDateList.get(position);
            getSchedule();
        });
        schedule_date.setAdapter(adapter);
    }

    private void getSchedule() {
        tv_date.setText(date);
        String url = WebApiUtils.getBaseUrl() + "/get";
        @SuppressLint("DefaultLocale") String params = String.format("{\n" +
                "    \"Scheduling\": {\n" +
                "        \"userId\": %d,\n" +
                "        \"date\": \"%s\"\n" +
                "    }\n" +
                "}", CommonStaticData.authUser.getId(), date);
        OkhttpUtils.getApiDataAsync(this, url, params, new OkhttpUtils.OnReadFinishedListener() {
            @Override
            public void onFinished(String readOutList) {
                JSONObject object = JSONObject.parseObject(readOutList);
                scheduling = object.getObject("Scheduling", Scheduling.class);
                if(scheduling==null){
                    et_location.setText("");
                    release.setText("尚未发布当天排班");
                    bt_release.setText("发布");
                } else{
                    et_location.setText(scheduling.getLocation());
                    release.setText("已发布当天排班");
                    bt_release.setText("更改");
                }
                picker_human.setValue(picker_human.getMinValue());
                picker_charge.setValue(picker_charge.getMinValue());
            }

            @Override
            public void onFail(String e) {

            }
        });
    }

    private void showToast(String e){
        Toast.makeText(this, e, Toast.LENGTH_LONG).show();
    }
}