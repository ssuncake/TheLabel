package team.nuga.thelabel.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyLikeContentsFragment extends Fragment {


    public MyLikeContentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_like_contents, container, false);
    }

}
