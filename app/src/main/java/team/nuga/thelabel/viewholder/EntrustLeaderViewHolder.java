package team.nuga.thelabel.viewholder;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import team.nuga.thelabel.Debug;
import team.nuga.thelabel.EntrustLeaderActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.EntrustLeaderRequest;

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
        final int labelId= EntrustLeaderActivity.label.getLabelID();
        entrustleaderView = (TextView)itemView.findViewById(R.id.textView_MemberList_Name);
        entrustleaderbutton = (Button)itemView.findViewById(R.id.button_etrustleader) ;
        entrustleaderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(itemView.getContext())
                        .setMessage("홍길동 님에게"+"\n"+"대표권한을 위임하겠습니까?")
                        .setPositiveButton("네, 위임합니다.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (Debug.debugmode) Log.i("위임 유저 정보"," 레이블 : "+labelId+", 유저아이디 : "+user.getUserID());
                                EntrustLeaderRequest request = new EntrustLeaderRequest(labelId,user.getUserID());
                                NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult>() {
                                    @Override
                                    public void onSuccess(NetworkRequest<NetworkResult> request, NetworkResult result) {
                                        if(result.getError()==null){
                                            if (Debug.debugmode)Log.i("권한위임 성공",""+result.getMessage()+", 코드 :"+result.getResultCode());
                                            Toast.makeText(itemView.getContext(), ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                                        }else{
                                            if(Debug.debugmode)Log.e("권한위임 실패 ", result.getError().getMessage());
                                        }
                                    }

                                    @Override
                                    public void onFail(NetworkRequest<NetworkResult> request, int errorCode, String errorMessage, Throwable e) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("다시 생각해볼게요", new DialogInterface.OnClickListener() {
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
