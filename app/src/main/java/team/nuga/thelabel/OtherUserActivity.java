package team.nuga.thelabel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.adapter.ContentsAdatper;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.NetworkResultOtherUser;
import team.nuga.thelabel.data.RoundImageTransform;
import team.nuga.thelabel.data.SearchUser;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.OtherUserRequest;

public class OtherUserActivity extends AppCompatActivity {
    ContentsAdatper contentsAdatper;
    RecyclerView otherUserContents;
    ImageView imageView;
    @OnClick(R.id.imageButton_back)
    public void backButton(){
        finish();
    }
    @OnClick(R.id.imageView_message)
    public void Message(){
        Intent intent = new Intent(OtherUserActivity.this, MessageActivity.class);
        intent.putExtra("user",searchUser);
        startActivity(intent);
      }
SearchUser searchUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_otheruser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        searchUser = (SearchUser) intent.getSerializableExtra("user");
        TextView text = (TextView) findViewById(R.id.text_otheruser);
        TextView userName = (TextView)findViewById(R.id.textView_profile_otherUserName);
        String i = intent.getStringExtra("name");
        text.setText(i + "");
        userName.setText(i + "");
        setResult(RESULT_OK, intent);

        TextView genreText = (TextView)findViewById(R.id.textView_otherUser_Genre);
        String genre = intent.getStringExtra("genre");
        genreText.setText(genre+"");
        setResult(RESULT_OK, intent);

        TextView positionText = (TextView)findViewById(R.id.textView_otherUser_position);
        String position = intent.getStringExtra("position");
        positionText.setText("#"+position);
        setResult(RESULT_OK, intent);

        TextView cityText = (TextView)findViewById(R.id.textView_otherUser_city);
        String city = intent.getStringExtra("city");
        cityText.setText("#"+city);
        setResult(RESULT_OK, intent);

        TextView townText = (TextView)findViewById(R.id.textView_otherUser_town);
        String town = intent.getStringExtra("town");
        townText.setText(town);
        setResult(RESULT_OK, intent);

        ImageView imageView = (ImageView)findViewById(R.id.imageView_OtherUser_profile);
        String imagePath = intent.getStringExtra("imagePath");
        Glide.with(this)
                .load(imagePath)
                .transform(new RoundImageTransform(this))
                .into(imageView);
        setResult(RESULT_OK, intent);

        ImageView labelNeed = (ImageView) findViewById(R.id.imageView_otheruser_need);
        String need = intent.getStringExtra("need");
        switch (need){
            case "0":
                labelNeed.setVisibility(View.INVISIBLE);
                break;
            case "1":
                labelNeed.setVisibility(View.VISIBLE);
                break;
        }


        contentsAdatper = new ContentsAdatper();
        otherUserContents = (RecyclerView)findViewById(R.id.recyclerView_otherUser);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        otherUserContents.setLayoutManager(manager);
        otherUserContents.setAdapter(contentsAdatper);
        OtherUserRequest otherUserRequest = new OtherUserRequest(this, 7, 1,10);
        NetworkManager.getInstance().getNetworkData(otherUserRequest, new NetworkManager.OnResultListener<NetworkResultOtherUser>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResultOtherUser> request, NetworkResultOtherUser result) {
                Contents[] contentses = result.getPost();
//                User user = result.getUser();
//                contentsAdatper.setUser(user);
                for(Contents c : contentses){
                    contentsAdatper.add(c);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResultOtherUser> request, int errorCode, String errorMessage, Throwable e) {
                Log.e("로그","컨텐츠 에러");
            }
        });

//        Button button = (Button) findViewById(R.id.button_label);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AlertDialog.Builder(OtherUserActivity.this)
//                        .setMessage("참여한 레이블이 없습니다.")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        })
//                .show();
//            }
//        });
//
        TextView label = (TextView)findViewById(R.id.textView_otheruser_label);
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence items[] = {"레이블1", "레이블2", "레이블3"};
                new AlertDialog.Builder(OtherUserActivity.this)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                switch (item){
                                    case 0:
                                        Intent intent = new Intent(OtherUserActivity.this, OtherLabelActivity.class);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        Intent intent1 = new Intent(OtherUserActivity.this, OtherLabelActivity.class);
                                        startActivity(intent1);
                                        break;
                                    case 2:
                                        Intent intent2 = new Intent(OtherUserActivity.this, OtherLabelActivity.class);
                                        startActivity(intent2);
                                        break;
                                }
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int whichButton){
                                dialog.cancel();
                            }
                        })

                        .show();
            }

        });
//

        TextView labelInvite = (TextView) findViewById(R.id.textView_otheruser_label_invite);
        labelInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(OtherUserActivity.this)
                        .setMessage("권한이 없습니다.")

                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });
//        button = (Button)findViewById(R.id.button_onelabel);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(OtherUserActivity.this, OtherLabelActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
