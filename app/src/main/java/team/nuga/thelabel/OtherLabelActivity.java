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

public class OtherLabelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_label);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_otherlabel);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        TextView text = (TextView) findViewById(R.id.text_otherlabel);
        String s = intent.getStringExtra("other");
        text.setText(s + "");
        setResult(RESULT_OK, intent);

        final ImageView joinrequestimageView = (ImageView) findViewById(R.id.image_joinrequest);
        joinrequestimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(OtherLabelActivity.this)
                        .setMessage("가입을 요청했습니다.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                joinrequestimageView.setImageResource(R.drawable.joinstandby);

                                joinrequestimageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        new AlertDialog.Builder(OtherLabelActivity.this)
                                                .setMessage("가입 요청이 취소되었습니다.")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        joinrequestimageView.setImageResource(R.drawable.joinstandby);
                                                    }
                                                })
                                                .show();
                                    }
                                });
                            }
                        })
                        .show();
            }
        });
    Button addbutton = (Button)findViewById(R.id.button_add);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherLabelActivity.this, MemberListActivity.class);
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
