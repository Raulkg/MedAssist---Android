package info.theh2o.rahul.medassist.ViewPager.YoutubeVideo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;


import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.api.services.youtube.YouTube;

import java.util.List;

/**
 *
 * Activities that contain this fragment must implement the

 */
public class VideoListFragment extends ListFragment {
    /**
     * Empty constructor
     */
    private List<YouTubeContent.YouTubeVideo> searchResults;
   // private Handler handler;
    public VideoListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new VideoListAdapter(getActivity()));
       // handler=new Handler();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
            //your codes here
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        final Context context = getActivity();
        final String DEVELOPER_KEY = "AIzaSyA-3LddQB0eSUua-MQ6FAKnfk85otNCKCI";

        final YouTubeContent.YouTubeVideo video = YouTubeContent.YouTubeITEMS.get(position);

        switch (3) {
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


            case 6:
                //Opens in the YouTubePlayerView
                final Intent actIntent = new Intent(getActivity(), YouTube.class);
               // actIntent.putExtra(YouTube.KEY_VIDEO_ID, video.id);
                startActivity(actIntent);
                break;



        }
    }


}