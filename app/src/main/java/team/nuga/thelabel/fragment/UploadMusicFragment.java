package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadMusicFragment extends Fragment {

   @BindView(R.id.textView_MusicUpload_FilePath)


    public UploadMusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_music, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
