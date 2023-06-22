package tcf.com.tcp.curriculumdesign2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import tcf.com.tcp.curriculumdesign2.server.model.Privacy;
import tcf.com.tcp.curriculumdesign2.server.model.User;
import tcf.com.tcp.curriculumdesign2.utils.OkhttpUtils;
import tcf.com.tcp.curriculumdesign2.utils.RegisterUtil;
import tcf.com.tcp.curriculumdesign2.utils.WebApiUtils;

public class RegisterActivity extends AppCompatActivity {
    EditText et_name, et_phone, et_password, et_password2;
    RadioGroup rg_sex;
    RadioButton rb_male, rb_female;
    Button bt_cancel, bt_reg;
    TextView tip_name, tip_phone, tip_password, tip_password2, tip_sex;
    Boolean name, phone, password, sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = RegisterUtil.judgeName(et_name.getText().toString());
                phone = RegisterUtil.isPhoneNo(et_phone.getText().toString());
                password = RegisterUtil.judgePassword(et_password.getText().toString()
                        , et_password2.getText().toString());
                sex = (rb_male.isChecked() || rb_female.isChecked());
                if(!name){
                    tip_name.setText("请输入姓名");
                }
                if(!phone){
                    tip_phone.setText("请输入正确的手机号");
                }
                if(TextUtils.isEmpty(et_password.getText().toString())){
                    tip_password.setText("请输入密码");
                }
                if(TextUtils.isEmpty(et_password2.getText().toString())){
                    tip_password.setText("请再次输入密码");
                }
                if(!password){
                    tip_password.setText("两次密码不一致");
                    tip_password2.setText("两次密码不一致");
                }
                if(!sex){
                    tip_sex.setText("请选择你的性别");
                }
                if(name&&phone&&password&&sex){
                    tip_name.setText("");
                    tip_phone.setText("");
                    tip_password.setText("");
                    tip_password2.setText("");
                    tip_sex.setText("");
                    String params = String.format("{\n" +
                            "    \"Privacy\": {\n" +
                            "        \"phone\": %s\n" +
                            "    }\n" +
                            "}", et_phone.getText().toString());
                    String url = WebApiUtils.getBaseUrl() + "/get";
                    OkhttpUtils.getApiDataAsync(RegisterActivity.this, url, params, new OkhttpUtils.OnReadFinishedListener() {
                        @Override
                        public void onFinished(String readOutList) {
                            JSONObject object = JSONObject.parseObject(readOutList);
                            Privacy privacy = object.getObject("Privacy", Privacy.class);
                            if(privacy == null){
                                @SuppressLint("DefaultLocale") String params2 = String.format("{\n" +
                                                "    \"tag\": \"register\",\n" +
                                                "    \"User\": {\n" +
                                                "        \"sex\": %d,\n" +
                                                "        \"name\": \"%s\",\n" +
                                                "        \"identity\": %d\n" +
                                                "    },\n" +
                                                "    \"Privacy\": {\n" +
                                                "        \"phone\": %d,\n" +
                                                "        \"_password\": \"%s\",\n" +
                                                "        \"balance\": %f\n" +
                                                "    }\n" +
                                                "}", rb_male.isChecked()?0:1, et_name.getText().toString(),
                                        0, Long.valueOf(et_phone.getText().toString()), et_password.getText().toString(), 0.0);
                                WebApiUtils.postData(RegisterActivity.this, params2, new WebApiUtils.OnClassFinishedListener<String>() {
                                    @Override
                                    public void onFinished(String out) {
                                        showToast("注册成功");
                                        finish();
                                    }

                                    @Override
                                    public void onFail(String e) {
                                        showToast("注册失败");
                                    }
                                });
                            }else{
                                showToast("该手机号已注册");
                            }
                        }

                        @Override
                        public void onFail(String e) {

                        }
                    });
                }
            }
        });
    }

    private void initView() {
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        et_password2 = findViewById(R.id.et_password2);
        rg_sex = findViewById(R.id.sex);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);
        bt_cancel = findViewById(R.id.bt_cancel);
        bt_reg = findViewById(R.id.bt_reg);
        tip_name = findViewById(R.id.tip_name);
        tip_phone = findViewById(R.id.tip_phone);
        tip_password = findViewById(R.id.tip_password);
        tip_password2 = findViewById(R.id.tip_password2);
        tip_sex = findViewById(R.id.tip_sex);
    }

    private void showToast(String e){
        Toast.makeText(this, e, Toast.LENGTH_LONG).show();
    }
}