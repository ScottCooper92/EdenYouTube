package com.eden.youtubesample.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Helper class for providing mock data to the app.
 * In a real world scenario you would either hard code the video ID's in the strings file or
 * retrieve them from a web service.
 */
public class YouTubeContent {

    /**
     * An array of YouTube videos
     */
    public static List<YouTubeVideo> ITEMS = new ArrayList<>();

    /**
     * A map of YouTube videos, by ID.
     */
    public static Map<String, YouTubeVideo> ITEM_MAP = new HashMap<>();

    static {
        addItem(new YouTubeVideo("tttG6SdnCd4", "Open in the YouTube App"));
        addItem(new YouTubeVideo("x-hH_Txxzls", "Open in the YouTube App in fullscreen"));
        addItem(new YouTubeVideo("TTh_qYMzSZk", "Open in the Standalone player in fullscreen"));
        addItem(new YouTubeVideo("tttG6SdnCd4", "Open in the Standalone player in \"Light Box\" mode"));
        addItem(new YouTubeVideo("x-hH_Txxzls", "Open in the YouTubeFragment"));
        addItem(new YouTubeVideo("TTh_qYMzSZk", "Hosting the YouTubeFragment in an Activity"));
        addItem(new YouTubeVideo("tttG6SdnCd4", "Open in the YouTubePlayerView"));
        addItem(new YouTubeVideo("x-hH_Txxzls", "Custom \"Light Box\" player with fullscreen handling"));
        addItem(new YouTubeVideo("TTh_qYMzSZk", "Custom player controls"));
    }

    private static void addItem(final YouTubeVideo item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A POJO representing a YouTube video
     */
    public static class YouTubeVideo {
        public String id;
        public String title;

        public YouTubeVideo(String id, String content) {
            this.id = id;
            this.title = content;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}