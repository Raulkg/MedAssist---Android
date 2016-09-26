
package info.theh2o.rahul.medassist.ViewPager.YoutubeVideo;
/**
 * Created by Kusum on 4/24/2016.
 */

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
    public static List<YouTubeVideo> YouTubeITEMS = new ArrayList<>();
   // private static YouTube youtube;
    /**
     * A map of YouTube videos, by ID.
     */
    public static Map<String, YouTubeVideo> ITEM_MAP = new HashMap<>();

    static {
        addItem(new YouTubeVideo("tttG6SdnCd4", "Open in the YouTube App","kl","th"));
        addItem(new YouTubeVideo("x-hH_Txxzls", "Open in the YouTube App in fullscreen","kl","th"));
        addItem(new YouTubeVideo("TTh_qYMzSZk", "Open in the Standalone player in fullscreen","kl","th"));
        addItem(new YouTubeVideo("tttG6SdnCd4", "Open in the Standalone player in \"Light Box\" mode","kl","th"));
        addItem(new YouTubeVideo("x-hH_Txxzls", "Open in the YouTubeFragment","kl","th"));
        addItem(new YouTubeVideo("TTh_qYMzSZk", "Hosting the YouTubeFragment in an Activity","kl","th"));
        addItem(new YouTubeVideo("tttG6SdnCd4", "Open in the YouTubePlayerView","kl","th"));
        addItem(new YouTubeVideo("x-hH_Txxzls", "Custom \"Light Box\" player with fullscreen handling","kl","th"));
        addItem(new YouTubeVideo("TTh_qYMzSZk", "Custom player controls","kl","th"));
    }

    public static void addItem(final YouTubeVideo item) {
        YouTubeITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A POJO representing a YouTube video
     */
    public static class YouTubeVideo {
        public String id;
        public String title;
        public String description;
        public String thumbnailURL;

        public YouTubeVideo(String id, String content,String description,String thumbnailURL) {
            this.id = id;
            this.title = content;
            this.description=description;
            this.thumbnailURL=thumbnailURL;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getThumbnailURL() {
            return thumbnailURL;
        }

        public void setThumbnailURL(String thumbnail) {
            this.thumbnailURL = thumbnail;
        }

        @Override
        public String toString() {
            return title;
        }
    }



}