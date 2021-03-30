package com.daxia.view.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvViews;
    private ViewAdapter adapter;
    private String[] data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = getResources().getStringArray(R.array.views_test);
        rvViews = (RecyclerView) findViewById(R.id.rv_views);
        adapter = new ViewAdapter(this, data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvViews.setAdapter(adapter);
        rvViews.setLayoutManager(layoutManager);
    }
}
