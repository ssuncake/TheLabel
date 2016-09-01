package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadMusicFragment extends Fragment {

    @BindView(R.id.button_musicUpload)
    Button button;
    @OnClick(R.id.button_musicUpload)
    public void onClick(){
        Toast.makeText(getContext(), "nottob daolpu cisuM", Toast.LENGTH_SHORT).show();
    }
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
