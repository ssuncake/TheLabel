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
public class AccountTypeMusicViewHolder extends ParentContentsViewHolder implements View.OnClickListener, View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {
    TextView titleMusicView;
    private ImageView imageViewMenu;


    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
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
                break;
        }
        return true;
    }

    public interface OnMusicContentsItemClick{
        void onMusicContentItemClick(View view, Contents contents, int adapterPosition);
    }

    OnMusicContentsItemClick musicContentslist;
    public void setOnMusicContentsItemClickListener(OnMusicContentsItemClick musiclistener){
        this.musicContentslist = musiclistener;
    }

    public AccountTypeMusicViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicContentslist!=null){
                    musicContentslist.onMusicContentItemClick(view, contents, getAdapterPosition());
                }
                Log.v("Music","test success");
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
    public void setMusicContents(Contents contents){
        this.contents = contents;
        titleMusicView.setText(contents.getContentsText());
    }
//    public void setContents (Contents contents){
//        Log.w("뷰타입" , ""+contents.getContentsType());
//
//    }
}
