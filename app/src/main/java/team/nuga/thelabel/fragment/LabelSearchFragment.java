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
import team.nuga.thelabel.OtherLabelActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.adapter.SearchLabelResultListAdapter;
import team.nuga.thelabel.data.ClearEditText;
import team.nuga.thelabel.data.NetworkResultLabelSearch;
import team.nuga.thelabel.data.SearchLabel;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.LabelTextSelectRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelSearchFragment extends Fragment {
    RecyclerView labelListView;
    SearchLabelResultListAdapter labeladapter;
//    EditText labelSearchText;
    ClearEditText clearEditText;
    @BindView(R.id.imageButton_label_search)
    ImageButton labelSearchButton;

    public LabelSearchFragment() {
        // Required empty public constructor   }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_label_search, container, false);
        ButterKnife.bind(this,view);
        int color = Color.parseColor("#060928");
        labelListView = (RecyclerView)view.findViewById(R.id.recyclerview_label_search);
//        labelSearchText = (EditText) view.findViewById(R.id.editText_label_search);
//        labelSearchText.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        clearEditText = (ClearEditText) view.findViewById(R.id.CustomView_claerEditText_label);
        clearEditText.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        labeladapter = new SearchLabelResultListAdapter();
        labeladapter.setOnAdapterItemClickListener(new SearchLabelResultListAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, SearchLabel label, int position) {
                Intent intent = new Intent(getActivity(),OtherLabelActivity.class);
//                intent.putExtra(MainActivity.MAINUSER,);
//                intent.putExtra(MainActivity.LABELID,)
                intent.putExtra("other",label.getSearchLabelName());
                startActivity(intent);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        labelListView.setLayoutManager(manager);
        labelListView.setAdapter(labeladapter);
        return view;
    }
    String searchText;
    @OnClick(R.id.imageButton_label_search)
    public void labelSearchButton(){
        searchText = clearEditText.getText().toString();
        LabelTextSelectRequest labelTextSelectRequest = new LabelTextSelectRequest(getContext(),1,10,searchText,"");
        NetworkManager.getInstance().getNetworkData(labelTextSelectRequest, new NetworkManager.OnResultListener<NetworkResultLabelSearch>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResultLabelSearch> request, NetworkResultLabelSearch result) {
                SearchLabel[] label = result.getLabel();
                for (SearchLabel l : label) {
                    labeladapter.add(l);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResultLabelSearch> request, int errorCode, String errorMessage, Throwable e) {

            }

        });
    }
}
