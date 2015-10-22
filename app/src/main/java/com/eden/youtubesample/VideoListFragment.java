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

/***********************************************************************************
 * The MIT License (MIT)

 * Copyright (c) 2015 Scott Cooper

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/

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
                if (YouTubeIntents.canResolvePlayVideoIntent(context)) {
                    //Opens the video in the YouTube app
                    startActivity(YouTubeIntents.createPlayVideoIntent(context, video.id));
                }
                break;
            case 1:
                if (YouTubeIntents.canResolvePlayVideoIntentWithOptions(context)) {
                    //Opens in the YouTube app in fullscreen and returns to this app once the video finishes
                    startActivity(YouTubeIntents.createPlayVideoIntentWithOptions(context, video.id, true, true));
                }
                break;
            case 2:
                //Issue #3 - Need to resolve StandalonePlayer as well
                if (YouTubeIntents.canResolvePlayVideoIntent(context)) {
                    //Opens in the StandAlonePlayer, defaults to fullscreen
                    startActivity(YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                            DEVELOPER_KEY, video.id));
                }
                break;
            case 3:
                //Issue #3 - Need to resolve StandalonePlayer as well
                if (YouTubeIntents.canResolvePlayVideoIntentWithOptions(context)) {
                    //Opens in the StandAlonePlayer but in "Light box" mode
                    startActivity(YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                            DEVELOPER_KEY, video.id, 0, true, true));
                }
                break;
            case 4:
                //Opens in the YouTubeSupportFragment
                final YouTubeFragment fragment = YouTubeFragment.newInstance(video.id);
                getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
                break;
            case 5:
                //Opens in Custom Activity
                final Intent fragIntent = new Intent(context, YouTubeFragmentActivity.class);
                fragIntent.putExtra(YouTubeFragmentActivity.KEY_VIDEO_ID, video.id);
                startActivity(fragIntent);
                break;
            case 6:
                //Opens in the YouTubePlayerView
                final Intent actIntent = new Intent(context, YouTubeActivity.class);
                actIntent.putExtra(YouTubeActivity.KEY_VIDEO_ID, video.id);
                startActivity(actIntent);
                break;
            case 7:
                //Opens in the the custom Lightbox activity
                final Intent lightboxIntent = new Intent(context, CustomLightboxActivity.class);
                lightboxIntent.putExtra(CustomLightboxActivity.KEY_VIDEO_ID, video.id);
                startActivity(lightboxIntent);
                break;
            case 8:
                //Custom player controls
                final Intent controlsIntent = new Intent(context, CustomYouTubeControlsActivity.class);
                controlsIntent.putExtra(CustomLightboxActivity.KEY_VIDEO_ID, video.id);
                startActivity(controlsIntent);
                break;


        }
    }

}
