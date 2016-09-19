package team.nuga.thelabel.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.OtherUserActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.adapter.SearchUserResultListAdapter;
import team.nuga.thelabel.data.ClearEditText;
import team.nuga.thelabel.data.NetworkResultUserSearch;
import team.nuga.thelabel.data.SearchUser;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.UserTextSearchRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserSearchFragment extends Fragment {
    RecyclerView userListView;
    SearchUserResultListAdapter useradapter;
//    EditText userSearchText;
    ClearEditText clearEditText;
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
        clearEditText = (ClearEditText) view.findViewById(R.id.CustomView_claerEditText_user);
        clearEditText.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        useradapter = new SearchUserResultListAdapter();
        useradapter.setOnAdapterItemClickListener(new SearchUserResultListAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, SearchUser user, int position) {
                Intent intent = new Intent(getActivity(), OtherUserActivity.class);
                intent.putExtra("name", user.getSearchUserName());
                intent.putExtra("genre", user.getSearchUserGenre());
                intent.putExtra("imagePath",user.getSearchUserImage());
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
        searchText = clearEditText.getText().toString();
        UserTextSearchRequest userTextSearchRequest = new UserTextSearchRequest(getContext(), 1, 10, searchText, "");
        NetworkManager.getInstance().getNetworkData(userTextSearchRequest, new NetworkManager.OnResultListener<NetworkResultUserSearch>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResultUserSearch> request, NetworkResultUserSearch result) {
                SearchUser[] user = result.getUser();
                for (SearchUser u : user) {
                    useradapter.add(u);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResultUserSearch> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
    }

}
