package team.nuga.thelabel.adapter;


import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import team.nuga.thelabel.R;
import team.nuga.thelabel.Utils;
import team.nuga.thelabel.data.ChatContract;
import team.nuga.thelabel.viewholder.MessageMeViewHolder;
import team.nuga.thelabel.viewholder.MessageOtherViewHolder;
import team.nuga.thelabel.viewholder.MessageParentViewHolder;

/**
 * Created by Tacademy on 2016-09-06.
 */
public class MessageListCursorAdapter extends RecyclerView.Adapter<MessageParentViewHolder>{

    String UserImagePath;
    Cursor cursor;
    public void changeCursor(Cursor c){
        if(cursor != null){
            cursor.close();
        }
        cursor =c;
        notifyDataSetChanged();
    }

    private static final int VIEW_TYPE_SEND = 1;
    private static final int VIEW_TYPE_RECEIVE = 2;
    private static final int VIEW_TYPE_DATELINE = 3;

    @Override
    public int getItemViewType(int position) {
        cursor.moveToPosition(position);
        int type = cursor.getInt(cursor.getColumnIndex(ChatContract.ChatMessage.COLUMN_TYPE));
        switch (type) {
            case ChatContract.ChatMessage.TYPE_SEND :
                return VIEW_TYPE_SEND;
            case ChatContract.ChatMessage.TYPE_RECEIVE :
                return VIEW_TYPE_RECEIVE;
        }
        throw new IllegalArgumentException("invalid type");
    }


    @Override
    public MessageParentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_SEND : {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_message_me, parent, false);
                return  new MessageMeViewHolder(view);
            }
            case VIEW_TYPE_RECEIVE : {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_message_other, parent, false);
                return new MessageOtherViewHolder(view);
            }
        }
        return null;
    }




    @Override
    public void onBindViewHolder(MessageParentViewHolder holder, int position) {
        cursor.moveToPosition(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_SEND : {
                MessageMeViewHolder mvh = (MessageMeViewHolder)holder;
                String message = cursor.getString(cursor.getColumnIndex(ChatContract.ChatMessage.COLUMN_MESSAGE));
                String date1= cursor.getString(cursor.getColumnIndex(ChatContract.ChatMessage.COLUMN_CREATED));
                long date2 = Long.parseLong(date1);
                Date date = new Date();
                date.setTime(date2);
                mvh.setMessage(message,Utils.convertTimeToStringm(date));
                break;
            }
            case VIEW_TYPE_RECEIVE :{
                MessageOtherViewHolder ovh = (MessageOtherViewHolder)holder;
                String message = cursor.getString(cursor.getColumnIndex(ChatContract.ChatMessage.COLUMN_MESSAGE));
                String date1= cursor.getString(cursor.getColumnIndex(ChatContract.ChatMessage.COLUMN_CREATED));
                long date2 = Long.parseLong(date1);
                Date date = new Date();
                date.setTime(date2);
                ovh.setMessage(message,Utils.convertTimeToStringm(date));
                ovh.setImage(UserImagePath);
                break;
            }
        }


    }


    @Override
    public int getItemCount() {
        if(cursor==null)
            return 0;
        return cursor.getCount();
    }

    public void setUserImagePath(String path){
        this.UserImagePath = path;
    }
}
