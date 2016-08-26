package team.nuga.thelabel.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import team.nuga.thelabel.Adapther.SearchLabelResultListAdapter;
import team.nuga.thelabel.Data.Label;
import team.nuga.thelabel.OtherLabelActivity;
import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelSearchFragment extends Fragment {
    RecyclerView listView;
    SearchLabelResultListAdapter labeladapter;

    public LabelSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_label_search, container, false);
        listView = (RecyclerView)view.findViewById(R.id.recyclerview_label_search);
        labeladapter = new SearchLabelResultListAdapter();
        labeladapter.setOnAdapterItemClickListener(new SearchLabelResultListAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, Label user, int position) {
                Intent intent = new Intent(getActivity(),OtherLabelActivity.class);
                intent.putExtra("other",position+"님의 계정입니다.");
                startActivity(intent);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(manager);
        listView.setAdapter(labeladapter);
        initData();
        return view;
    }
    private void initData(){
        Random r = new Random();
        for (int i = 0; i<3; i++){
            Label label = new Label();
            label.setLabelName("other "+i);
            labeladapter.add(label);
        }

    }
}
