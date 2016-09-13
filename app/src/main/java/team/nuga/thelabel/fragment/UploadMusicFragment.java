package team.nuga.thelabel.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.MusicListActivity;
import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadMusicFragment extends Fragment {

   @BindView(R.id.textView_MusicUpload_FilePath)
    TextView filePath;

    public UploadMusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_music, container, false);
        ButterKnife.bind(this, view);
        if(getArguments().getString("MusicTitle")!=null){
            String musicTitle = getArguments().getString("MusicTitle");
            filePath.setText(musicTitle);
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivityForResult(new Intent(getActivity(), MusicListActivity.class), MainActivity.REQUEST_MUSICUPLOAD);
            }
        });
        return view;
    }


}
