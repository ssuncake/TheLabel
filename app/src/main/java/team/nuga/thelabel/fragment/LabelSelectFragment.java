package team.nuga.thelabel.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.LabelMakeActivity;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.LabelSelectRequest;
import team.nuga.thelabel.wiget.LabelSelectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelSelectFragment extends Fragment  {

    List<Label> userlabellist;



    public LabelSelectFragment() {
        // Required empty public constructor
    }


    @BindView(R.id.layout_LabelSelectMain)
    LinearLayout linearLayout;


    User user;
    List<Label> labels;
    Button[] buttons;
    LabelSelectView[] labelSelectViews = new LabelSelectView[3];
    LabelSelectView labelView;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_label_select, container, false);
        ButterKnife.bind(this,view);

        user = (User)getArguments().getSerializable(MainActivity.MAINUSER);



        for(int i=0 ; i<3 ; i++){

            labelView = new LabelSelectView(getContext(),i);
            labelSelectViews[i] = labelView;
        }

        linearLayout.addView(labelSelectViews[0]);
        linearLayout.addView(labelSelectViews[1]);
        linearLayout.addView(labelSelectViews[2]);


        LabelSelectRequest request = new LabelSelectRequest(getContext());
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<Label[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<Label[]>> request, NetworkResult<Label[]> result) {
                Label[] labels = result.getData();
                if(labels!=null) {
                    for (Label l : labels) {
                        user.addLabelList(l);
                    }
                    for (int i = 0; i < 3; i++) {
                        if (i < user.getUserInLabelList().size()) {
                            labelSelectViews[i].setLabel(user.getUserInLabelList().get(i));
                        }

                        labelSelectViews[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.e("레이블 클릭", "뷰클릭");
                                LabelSelectView l = (LabelSelectView) view;
                                if (l.getEmpty()) {
                                    Log.e("레이블 클릭", "엠티 : " + l.getEmpty());
                                    makeLabel();
                                } else {
                                    Log.e("레이블 클릭", "레이블 이름 " + l.getLabel().getLabelName());
                                    selectLabel(l.getLabel());
                                }
                            }
                        });
                    }
                    Log.e("레이블 클릭","labels size : "+ labels.length + " user labelsize : "+ user.getUserInLabelList().size());
                }else{
                    for (int i = 0; i < 3; i++) {
                        labelSelectViews[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                makeLabel();
                            }
                        });
                    }
                }

            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<Label[]>> request, int errorCode, String errorMessage, Throwable e) {
                Log.e("레이블 선택","불러오기 실패");
            }
        });


        return view;
    }

    public void selectLabel(Label label){
        LabelContainerFragment parent  = (LabelContainerFragment)getParentFragment();
        parent.selectLabel(label);

        //프레그먼트 교체가 부모프레그먼트에서 이루어져야 하기때문에 부모 프래그먼트를 getParentFragment로 호출하여
        // selectLabel을 호출한다.
    }

    public void makeLabel(){
        Intent intent = new Intent(getActivity(), LabelMakeActivity.class);
        intent.putExtra(MainActivity.MAINUSER,user);
        startActivityForResult(intent,MainActivity.REQUEST_MAKELABEL);
    }

    public void buttonSetting(User user){
        if(user.getUserInLabelList()!=null)
        {
            int size = user.getUserInLabelList().size();
            this.user = user;
            for(int i =0; i<size; i++){
                buttons[i].setText(user.getUserInLabelList().get(i).getLabelName().toString());
            }
        }
    }


}
