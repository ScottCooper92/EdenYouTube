package com.eden.youtubesample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.eden.youtubesample.content.YouTubeContent;
import com.eden.youtubesample.fragment.YouTubeFragment;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

/**
 * Created by Scott on 19/04/15.
 */
public class VideoListFragment extends ListFragment {

    /**
     * Empty constructor
     */
    public VideoListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new VideoListAdapter(getActivity()));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        final Context context = getActivity();
        final String DEVELOPER_KEY = getString(R.string.DEVELOPER_KEY);
        final YouTubeContent.YouTubeVideo video = YouTubeContent.ITEMS.get(position);

        switch (position) {
            case 0:
                //Check whether we can actually open YT
                if (YouTubeIntents.canResolvePlayVideoIntent(getActivity())) {
                    //Opens the video in the YouTube app
                    startActivity(YouTubeIntents.createPlayVideoIntent(context, video.id));
                }
                break;
            case 1:
                if (YouTubeIntents.canResolvePlayVideoIntentWithOptions(getActivity())) {
                    //Opens in the YouTube app in fullscreen and returns to this app once the video finishes
                    startActivity(YouTubeIntents.createPlayVideoIntentWithOptions(context, video.id, true, true));
                }
                break;
            case 2:
                //Opens in the StandAlonePlayer, defaults to fullscreen
                startActivity(YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                        DEVELOPER_KEY, video.id));
                break;
            case 3:
                //Opens in the StandAlonePlayer but in "Light box" mode
                startActivity(YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                        DEVELOPER_KEY, video.id, 0, true, true));
                break;
            case 4:
                //Opens in the YouTubeSupportFragment
                final YouTubeFragment fragment = YouTubeFragment.newInstance(video.id);
                getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
                break;
            case 5:
                //Opens in Custom Activity
                final Intent fragIntent = new Intent(getActivity(), YouTubeFragmentActivity.class);
                fragIntent.putExtra(YouTubeFragmentActivity.KEY_VIDEO_ID, video.id);
                startActivity(fragIntent);
                break;
            case 6:
                //Opens in the YouTubePlayerView
                final Intent actIntent = new Intent(getActivity(), YouTubeActivity.class);
                actIntent.putExtra(YouTubeActivity.KEY_VIDEO_ID, video.id);
                startActivity(actIntent);
                break;
            case 7:
                //Opens in the the custom Lightbox activity
                final Intent lightboxIntent = new Intent(getActivity(), CustomLightboxActivity.class);
                lightboxIntent.putExtra(CustomLightboxActivity.KEY_VIDEO_ID, video.id);
                startActivity(lightboxIntent);
                break;
            case 8:
                //Custom player controls
                final Intent controlsIntent = new Intent(getActivity(), CustomYouTubeControlsActivity.class);
                controlsIntent.putExtra(CustomLightboxActivity.KEY_VIDEO_ID, video.id);
                startActivity(controlsIntent);
                break;


        }
    }

}
