package team.nuga.thelabel.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.ChatContract;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.viewholder.MessageViewHolder;


public class MessageMemberAdapter extends RecyclerView.Adapter<MessageViewHolder> implements MessageViewHolder.OnMessageItemClickListener{

    Cursor cursor;
    Context context;
    String username;
    String image;

    User tempUser;
    MessageViewHolder tempHolder;
    public void changeCursor(Cursor c){
        if(cursor != null){
            cursor.close();
        }
        cursor =c;
        notifyDataSetChanged();
    }




    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_messagelist, parent, false);
        context = parent.getContext();
        return  new MessageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        cursor.moveToPosition(position);

        int id = cursor.getInt(cursor.getColumnIndex(ChatContract.ChatUser.OTHER_ID));
        String name = cursor.getString(cursor.getColumnIndex(ChatContract.ChatUser.OTHER_NAME));
        String image = cursor.getString(cursor.getColumnIndex(ChatContract.ChatUser.OTHER_IMAGE));

        User user = new User();
        user.setUserID(id);
        user.setUserName(name);
        user.setImageUrl(image);
        holder.setData(name,image);
        holder.setUser(user);
        holder.setOnMessageItemClickListener(this);




      }


    @Override
    public int getItemCount() {
        if(cursor==null)
            return 0;
        return cursor.getCount();
    }

    public interface OnAdapterItemClickLIstener {
        public void onAdapterItemClick(View view,User user, int position);
    }

    OnAdapterItemClickLIstener listener;

    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }

    @Override
    public void onMessageItemClick(View view, User user,int position) {
        if(listener != null){
            listener.onAdapterItemClick(view,user,position);
        }
    }
}
