package com.eden.youtubesample.fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.eden.youtubesample.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * A sample implementation of a fragment extending YouTubePlayerFragment.
 * This will take care of most of the work necessary to load and play a video
 */
public class YouTubeFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private static final String KEY_VIDEO_ID = "KEY_VIDEO_ID";

    private String mVideoId;

    //Empty constructor
    public YouTubeFragment() {
    }

    /**
     * Returns a new instance of this Fragment
     *
     * @param videoId The ID of the video to play
     */
    public static YouTubeFragment newInstance(final String videoId) {
        final YouTubeFragment youTubeFragment = new YouTubeFragment();
        final Bundle bundle = new Bundle();
        bundle.putString(KEY_VIDEO_ID, videoId);
        youTubeFragment.setArguments(bundle);
        return youTubeFragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        final Bundle arguments = getArguments();

        if (bundle != null && bundle.containsKey(KEY_VIDEO_ID)) {
            mVideoId = bundle.getString(KEY_VIDEO_ID);
        } else if (arguments != null && arguments.containsKey(KEY_VIDEO_ID)) {
            mVideoId = arguments.getString(KEY_VIDEO_ID);
        }

        initialize(getString(R.string.DEVELOPER_KEY), this);
    }

    /**
     * Set the video id and initialize the player
     * This can be used when including the Fragment in an XML layout
     * @param videoId The ID of the video to play
     */
    public void setVideoId(final String videoId) {
        mVideoId = videoId;
        initialize(getString(R.string.DEVELOPER_KEY), this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {

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
            youTubeInitializationResult.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST).show();
        } else {
            //Handle the failure
            Toast.makeText(getActivity(), R.string.error_init_failure, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(KEY_VIDEO_ID, mVideoId);
    }
}
