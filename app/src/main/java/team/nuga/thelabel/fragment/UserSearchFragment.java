package team.nuga.thelabel.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    RecyclerView listView;
    SearchUserResultListAdapter useradapter;

    public UserSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_search, container, false);

        listView = (RecyclerView) view.findViewById(R.id.recyclerview_user_search);
        useradapter = new SearchUserResultListAdapter();
        useradapter.setOnAdapterItemClickListener(new SearchUserResultListAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, User user, int position) {
                Intent intent = new Intent(getActivity(), OtherUserActivity.class);
                intent.putExtra("name", position + "님의 계정입니다.");
                startActivity(intent);
            }
        });

        String searchText = "o";
        String sort="";
        UserTextSearchRequest userTextSearchRequest = new UserTextSearchRequest(getContext(), 1, 20, searchText,sort);
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
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(manager);
        listView.setAdapter(useradapter);
        return view;
    }


}
