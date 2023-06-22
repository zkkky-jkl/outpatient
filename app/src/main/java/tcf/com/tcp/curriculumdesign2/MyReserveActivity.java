package tcf.com.tcp.curriculumdesign2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import tcf.com.tcp.curriculumdesign2.data.CommonStaticData;
import tcf.com.tcp.curriculumdesign2.server.model.RecyclerReserveItem;
import tcf.com.tcp.curriculumdesign2.server.model.Reserve;
import tcf.com.tcp.curriculumdesign2.server.model.Scheduling;
import tcf.com.tcp.curriculumdesign2.utils.OkhttpUtils;
import tcf.com.tcp.curriculumdesign2.utils.WebApiUtils;
import tcf.com.tcp.curriculumdesign2.view.MyReserveRVAdapter;

public class MyReserveActivity extends AppCompatActivity {
    List<RecyclerReserveItem> list;
    MyReserveRVAdapter adapter;
    RecyclerView rv_reserve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reserve);
        list = new ArrayList<>();
        rv_reserve = findViewById(R.id.my_reserve_rv);
        rv_reserve.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_reserve.setLayoutManager(linearLayoutManager);
        getSupportActionBar().setTitle("我的预约");
        String url = WebApiUtils.getBaseUrl() + "/get";
        @SuppressLint("DefaultLocale") String params = String.format("{\n" +
                "    \"[]\": {\n" +
                "        \"Reserve\": {\n" +
                "            \"userId\": %d,\n" +
                "            \"@order\":\"id-\"\n" +
                "        },\n" +
                "        \"Scheduling\":{\n" +
                "            \"id@\":\"/Reserve/schedulingId\"\n" +
                "        }\n" +
                "    }\n" +
                "}", CommonStaticData.authUser.getId());
        OkhttpUtils.getApiDataAsync(this, url, params, new OkhttpUtils.OnReadFinishedListener() {
            @Override
            public void onFinished(String readOutList) {
                JSONObject temp = JSON.parseObject(readOutList);
                List<JSONObject> list1 = (List<JSONObject>) JSONPath.eval(temp, "$..Reserve");
                List<JSONObject> list2 = (List<JSONObject>) JSONPath.eval(temp, "$..Scheduling");
                for (int i = 0; i < list1.size(); i++) {
                    Reserve reserve = list1.get(i).toJavaObject(Reserve.class);
                    Scheduling scheduling = list2.get(i).toJavaObject(Scheduling.class);
                    list.add(new RecyclerReserveItem(reserve, scheduling));
                }
                adapter = new MyReserveRVAdapter(list);
                rv_reserve.setAdapter(adapter);
            }

            @Override
            public void onFail(String e) {

            }
        });
    }
}