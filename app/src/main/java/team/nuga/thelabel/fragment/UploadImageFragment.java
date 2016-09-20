package team.nuga.thelabel.fragment;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;


public class UploadImageFragment extends Fragment {
    @BindView(R.id.imageView_uploadImage)
    ImageView imageView;

    public UploadImageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_image, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        if(bundle!=null){
            try {    String dataUri = bundle.getString("dataUri");
                Uri uri = Uri.parse(dataUri);
                Log.e("gallery","이미지버튼으로부터 가져오기 성공"+uri.toString());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            imageView.setImageResource(R.drawable.img_user);
        }

        return view;
    }

}
