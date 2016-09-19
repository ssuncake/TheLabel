package team.nuga.thelabel.viewholder;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.Debug;
import team.nuga.thelabel.FireMemberActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.RoundImageTransform;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.FireLabelMemberRequest;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class FireMemberViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.textView_FireMember_Name)
    TextView firememberView;
    @BindView(R.id.button_subfiremember)
    Button firememberbutton;
    @OnClick(R.id.button_subfiremember)
    public void onFireClick(final View view) {
        if(Debug.debugmode) Log.i("유저 프사",""+user.getImageUrl());
        if(Debug.debugmode) Log.i("유저 ID ",""+user.getUserID());
        if(Debug.debugmode)Log.i("유저 레이블",""+FireMemberActivity.label.getLabelID());
        final int labelId = FireMemberActivity.label.getLabelID();
        final int LABEL_LEADER = FireMemberActivity.label.getLabelLeaderID();
       if(user.getUserID()!=LABEL_LEADER){
           new AlertDialog.Builder(view.getContext())
                   .setMessage(user.getUserName()+"님을"+"\n"+"탈퇴시키겠습니까?")
                   .setPositiveButton("네, 탈퇴시키겠습니다.", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           FireLabelMemberRequest request = new FireLabelMemberRequest(labelId,user.getUserID());
                           NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult>() {
                               @Override
                               public void onSuccess(NetworkRequest<NetworkResult> request, NetworkResult result) {
                                   if(result.getError()==null){
                                       Toast.makeText(view.getContext(), ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                                   }else{
                                       Log.e("에러","메세지"+result.getError().getMessage());
                                       Log.e("에러","메세지");
                                   }
                               }

                               @Override
                               public void onFail(NetworkRequest<NetworkResult> request, int errorCode, String errorMessage, Throwable e) {
                                   Toast.makeText(view.getContext(), "네트워크를 확인해주세요", Toast.LENGTH_SHORT).show();
                               }
                           });
                       }
                   })
                   .setNegativeButton("아니요, 취소하겠습니다.", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                       }
                   })
                   .show();
       }else{
           new AlertDialog.Builder(view.getContext())
                   .setMessage("리더입니다.. 탈퇴하실거에요?")
                   .setPositiveButton("탈퇴안할게요..", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                       }
                   })
                   .setNegativeButton("레이블을 계속 하겠습니다.", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                       }
                   })
                   .show();
       }
    }

    @BindView(R.id.image_fireMember)
    ImageView firememberProfile;



    public interface OnFireMemberListItemClickListener{
        void onUserItemClick(View view, User user, int adapterPosition);
    }

    OnFireMemberListItemClickListener userlistener;
    public void setOnFireMemberListItemClickListener(OnFireMemberListItemClickListener listener){
        this.userlistener = userlistener;
    }
    public FireMemberViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userlistener !=null){
                    userlistener.onUserItemClick(view, user, getAdapterPosition());
                }
            }
        });

    }



    User user;
    public void setUser(User user){
            this.user = user;
            firememberView.setText(user.getUserName());
        Uri uri = Uri.parse(user.getImageUrl());
        Glide.with(firememberProfile.getContext())
                .load(uri)
                .transform(new RoundImageTransform(firememberProfile.getContext()))
                .into(firememberProfile);
    }
}
