package team.nuga.thelabel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OtherUserActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_otheruser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        TextView text = (TextView) findViewById(R.id.text_otheruser);
        String i = intent.getStringExtra("name");
        text.setText(i + "");
        setResult(RESULT_OK, intent);

        imageView = (ImageView) findViewById(R.id.imageView_chatting);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherUserActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });
        Button button = (Button) findViewById(R.id.button_label);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(OtherUserActivity.this)
                        .setMessage("참여한 레이블이 없습니다.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                .show();
            }
        });

        button = (Button)findViewById(R.id.button_yeslabel);
        button.setOnClickListener(new View.OnClickListener() {
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


        button = (Button)findViewById(R.id.button_labelinvite);
        button.setOnClickListener(new View.OnClickListener() {
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
        button = (Button)findViewById(R.id.button_onelabel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherUserActivity.this, OtherLabelActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
