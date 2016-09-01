package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Contents;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class CardViewHolder extends RecyclerView.ViewHolder {
//    @BindView(R.id.cardView_profileImage)
    ImageView writer_profileImage;
//    @BindView(R.id.textView_cardView_profileId)
    TextView writer_Id;
//    @OnClick(R.id.textView_cardView_profileId)

//    @BindView(R.id.seekBar_cardView)
    SeekBar seekBar;    //    @BindView(R.id.) //재생버튼
    ToggleButton playButton;
//    @BindView(R.id.imageButton_share_cardView)
    ImageButton imageButton;

    public CardViewHolder(final View itemView) {
        super(itemView);
    writer_Id = (TextView)itemView.findViewById(R.id.textView_cardView_profileId);

    }

    Contents contents;
    public void setContent(Contents content) {
        this.contents = content;
//        writer_profileImage.setImageDrawable(contents.getContentsWriterID());
//        writer_profileImage.setImageResource(R.drawable.profile_girl); //dummy profile Image...
//        writer_Id.setText(contents.getWriterNickName());
    }

    public void onWriterIdClick(){
//        Intent intent = new Intent(, OtherUserActivity.class);
    }
}