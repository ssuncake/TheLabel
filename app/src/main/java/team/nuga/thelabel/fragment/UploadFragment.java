package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.UploadActivity;
import team.nuga.thelabel.data.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadFragment extends Fragment {

    public static final String UPLOADMODE = "UploadMode";
    User user;



    @OnClick(R.id.linearLayout_UploadFragment_MusicUpload)
    public void ClickMusicUpload(){
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.startUploadActivity(UploadActivity.MUSIC);
    }
    @OnClick(R.id.linearLayout_UploadFragment_PictureUpload)
    public void ClickPhotoUpload(){
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.startUploadActivity(UploadActivity.PHOTO);
    }
    @OnClick(R.id.linearLayout_UploadFragment_YoutubeUpload)
    public void ClickYoutubeUpload(){
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.startUploadActivity(UploadActivity.YOUTUBE);
    }



    public UploadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        ButterKnife.bind(this,view);
        user = (User)getArguments().getSerializable(MainActivity.MAINUSER);


        return view;
    }

}
