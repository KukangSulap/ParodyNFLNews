package com.barney.parodynflnews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barney.parodynflnews.apiRetrofit.apiRetrofit.ApiService;
import com.barney.parodynflnews.apiRetrofit.apiRetrofit.InstanceRetrofit;
import com.barney.parodynflnews.model.ArticlesItem;
import com.barney.parodynflnews.model.ResponseNFL;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvNews)
    RecyclerView rvNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadData();
    }

    private void loadData() {
        ApiService apiService = InstanceRetrofit.getInstance();
        Call<ResponseNFL> call = (Call<ResponseNFL>) apiService;
        call.enqueue(new Callback<com.barney.parodynflnews.model.ResponseNFL>() {
            @Override
            public void onResponse(Call<ResponseNFL> call, Response<com.barney.parodynflnews.model.ResponseNFL> response) {
                if (response.body().getStatus().equals("success")) {
                    List<ArticlesItem> articlesItems = response.body().getArticles();
                    Log.e("Response",response.body().getStatus());
                    rvNews.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    CustomAdapter adapter = new CustomAdapter(articlesItems, MainActivity.this);
                    rvNews.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<com.barney.parodynflnews.model.ResponseNFL> call, Throwable t) {
            }
        });
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        List<ArticlesItem> articlesItems;
        Context context;

        public CustomAdapter(List<ArticlesItem> articlesItems, MainActivity mainActivity) {
            this.articlesItems = articlesItems;
            this.context = mainActivity;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.listitem,null);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@Nullable MyViewHolder holder, final int i) {
            holder.title.setText(articlesItems.get(i).getTitle());
            holder.author.setText(articlesItems.get(i).getAuthor());
            holder.description.setText(String.valueOf(articlesItems.get(i).getDescription()));
        }

        @Override
        public int getItemCount() {
            return articlesItems.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title,author,description;

            public MyViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.nama);
                author = itemView.findViewById(R.id.phone);
                description = itemView.findViewById(R.id.lat);
            }
        }
    }
}