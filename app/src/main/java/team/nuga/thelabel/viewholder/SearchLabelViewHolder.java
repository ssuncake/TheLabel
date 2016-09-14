package team.nuga.thelabel.viewholder;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.RoundImageTransform;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class SearchLabelViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_search_labelimage)
    ImageView labelPhotoView;
    @BindView(R.id.textView_search_labelName)
    TextView labelNameView;
    @BindView(R.id.textView_search_label_position)
    TextView labelPosition;
    @BindView(R.id.textView_search_label_genre)
    TextView labelGenre;

    public interface OnSearchLabelItemClickListener{
        public void onLabelItemClick(View view, Label label, int position);
    }
    OnSearchLabelItemClickListener listener;
    public void setOnSearchItemClickListener(OnSearchLabelItemClickListener listener){
        this.listener = listener;
    }
    public SearchLabelViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener !=null){
                    listener.onLabelItemClick(view, label, getAdapterPosition());
                }
            }
        });
    }
    Label label;
    public void setLabel(Label label){
        this.label = label;
        labelNameView.setText(label.getSearchLabelName());
        labelGenre.setText(label.getSearchLabelGenre());
        labelPosition.setText(label.getSearchLabelPosition());
        Glide.with(labelPhotoView.getContext())
                .load(label.getSearchLabelImage())
                .into(labelPhotoView);
    }
}
