package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.RoundImageTransform;
import team.nuga.thelabel.data.SearchUser;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class SearchUserViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.image_search_userimage)
    ImageView userphotoView;
    @BindView(R.id.textView_search_userName)
    TextView usernameView;
    @BindView(R.id.textView_search_user_position)
    TextView userPosition;
    @BindView(R.id.textView_search_user_city)
    TextView userCity;
    @BindView(R.id.textView_search_user_town)
    TextView userTown;
    @BindView(R.id.imageView_user_need)
    ImageView isNeed;
    @BindView(R.id.textView_search_user_genre)
    TextView searchUserGenre;
    public interface OnSearchUserItemClickListener{
        public void onUserItemClick(View view, SearchUser user, int position);
    }
    OnSearchUserItemClickListener listener;
    public void setOnSearchItemClickListener(OnSearchUserItemClickListener listener){
        this.listener = listener;
    }
    public SearchUserViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener !=null){
                    listener.onUserItemClick(view, user, getAdapterPosition());
                }
            }
        });
    }
    SearchUser user;
    public void setUser(SearchUser user) {
        this.user = user;
        usernameView.setText(user.getSearchUserName());
        Glide.with(userphotoView.getContext())
                .load(user.getSearchUserImage())
                .transform(new RoundImageTransform(userphotoView.getContext()))
                .into(userphotoView);
        Log.i("이미지",user.getSearchUserImage());
        userPosition.setText("#" + user.getSearchUserPosition());
        userCity.setText("#" + user.getSearchUserCity());
        userTown.setText(user.getSearchUserTown());
        searchUserGenre.setText(user.getSearchUserGenre());
        String need = user.getSearchUserNeed();
        switch (need){
            case "0":
                isNeed.setVisibility(View.INVISIBLE);
                break;
            case "1":
                isNeed.setVisibility(View.VISIBLE);
                break;
        }
    }
}
