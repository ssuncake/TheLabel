package team.nuga.thelabel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import team.nuga.thelabel.R;

/**
 * Created by Blissun on 2016-08-26.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private ArrayList<MyData> myDataset;

public static class ViewHolder extends RecyclerView.ViewHolder{
    public TextView textView;
    public ImageView imageView;

    public ViewHolder(View view){
        super(view);

        textView = (TextView)view.findViewById(R.id.textView_cardView_profileId);
        imageView = (ImageView)view.findViewById(R.id.cardView_profileImage);
    }
}
    public MyAdapter(ArrayList<MyData> myDataset){
        myDataset = myDataset;
    }



    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_music_content, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(R.drawable.profile_girl);
//        holder.textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(view.getContext(), OtherUserActivity.class);
//            }
//        });
        holder.textView.setText("222222222");
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

