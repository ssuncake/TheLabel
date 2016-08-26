package team.nuga.thelabel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.viewholder.CardViewHolder;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewHolder>{
 List<Contents> items = new ArrayList<>();
 public void add(Contents contents){
 items.add(contents);

 notifyDataSetChanged();
 }

 @Override
 public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
 View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_music_content,parent,false);
 CardViewHolder cardViewHolder = new CardViewHolder(view);

 return cardViewHolder;
 }

 @Override
 public void onBindViewHolder(CardViewHolder holder, int position) {
 holder.setContent(items.get(position));
 }

 @Override
 public int getItemCount() {
 return items.size();
 }
 }