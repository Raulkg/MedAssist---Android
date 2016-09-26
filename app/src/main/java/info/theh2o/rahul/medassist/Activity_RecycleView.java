package info.theh2o.rahul.medassist;

/**
 * Created by Rahulkumat on 4/28/2016.
 */

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.HashMap;

import info.theh2o.rahul.medassist.network.api;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.Callback;

public class Activity_RecycleView extends AppCompatActivity implements RecycleViewFragment.RecycleViewFragmentInterface {

    Task1Fragment mcontent;
    MovieData movieData;
    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        Firebase.setAndroidContext(this);


        if(savedInstanceState==null) {
            movieData=new MovieData();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecycleViewFragment.newInstance(movieData.getMoviesList()))
                    .commit();

        }
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adding New Assist", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return true;
    }
    @Override
    public void onListItemSelected(int position, HashMap<String, ?> movie) {

        mcontent = Task1Fragment.newInstance(movie);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,Task1Fragment.newInstance(movie))
                .addToBackStack(null).commit();

    }



}
