package tcf.com.tcp.curriculumdesign2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tcf.com.tcp.curriculumdesign2.data.CommonStaticData;
import tcf.com.tcp.curriculumdesign2.server.model.LoginUser;
import tcf.com.tcp.curriculumdesign2.server.model.Privacy;
import tcf.com.tcp.curriculumdesign2.server.model.User;
import tcf.com.tcp.curriculumdesign2.utils.WebApiUtils;

public class LoginActivity extends AppCompatActivity {
    String BASE_URL = "http://192.168.43.229:8080";
    EditText et_act, et_pwd;
    Button bt_check, bt_reg, bt_forget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        WebApiUtils.setBaseUrl(BASE_URL);
        initView();
        String account = "13000038710";
        String pwd = "666666";
        LoginUser loginUser = new LoginUser(account, pwd);
        WebApiUtils.loginWithPwd(LoginActivity.this, loginUser, new WebApiUtils.OnClassFinishedListener<User>() {
            @Override
            public void onFinished(User out) {
            }

            @Override
            public void onFail(String e) {
                showToast(e);
            }
        });
    }

    private void initView() {
        et_act = findViewById(R.id.Edit_account);
        et_pwd = findViewById(R.id.Edit_password);
        bt_check = findViewById(R.id.login_bt_check);
        bt_reg = findViewById(R.id.login_bt_register);
        bt_forget = findViewById(R.id.login_bt_forget);
        bt_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = et_act.getText().toString();
                String pwd = et_pwd.getText().toString();
                LoginUser loginUser = new LoginUser(account, pwd);
                WebApiUtils.loginWithPwd(LoginActivity.this, loginUser, new WebApiUtils.OnClassFinishedListener<User>() {
                    @Override
                    public void onFinished(User out) {
                        CommonStaticData.authUser = out;
                        WebApiUtils.getUserPrivacy(LoginActivity.this, out.getId(), new WebApiUtils.OnClassFinishedListener<Privacy>() {
                            @Override
                            public void onFinished(Privacy out) {
                                CommonStaticData.privacy = out;
                            }

                            @Override
                            public void onFail(String e) {

                            }
                        });
                        Calendar cal = Calendar.getInstance();
                        Date date = cal.getTime();
                        CommonStaticData.today = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        CommonStaticData.sevenDays = getSevenDate();
                        try {
                            CommonStaticData.sevenWeekDay = getWeekOfDate(new SimpleDateFormat("yyyy-MM-dd").parse(CommonStaticData.sevenDays.get(0)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Intent i;
                        if(CommonStaticData.authUser.getIdentity().equals("0")){
                            i = new Intent(LoginActivity.this, MainActivity.class);
                        } else{
                            i = new Intent(LoginActivity.this, DoctorActivity.class);
                        }
                        startActivity(i);
                    }

                    @Override
                    public void onFail(String e) {
                        showToast(e);
                    }
                });
            }
        });
        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    private String getFutureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(today);
    }

    private List<String> getSevenDate() {
        List<String> sevenDateList = new ArrayList<>();
        for (int i = 0; i < 7 ; i++) {
            sevenDateList.add(getFutureDate(i));
        }
        return sevenDateList;
    }

    private List<String> getWeekOfDate(Date date) {
        List<String> sevenWeekDayList = new ArrayList<>();
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) w = 0;
        for (int i = 0; i < 7; i++) {
            sevenWeekDayList.add(weekDays[(w+i)%7]);
        }
        return sevenWeekDayList;
    }

    private void showToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}