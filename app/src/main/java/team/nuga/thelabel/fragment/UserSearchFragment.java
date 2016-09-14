package team.nuga.thelabel.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.OtherUserActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.adapter.SearchUserResultListAdapter;
import team.nuga.thelabel.data.NetworkResultUserSearch;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.UserTextSearchRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserSearchFragment extends Fragment {
    RecyclerView userListView;
    SearchUserResultListAdapter useradapter;
    EditText userSearchText;
    @BindView(R.id.imageButton_user_search)
    ImageButton userSearchButton;

    public UserSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_search, container, false);
        ButterKnife.bind(this, view);
        int color = Color.parseColor("#060928");
        userListView = (RecyclerView) view.findViewById(R.id.recyclerview_user_search);
        userSearchText = (EditText) view.findViewById(R.id.editText_user_search);
        userSearchText.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        useradapter = new SearchUserResultListAdapter();
        useradapter.setOnAdapterItemClickListener(new SearchUserResultListAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, User user, int position) {
                Intent intent = new Intent(getActivity(), OtherUserActivity.class);
                intent.putExtra("name", user.getSearchUserName() + "님의 계정입니다.");
                startActivity(intent);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        userListView.setLayoutManager(manager);
        userListView.setAdapter(useradapter);
        return view;
    }

    String searchText;

    @OnClick(R.id.imageButton_user_search)
    public void userSearchButton() {
        searchText = userSearchText.getText().toString();
        UserTextSearchRequest userTextSearchRequest = new UserTextSearchRequest(getContext(), 1, 10, searchText, "");
        NetworkManager.getInstance().getNetworkData(userTextSearchRequest, new NetworkManager.OnResultListener<NetworkResultUserSearch>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResultUserSearch> request, NetworkResultUserSearch result) {
                User[] user = result.getResult();
                for (User u : user) {
                    useradapter.add(u);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResultUserSearch> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
    }

}
