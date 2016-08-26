package team.nuga.thelabel.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.PasswordSettingActivity;
import team.nuga.thelabel.PushAlramSettingActivity;
import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    @BindView(R.id.button_myimpormaton)
    Button impormationbutton;
    @BindView(R.id.button_alram_setting)
    Button alrambutton;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @OnClick(R.id.button_myimpormaton)
    public void impormationClick(View v){
        Intent intent = new Intent(getActivity(), PasswordSettingActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.button_alram_setting)
    public void alramClick(View v){
        Intent intent = new Intent(getActivity(), PushAlramSettingActivity.class);
        startActivity(intent);
    }

}
