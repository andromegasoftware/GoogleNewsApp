package com.company.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<News> haberList;
    Context context;
    News news;

    public Adapter(ArrayList<News> haberList) {
        this.haberList = haberList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.newcustomlayout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        news = haberList.get(position);

        holder.title.setText("" + haberList.get(position).getTitle());
        holder.author.setText("" + haberList.get(position).getAuthor());
        holder.exp.setText("" + haberList.get(position).getDescription());
        String imageLink =("" + haberList.get(position).getUrlToImage());
        Picasso.get().load(imageLink).into(holder.newsImage);

    }

    @Override
    public int getItemCount() {
        return haberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView author;
        public ImageView newsImage;
        public TextView exp;
        public LinearLayout clickLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textViewTitle);
            author = itemView.findViewById(R.id.textViewAuthor);
            newsImage = itemView.findViewById(R.id.imageViewNewsImage);
            exp = itemView.findViewById(R.id.textViewExp);
            clickLayout = itemView.findViewById(R.id.clickLayout);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, NewsDetail.class);
                    i.putExtra("title", news.getTitle());
                    i.putExtra("content", news.getContent());
                    i.putExtra("link", news.getUrlToImage());
                    context.startActivity(i);
                }
            });

        }
    }

}
