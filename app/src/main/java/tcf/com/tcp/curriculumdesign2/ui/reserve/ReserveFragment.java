package tcf.com.tcp.curriculumdesign2.ui.reserve;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.luck.lib.camerax.listener.ClickListener;

import java.util.ArrayList;
import java.util.List;

import tcf.com.tcp.curriculumdesign2.Func20Activity;
import tcf.com.tcp.curriculumdesign2.GuideEmergencyActivity;
import tcf.com.tcp.curriculumdesign2.GuideHospitalizeActivity;
import tcf.com.tcp.curriculumdesign2.GuideOutpatientActivity;
import tcf.com.tcp.curriculumdesign2.GuidePhysicalExaminationActivity;
import tcf.com.tcp.curriculumdesign2.OutpatientReserveActivity;
import tcf.com.tcp.curriculumdesign2.R;
import tcf.com.tcp.curriculumdesign2.ScheduleActivity;
import tcf.com.tcp.curriculumdesign2.TemporaryActivity;
import tcf.com.tcp.curriculumdesign2.data.CommonStaticData;
import tcf.com.tcp.curriculumdesign2.server.model.Division;
import tcf.com.tcp.curriculumdesign2.server.model.Privacy;
import tcf.com.tcp.curriculumdesign2.utils.OkhttpUtils;
import tcf.com.tcp.curriculumdesign2.utils.WebApiUtils;
import tcf.com.tcp.curriculumdesign2.view.ReserveDetailListViewAdapter;

public class ReserveFragment extends Fragment implements View.OnClickListener {
    public static String KEY_DIVISION = "KEY_DIVISION";
    LinearLayout emergency, outpatient, hospitalize, examination;
    ImageView func1,func2,func3,func4,func5,func6,func7,func8,func9,func10,func11,func12,
            func13,func14,func15,func16,func17,func18,func19,func20,func21,func22,func23,func24;
    Intent i;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reserve, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v){
        emergency = v.findViewById(R.id.emergency);
        outpatient = v.findViewById(R.id.outpatient);
        hospitalize = v.findViewById(R.id.hospitalize);
        examination = v.findViewById(R.id.examination);
        func1 = v.findViewById(R.id.function1);
        func2 = v.findViewById(R.id.function2);
        func3 = v.findViewById(R.id.function3);
        func4 = v.findViewById(R.id.function4);
        func5 = v.findViewById(R.id.function5);
        func6 = v.findViewById(R.id.function6);
        func7 = v.findViewById(R.id.function7);
        func8 = v.findViewById(R.id.function8);
        func9 = v.findViewById(R.id.function9);
        func10 = v.findViewById(R.id.function10);
        func11 = v.findViewById(R.id.function11);
        func12 = v.findViewById(R.id.function12);
        func13 = v.findViewById(R.id.function13);
        func14 = v.findViewById(R.id.function14);
        func15 = v.findViewById(R.id.function15);
        func16 = v.findViewById(R.id.function16);
        func17 = v.findViewById(R.id.function17);
        func18 = v.findViewById(R.id.function18);
        func19 = v.findViewById(R.id.function19);
        func20 = v.findViewById(R.id.function20);
        func21 = v.findViewById(R.id.function21);
        func22 = v.findViewById(R.id.function22);
        func23 = v.findViewById(R.id.function23);
        func24 = v.findViewById(R.id.function24);
        emergency.setOnClickListener(this);
        outpatient.setOnClickListener(this);
        hospitalize.setOnClickListener(this);
        examination.setOnClickListener(this);
        func1.setOnClickListener(this);
        func2.setOnClickListener(this);
        func3.setOnClickListener(this);
        func4.setOnClickListener(this);
        func5.setOnClickListener(this);
        func6.setOnClickListener(this);
        func7.setOnClickListener(this);
        func8.setOnClickListener(this);
        func9.setOnClickListener(this);
        func10.setOnClickListener(this);
        func11.setOnClickListener(this);
        func12.setOnClickListener(this);
        func13.setOnClickListener(this);
        func14.setOnClickListener(this);
        func15.setOnClickListener(this);
        func16.setOnClickListener(this);
        func17.setOnClickListener(this);
        func18.setOnClickListener(this);
        func19.setOnClickListener(this);
        func20.setOnClickListener(this);
        func21.setOnClickListener(this);
        func22.setOnClickListener(this);
        func23.setOnClickListener(this);
        func24.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.emergency:
                i = new Intent(getActivity(), GuideEmergencyActivity.class);
                startActivity(i);
                break;
            case R.id.outpatient:
                i = new Intent(getActivity(), GuideOutpatientActivity.class);
                startActivity(i);
                break;
            case R.id.hospitalize:
                i = new Intent(getActivity(), GuideHospitalizeActivity.class);
                startActivity(i);
                break;
            case R.id.examination:
                i = new Intent(getActivity(), GuidePhysicalExaminationActivity.class);
                startActivity(i);
                break;
            case R.id.function6:
                i = new Intent(getActivity(), OutpatientReserveActivity.class);
                startActivity(i);
                break;
            case R.id.function1:
            case R.id.function2:
            case R.id.function3:
            case R.id.function4:
            case R.id.function5:
            case R.id.function7:
            case R.id.function8:
            case R.id.function9:
            case R.id.function11:
            case R.id.function12:
            case R.id.function13:
            case R.id.function14:
            case R.id.function15:
            case R.id.function16:
            case R.id.function17:
            case R.id.function18:
            case R.id.function19:
            case R.id.function21:
            case R.id.function22:
            case R.id.function23:
            case R.id.function24:
                i = new Intent(getActivity(), TemporaryActivity.class);
                startActivity(i);
                break;
            case R.id.function10:
                AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
                ab.setTitle("请向医护人员出示此二维码");
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_func10, null, false);
                ab.setView(view);
                ab.create().show();
                break;
            case R.id.function20:
                i = new Intent(getActivity(), Func20Activity.class);
                startActivity(i);
                break;
        }
    }
}