package com.company.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<News> haberList;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewNews);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        haberList = new ArrayList<>();
        getDataFromNewsApi();
        recyclerView.setOnContextClickListener(new View.OnContextClickListener() {
            @Override
            public boolean onContextClick(View view) {
                Intent i = new Intent(MainActivity.this, NewsDetail.class);
                startActivity(i);
                return true;
            }
        });
    }

    public void getDataFromNewsApi(){
        String url = "http://newsapi.org/v2/everything?q=bitcoin&from=2020-10-07&sortBy=publishedAt&apiKey=b39a577672814fb8bd91921dd051edb9";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("q", "android");
        params.put("rsz", "8");
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                // Handle resulting parsed JSON response here
                Log.d("jsondata", response.toString());

                try {
                    JSONArray articles = response.getJSONArray("articles");
                    for(int i = 0; i < articles.length(); i++){
                        JSONObject article = (JSONObject) articles.get(i);
                        News news = new News(article.getString("author"),
                                article.getString("title"),
                                article.getString("description"),
                                article.getString("urlToImage"),
                                article.getString("content"));
                        haberList.add(news);
                    }

                    adapter = new Adapter(haberList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(MainActivity.this, "Failure " +
                        Integer.toString(statusCode), Toast.LENGTH_LONG).show();
            }
        });
    }
}