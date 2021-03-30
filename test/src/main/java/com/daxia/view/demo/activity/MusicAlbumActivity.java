package com.daxia.view.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.daxia.view.demo.R;

/**
 * @author Dongping Wang
 * date 3/30/2021
 */
public class MusicAlbumActivity extends CommActivity {
    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_music_album);

    }

    @Override
    protected int getTitleRes() {
        return R.string.activity_music_album_title;
    }
}
