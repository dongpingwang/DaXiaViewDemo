package com.daxia.view.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Dongping Wang
 * date 3/30/2021
 */
public abstract class CommActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getTitleRes());
        }
        init(savedInstanceState);
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract @StringRes int getTitleRes();
}
