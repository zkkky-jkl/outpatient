package tcf.com.tcp.curriculumdesign2.ui.myself;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import tcf.com.tcp.curriculumdesign2.MyReserveActivity;
import tcf.com.tcp.curriculumdesign2.R;
import tcf.com.tcp.curriculumdesign2.data.CommonStaticData;


public class MyselfFragment extends Fragment {
    ImageView userHead;
    TextView tv_name, tv_phone, tv_certify;
    ImageButton ib_certify;
    LinearLayout card, my_reserve, examination, bill, complain, about, logout;
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_myself, container, false);
        initView(v);
        getMyInfo();
        return v;
    }

    private void initView(View v){
        userHead = v.findViewById(R.id.myself_userHead_iv);
        tv_name = v.findViewById(R.id.myself_userName_tv);
        tv_phone = v.findViewById(R.id.myself_userPhone_tv);
        tv_certify = v.findViewById(R.id.myself_tv_certify);
        ib_certify = v.findViewById(R.id.myself_ib_certify);
        card = v.findViewById(R.id.myself_LinearLayout_card);
        my_reserve = v.findViewById(R.id.myself_LinearLayout_reserve);
        examination = v.findViewById(R.id.myself_LinearLayout_examination);
        bill = v.findViewById(R.id.myself_LinearLayout_bill);
        complain = v.findViewById(R.id.myself_LinearLayout_complain);
        about = v.findViewById(R.id.myself_LinearLayout_about);
        logout = v.findViewById(R.id.myself_LinearLayout_logout);
        my_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getActivity(), MyReserveActivity.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private void getMyInfo(){
        Glide.with(getContext()).load(CommonStaticData.authUser.getHead()).into(userHead);
        tv_name.setText(CommonStaticData.authUser.getName());
        tv_phone.setText(CommonStaticData.privacy.getPhone());
        if(CommonStaticData.privacy.getCertified() == 1){
            tv_certify.setText("已认证");
            ib_certify.setVisibility(View.GONE);
        }
    }
}