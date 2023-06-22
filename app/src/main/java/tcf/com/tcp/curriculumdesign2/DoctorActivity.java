package tcf.com.tcp.curriculumdesign2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class DoctorActivity extends AppCompatActivity {
    LinearLayout my_schedule, my_reserve, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        my_schedule = findViewById(R.id.doctor_my_schedule);
        my_reserve = findViewById(R.id.doctor_my_reserve);
        logout = findViewById(R.id.logout);
        my_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorActivity.this, DoctorScheduleActivity.class);
                startActivity(i);
            }
        });
        my_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}