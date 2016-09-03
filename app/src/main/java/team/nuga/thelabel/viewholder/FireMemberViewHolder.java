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
public class FireMemberViewHolder extends RecyclerView.ViewHolder {
    TextView firememberView;
    Button firememberbutton;

    public interface OnFireMemberListItemClickListener{
        void onUserItemClick(View view, User user, int adapterPosition);
    }

    OnFireMemberListItemClickListener userlistener;
    public void setOnFireMemberListItemClickListener(OnFireMemberListItemClickListener listener){
        this.userlistener = userlistener;
    }
    public FireMemberViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userlistener !=null){
                    userlistener.onUserItemClick(view, user, getAdapterPosition());
                }
            }
        });
        firememberView = (TextView)itemView.findViewById(R.id.textView_MemberList_Name);
        firememberbutton = (Button)itemView.findViewById(R.id.button_subfiremember) ;
        firememberbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setMessage("홍길동 님에게"+"\n"+"탈퇴시키겠습니까?")
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
        firememberView.setText(user.getUserName());
    }
}
