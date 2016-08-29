package team.nuga.thelabel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.viewholder.ContentsViewHolder;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class ContentsListAdapter extends RecyclerView.Adapter<ContentsViewHolder>{
    List<Contents> contentsList = new ArrayList<>();

    public ContentsListAdapter(List<Contents> contentsList){
        this.contentsList = contentsList;
    }
    public void add(Contents contents) {
        contentsList.add(contents);
        notifyDataSetChanged();
    }
    @Override
    public ContentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_my_like_contents,parent,false);
        return new ContentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentsViewHolder holder, int position) {
        Contents cl = contentsList.get(position);
        holder.textView.setText(cl.getContentsText());
//        holder.imageView.setImageResource(R.drawable.profile_girl);
    }

    @Override
    public int getItemCount() {
        return contentsList.size();
    }
}
