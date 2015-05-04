package com.eden.youtubesample;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by Scott on 21/04/15.
 */
public class YouTubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    public static final String KEY_VIDEO_ID = "KEY_VIDEO_ID";

    private String mVideoId;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_youtube);

        final Bundle arguments = getIntent().getExtras();
        if (arguments != null && arguments.containsKey(KEY_VIDEO_ID)) {
            mVideoId = arguments.getString(KEY_VIDEO_ID);
        }

        final YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.youTubePlayerView);
        playerView.initialize(getString(R.string.DEVELOPER_KEY), this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {

        //Here we can set some flags on the player

        //This flag tells the player to switch to landscape when in fullscreen, it will also return to portrait
        //when leaving fullscreen
        youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);

        //This flag tells the player to automatically enter fullscreen when in landscape. Since we don't have
        //landscape layout for this activity, this is a good way to allow the user rotate the video player.
        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);

        //This flag controls the system UI such as the status and navigation bar, hiding and showing them
        //alongside the player UI
        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);

        if (mVideoId != null) {
            if (restored) {
                youTubePlayer.play();
            } else {
                youTubePlayer.loadVideo(mVideoId);
            }
        }
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
}
