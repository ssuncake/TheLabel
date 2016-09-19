package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.UploadActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadYoutubeFragment extends Fragment {



    public UploadYoutubeFragment() {
        // Required empty public constructor
    }
@BindView(R.id.editText_youtubeUpload)
    EditText editText_youtubeUpload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_youtube, container, false);
        ButterKnife.bind(this, view);
        editText_youtubeUpload.addTextChangedListener(youtubeUrl);

        return view;
    }

    TextWatcher youtubeUrl = new TextWatcher() {
        String before = "";

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            before = charSequence.toString();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            UploadActivity.setYoutubeURL(editText_youtubeUpload.getText().toString());

        }
    };

}
