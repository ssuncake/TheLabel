package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadImageFragment extends Fragment {
@BindView(R.id.imageView_uploadImage)
    ImageView imageView;
    public UploadImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_image, container, false);
        ButterKnife.bind(this,view);

        imageView.setImageResource(R.drawable.profile_girl);


        return view;
    }

}
