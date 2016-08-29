package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.adapter.CardViewAdapter;
import team.nuga.thelabel.data.MusicContents;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedFragment extends Fragment {

    CardViewAdapter cardViewAdapter;

//   MyAdapter myAdapter;

    @BindView(R.id.recyclerview_newsfeed)
    RecyclerView recyclerView;

    public NewsFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_feed, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);

            cardViewAdapter = new CardViewAdapter();
        recyclerView.setAdapter(cardViewAdapter);



        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);



//        myAdapter = new MyAdapter();

//        mDataset = new ArrayList<>();


//        recyclerView.setAdapter(myAdapter);
        initData();
        return view;

    }

    private void initData() {
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            MusicContents u = new MusicContents();
            u.setWriterNickName("nick" + i);
            cardViewAdapter.add(u);
        }

    }

}
