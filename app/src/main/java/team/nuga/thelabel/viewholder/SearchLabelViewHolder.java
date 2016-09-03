package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.R;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class SearchLabelViewHolder extends RecyclerView.ViewHolder {
    TextView labelnameView;

    public interface OnSearchLabelItemClickListener{
        public void onLabelItemClick(View view, Label label, int position);
    }
    OnSearchLabelItemClickListener listener;
    public void setOnSearchItemClickListener(OnSearchLabelItemClickListener listener){
        this.listener = listener;
    }
    public SearchLabelViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener !=null){
                    listener.onLabelItemClick(view, label, getAdapterPosition());
                }
            }
        });
        labelnameView = (TextView)itemView.findViewById(R.id.textView_MemberList_Name);
    }
    Label label;
    public void setLabel(Label label){
        this.label = label;
        labelnameView.setText(label.getLabelName());
    }
}
