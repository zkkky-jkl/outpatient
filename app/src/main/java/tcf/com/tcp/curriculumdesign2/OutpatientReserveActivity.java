package tcf.com.tcp.curriculumdesign2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import java.util.ArrayList;
import java.util.List;

import tcf.com.tcp.curriculumdesign2.server.model.Division;
import tcf.com.tcp.curriculumdesign2.utils.OkhttpUtils;
import tcf.com.tcp.curriculumdesign2.utils.WebApiUtils;
import tcf.com.tcp.curriculumdesign2.view.ReserveDetailListViewAdapter;

public class OutpatientReserveActivity extends AppCompatActivity {
    List<Division> list1;
    List<Division> list2;
    ListView lv_division;
    ListView lv_detail;
    ReserveDetailListViewAdapter detailListViewAdapter;
    public static String KEY_DIVISION = "KEY_DIVISION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outpatient_reserve);
        lv_division = findViewById(R.id.reserve_lv_division);
        lv_detail = findViewById(R.id.reserve_lv_detail);
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        getDivisions();
        getDetails();
        getSchedule();
    }

    private void getSchedule(){
        lv_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(OutpatientReserveActivity.this, ScheduleActivity.class);
                i.putExtra(KEY_DIVISION, list2.get(position).getId());
                startActivity(i);
            }
        });
    }

    private void getDetails() {
        lv_division.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list2.clear();
                String url = WebApiUtils.getBaseUrl() + "/get";
                Long Id = list1.get(position).getId();
                @SuppressLint("DefaultLocale") String params = String.format("{\n" +
                        "\"[]\":{\n" +
                        "\"count\":20,\n" +
                        "\"Division\":\n" +
                        "{\n" +
                        "belong:%d\n" +
                        "}\n" +
                        "}\n" +
                        "}", Id);
                OkhttpUtils.getApiDataAsync(OutpatientReserveActivity.this, url, params, new OkhttpUtils.OnReadFinishedListener() {
                    @Override
                    public void onFinished(String readOutList) {
                        JSONObject temp = JSON.parseObject(readOutList);
                        List<JSONObject> list = (List<JSONObject>) JSONPath.eval(temp,"$..Division");
                        for (int i = 0; i < list.size(); i++) {
                            Division d = list.get(i).toJavaObject(Division.class);
                            list2.add(d);
                        }
                        detailListViewAdapter = new ReserveDetailListViewAdapter(OutpatientReserveActivity.this, list2);
                        lv_detail.setAdapter(detailListViewAdapter);
                    }

                    @Override
                    public void onFail(String e) {

                    }
                });
            }
        });
    }

    public void getDivisions(){
        String url = WebApiUtils.getBaseUrl() + "/get";
        String params = "{\n" +
                "\"[]\":{\n" +
                "\"count\":22,\n" +
                "\"Division\":{\n" +
                "belong:0\n" +
                "}\n" +
                "}\n" +
                "}";
        OkhttpUtils.getApiDataAsync(OutpatientReserveActivity.this, url, params, new OkhttpUtils.OnReadFinishedListener() {
            @Override
            public void onFinished(String readOutList) {
                JSONObject temp = JSON.parseObject(readOutList);
                List<JSONObject> list = (List<JSONObject>) JSONPath.eval(temp,"$..Division");
                List<String> nameList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    Division d = list.get(i).toJavaObject(Division.class);
                    list1.add(d);
                    nameList.add(d.getName());
                }
                //$表示根节点，.表示当前路径子节点，..表示当前路径孙节点，所以使用$..巧妙跳过[]
                ArrayAdapter<String> adapter = new ArrayAdapter<>(OutpatientReserveActivity.this, android.R.layout.simple_list_item_1, nameList);
                lv_division.setAdapter(adapter);
            }

            @Override
            public void onFail(String e) {
                showToast(e);
            }
        });
    }

    private void showToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();}
}