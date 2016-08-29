package team.nuga.thelabel.viewholder;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.User;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class EntrustLeaderViewHolder extends RecyclerView.ViewHolder {
    TextView entrustleaderView;
    Button entrustleaderbutton;

    public interface OnEntrustLeaderItemClickListener{

        void onUserItemClick(View view, User user, int adapterPosition);
    }

    OnEntrustLeaderItemClickListener userlistener;
    public void setOnMemberListUserItemClickListener(OnEntrustLeaderItemClickListener listener){
        this.userlistener = userlistener;
    }
    public EntrustLeaderViewHolder(final View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userlistener !=null){
                    userlistener.onUserItemClick(view, user, getAdapterPosition());
                }
            }
        });
        entrustleaderView = (TextView)itemView.findViewById(R.id.text_username);
        entrustleaderbutton = (Button)itemView.findViewById(R.id.button_etrustleader) ;
        entrustleaderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(itemView.getContext())
                        .setMessage("홍길동 님에게"+"\n"+"대표권한을 위임하겠습니까?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });
    }
    User user;
    public void setUser(User user){
        this.user = user;
        entrustleaderView.setText(user.getUserName());
    }
}
