package com.daxia.view.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.daxia.view.demo.R;
import com.daxia.view.demo.util.ToastUtils;
import com.daxia.view.equalizerview.view.EqSquareBars;

public class EQActivity extends CommActivity implements EqSquareBars.OnRegionChangeListener, View.OnClickListener {

    private EqSquareBars eqBars;
    private Button btnEqUp;
    private Button btnEqDown;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_eq);
        eqBars = (EqSquareBars) findViewById(R.id.eq_bars);
        btnEqUp = (Button) findViewById(R.id.btn_eq_gain_up);
        btnEqDown = (Button) findViewById(R.id.btn_eq_gain_down);
        eqBars.setOnRegionChangeListener(this);
        btnEqUp.setOnClickListener(this);
        btnEqDown.setOnClickListener(this);
    }

    @Override
    protected int getTitleRes() {
        return R.string.activity_eq_title;
    }

    @Override
    public void onRegionChanged(int region) {
        ToastUtils.show(this, "onRegionChanged: " + region);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        int progress = eqBars.getProgress();
        if (id == R.id.btn_eq_gain_up) {
            eqBars.setProgress((++progress));
        } else {
            eqBars.setProgress((--progress));
        }
    }
}
