package team.nuga.thelabel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.viewholder.LabelMainMemberViewHolder;

/**
 * Created by Tacademy on 2016-09-02.
 */
public class LabelMainListAdapter extends RecyclerView.Adapter<LabelMainMemberViewHolder> {

    List<User> list = new ArrayList();

    public void addUser(User user){
        list.add(user);
        notifyDataSetChanged();
    }

    @Override
    public LabelMainMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_labelmain_memberlist,parent,false);
        LabelMainMemberViewHolder holder = new LabelMainMemberViewHolder(view);
        // 여기에 리스너등록

        return holder;
    }

    @Override
    public void onBindViewHolder(LabelMainMemberViewHolder holder, int position) {
        holder.setUser(list.get(position));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
