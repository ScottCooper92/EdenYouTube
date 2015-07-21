package com.eden.youtubesample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/***********************************************************************************
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Scott Cooper
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/

public class CustomYouTubeControlsActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    public static final String KEY_VIDEO_ID = "KEY_VIDEO_ID";

    private YouTubePlayer mPlayer;
    private String mVideoId;
    private Button playButton;
    private Button pauseButton;
    private RadioGroup styleRadioGroup;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_youtube_controls);

        final Bundle arguments = getIntent().getExtras();
        if (arguments != null && arguments.containsKey(KEY_VIDEO_ID)) {
            mVideoId = arguments.getString(KEY_VIDEO_ID);
        }

        final YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.youTubePlayerView);
        playerView.initialize(getString(R.string.DEVELOPER_KEY), this);

        playButton = (Button) findViewById(R.id.play_button);
        playButton.setOnClickListener(this);
        pauseButton = (Button) findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(this);

        styleRadioGroup = (RadioGroup) findViewById(R.id.style_radio_group);
        ((RadioButton) findViewById(R.id.style_default)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.style_minimal)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.style_chromeless)).setOnCheckedChangeListener(this);

        setControlsEnabled(false);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
        mPlayer = youTubePlayer;

        if (mVideoId != null) {
            if (restored) {
                mPlayer.play();
            } else {
                mPlayer.loadVideo(mVideoId);
            }
        }
        setControlsEnabled(true);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            //Handle the failure
            Toast.makeText(this, R.string.error_init_failure, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        //Null check the player
        if (mPlayer != null) {
            if (v == playButton) {
                mPlayer.play();
            } else if (v == pauseButton) {
                mPlayer.pause();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked && mPlayer != null) {
            switch (buttonView.getId()) {
                case R.id.style_default:
                    mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    break;
                case R.id.style_minimal:
                    mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                    break;
                case R.id.style_chromeless:
                    mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                    break;
            }
        }
    }

    private void setControlsEnabled(boolean enabled) {
        playButton.setEnabled(enabled);
        pauseButton.setEnabled(enabled);
        for (int i = 0; i < styleRadioGroup.getChildCount(); i++) {
            styleRadioGroup.getChildAt(i).setEnabled(enabled);
        }
    }
}
