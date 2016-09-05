package team.nuga.thelabel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Message;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.viewholder.MessageDateLineViewHolder;
import team.nuga.thelabel.viewholder.MessageMeViewHolder;
import team.nuga.thelabel.viewholder.MessageOtherViewHolder;
import team.nuga.thelabel.viewholder.MessageParentViewHolder;


/**
 * Created by Tacademy on 2016-09-05.
 */
public class MessageMainAdapter extends RecyclerView.Adapter<MessageParentViewHolder> {

    List<Message> list = new ArrayList<>();
    User user;

    public void add(Message m){
        list.add(m);
        notifyDataSetChanged();
    }
    public void setUser(User user){
        this.user = user;
    }


    @Override
    public int getItemViewType(int position) {

        int viewType = list.get(position).getMessageType();
        return viewType;
    }

    public MessageParentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType){
            case Message.ME :
                view = layoutInflater.inflate(R.layout.view_message_me,parent,false);
                MessageMeViewHolder mholder = new MessageMeViewHolder(view);
                return mholder;
            case Message.OTHER :
                view = layoutInflater.inflate(R.layout.view_message_other,parent,false);
                MessageOtherViewHolder oholder = new MessageOtherViewHolder(view);
                return oholder;
            default:
                view = layoutInflater.inflate(R.layout.view_message_dateline,parent,false);
                MessageDateLineViewHolder dholder = new MessageDateLineViewHolder(view);
                return dholder;

        }
    }


    @Override
    public void onBindViewHolder(MessageParentViewHolder holder, int position) {
        switch (list.get(position).getMessageType()){
            case Message.ME :
                holder.setMessage(list.get(position));
                break;

            case Message.OTHER :
                holder.setMessage(list.get(position));
                // setimage 사용해서 사진설정
                break;
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

}
