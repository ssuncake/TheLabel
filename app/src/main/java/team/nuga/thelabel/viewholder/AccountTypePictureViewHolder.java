package team.nuga.thelabel.viewholder;

import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Contents;

/**
 * Created by Tacademy on 2016-08-31.
 */
public class AccountTypePictureViewHolder extends ParentContentsViewHolder implements View.OnClickListener, View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {
    TextView titlePictureView;
    ImageView imageViewPicture;
    private ImageView imageViewMenu;

    @Override
    public boolean onLongClick(View view) {
        return false;
    } //popup 메뉴로 생긴 메소드

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(itemView.getContext(), "ddd", Toast.LENGTH_SHORT).show();
        return true;
    } // popup 메뉴로 생긴 메소드


    public interface OnPictureContentsItemClick {
        void onPictureContentsItemClick(View view, Contents contents, int adapterPosition);
    }

    OnPictureContentsItemClick pictureContentslist;

    public void setOnPictureContentsItemClickListener(OnPictureContentsItemClick picturelistener) {
        this.pictureContentslist = picturelistener;
    }

    public AccountTypePictureViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
//        titlePictureView = (TextView)itemView.findViewById(R.id.textView_username);
        imageViewPicture = (ImageView) itemView.findViewById(R.id.image_contnet_picture);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pictureContentslist != null) {
                    pictureContentslist.onPictureContentsItemClick(view, contents, getAdapterPosition());
                }
                Log.v("Picture", "test success");
            }
        });
        imageViewMenu = (ImageView) itemView.findViewById(R.id.imageView_menu);
        imageViewMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == imageViewMenu) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.contents_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

//        imageViewMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//              //                new AlertDialog.Builder(itemView.getContext())
////                        .setMessage("홍길동 님에게"+"\n"+"대표권한을 위임하겠습니까?")
////                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialogInterface, int i) {
////
////                            }
////                        }).show();
//                itemView.setOnCreateContextMenuListener(this);
//            }
//        });
    }

    Contents contents;

    public void setPictureContent(Contents contents) {
        this.contents = contents;
//        titlePictureView.setText(contents.getContentsText());
    }
}