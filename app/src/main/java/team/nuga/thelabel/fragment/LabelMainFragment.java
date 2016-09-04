package team.nuga.thelabel.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.LabelSettingActivity;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.MemberListActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.adapter.LabelMainListAdapter;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.Member;
import team.nuga.thelabel.data.NetworkResultLabeMain;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.GetLabelByIdMainRequest;
import team.nuga.thelabel.wiget.LabelMainTop;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelMainFragment extends Fragment {


    private String labelName;
    Label label;
    LabelMainListAdapter adapter;
    Member[] members;



    @BindView(R.id.view_LabelMainTop)
    LabelMainTop labelMainTop;
    @BindView(R.id.recyclerView_LabelMain_Member)
    RecyclerView memberRecycler;

    @OnClick(R.id.button_LabelMain_back)
    public void clickBackButton(){
        team.nuga.thelabel.fragment.LabelContainerFragment parent  = (team.nuga.thelabel.fragment.LabelContainerFragment)getParentFragment();
        parent.backSelectLabel();
        // 레이블선택으로 돌아가기위해 역시 부모 프래그먼트를 얻어와 메소드를 실행
    }
    @OnClick(R.id.button_LabelMain_goSetting)
    public void clickSettingButton(){
        Intent intent = new Intent(getActivity(), LabelSettingActivity.class);
        intent.putExtra(MainActivity.SELECTLABEL,label);
        startActivityForResult(intent,MainActivity.REQUEST_SETTINGLABEL);
    }

    @OnClick(R.id.button_LabelMain_Memberlist)
    public void clickMemberList(){
        Intent intent = new Intent(getActivity(), MemberListActivity.class);
        intent.putExtra(MainActivity.LABELID,label.getLabel_id());
        startActivity(intent);
    }



    public LabelMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_label_main, container, false);
        ButterKnife.bind(this,view);

        int id = getArguments().getInt(MainActivity.LABELID);

        GetLabelByIdMainRequest request = new GetLabelByIdMainRequest(getActivity(),id);

        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResultLabeMain>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResultLabeMain> request, NetworkResultLabeMain result) {
                Log.e("레이블 메인","page "+result.getPage()+"  count "+result.getCount());

                label = result.getResult();
                labelMainTop.setLabel(label);

                adapter = new LabelMainListAdapter();
                memberRecycler.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                memberRecycler.setLayoutManager(manager);
                members = result.getMember();
                memberSetting();


//                dummyAddmember();



            }

            @Override
            public void onFail(NetworkRequest<NetworkResultLabeMain> request, int errorCode, String errorMessage, Throwable e) {
                Log.e("레이블 메인","레이블 찾기 실패 : "+errorMessage);
            }
        });




        return view;

    }

//    public void dummyAddmember(){
//        for(int i=0;i<30;i++){
//            User user = new User();
//            user.setUserName("유저 "+i);
//            adapter.addUser(user);
//        }
//    }

    public void memberSetting(){
        for(Member m : members)
            adapter.addUser(m);
    }



}
