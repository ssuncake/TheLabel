package team.nuga.thelabel.viewholder;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
    public boolean onMenuItemClick(MenuItem item) {     // popup 메뉴로 생긴 메소드 (popup menu에 들어가 있는 item 클릭시 호출 됨)
        switch (item.getItemId()){
            case R.id.contents_change:
                Toast.makeText(itemView.getContext(), "Contents Change", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contents_delete:
                new AlertDialog.Builder(itemView.getContext())
                        .setTitle("삭제 확인")
                        .setMessage("이 게시물을 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
//                Toast.makeText(itemView.getContext(),"Contents Delete", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


    public interface OnPictureContentsItemClick {
        void onPictureContentsItemClick(View view, Contents contents, int adapterPosition);
    }

    OnPictureContentsItemClick pictureContentslist;

    public void setOnPictureContentsItemClickListener(OnPictureContentsItemClick picturelistener) {
        this.pictureContentslist = picturelistener;
    }

    public AccountTypePictureViewHolder(View itemView) {
        super(itemView);
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
        itemView.setOnClickListener(this); //popup listener
        itemView.setOnLongClickListener(this);  //popup listener
        imageViewMenu = (ImageView) itemView.findViewById(R.id.imageView_menu);
        imageViewMenu.setOnClickListener(this); //imageMenu 클릭 시 메뉴 팝업메뉴가 뜨도록
    }

    @Override
    public void onClick(View view) {
        if (view == imageViewMenu) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.contents_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

    }
    Contents contents;

    public void setPictureContent(Contents contents) {
        this.contents = contents;
//        titlePictureView.setText(contents.getContentsText());
    }
}