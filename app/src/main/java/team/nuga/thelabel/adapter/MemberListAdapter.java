package team.nuga.thelabel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Member;
import team.nuga.thelabel.viewholder.MemberListViewHolder;

/**
 * Created by 우리집 on 2016-09-03.
 */
public class MemberListAdapter extends RecyclerView.Adapter<MemberListViewHolder> {

    List<Member> list = new ArrayList<>();

    public void add(Member m){
        list.add(m);
        notifyDataSetChanged();
    }

    @Override
    public MemberListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_memberlist,parent,false);
        MemberListViewHolder holder = new MemberListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MemberListViewHolder holder, int position) {
        holder.setMember(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
