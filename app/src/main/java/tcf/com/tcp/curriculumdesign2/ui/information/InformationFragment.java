package tcf.com.tcp.curriculumdesign2.ui.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import tcf.com.tcp.curriculumdesign2.MainActivity;
import tcf.com.tcp.curriculumdesign2.R;
import tcf.com.tcp.curriculumdesign2.TemporaryActivity;


public class InformationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_information, container, false);
        LinearLayout new1 = v.findViewById(R.id.new1);
        LinearLayout new2 = v.findViewById(R.id.new2);
        new1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TemporaryActivity.class);
                startActivity(i);
            }
        });
        new2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TemporaryActivity.class);
                startActivity(i);
            }
        });
        return v;
    }
}