package com.company.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetail extends AppCompatActivity {

    String title;
    String content;
    String imageLink;

    TextView mTitle, mContent;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        mTitle = findViewById(R.id.textViewDetailTitle);
        mContent = findViewById(R.id.textViewDetailNews);
        image = findViewById(R.id.imageViewDetailImage);
        Intent i = getIntent();
        title = i.getStringExtra("title");
        content = i.getStringExtra("content");
        imageLink = i.getStringExtra("link");

        mTitle.setText(title);
        mContent.setText(content);
        Picasso.get().load(imageLink).into(image);

    }
}