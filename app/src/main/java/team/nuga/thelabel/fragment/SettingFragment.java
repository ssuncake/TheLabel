package team.nuga.thelabel.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.PasswordSettingActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.TestLoginActivity;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.LogoutRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    @OnClick(R.id.view_Setting_item_logout)
    public void onLogOutClick(){

        LogoutRequest request = new LogoutRequest(getContext());
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult> request, NetworkResult result) {
                Toast.makeText(getContext(), "정상적으로 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), TestLoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult> request, int errorCode, String errorMessage, Throwable e) {
                Log.e("로그아웃 실패","연결상태 점검");
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @OnClick(R.id.view_Setting_item_account)
    public void onAccountClick(){//비밀번호설정액티비티로 이동~!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Intent intent = new Intent(getContext(), PasswordSettingActivity.class);
        startActivity(intent);
    }

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


}
