package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import team.nuga.thelabel.R;
import team.nuga.thelabel.adapter.ContentsListAdapter;
import team.nuga.thelabel.data.Contents;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyLikeContentsFragment extends Fragment {
    ArrayList<Contents> mContents;
    ContentsListAdapter mAdapter;

    public MyLikeContentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_like_contents, container, false);
        RecyclerView mylikerecyclerview = (RecyclerView)view.findViewById(R.id.recyclerview_my_like_contents);
        mContents = new ArrayList<>();
        mylikerecyclerview.setHasFixedSize(true);
        mAdapter= new ContentsListAdapter(mContents);

        dummysetting();

        mylikerecyclerview.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
       linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mylikerecyclerview.setLayoutManager(linearLayoutManager);
        return view;
    }

    public void dummysetting(){
        Contents contents = new Contents();
        contents.setContentsText("구혜빈");
        mAdapter.add(contents);
    }

}
