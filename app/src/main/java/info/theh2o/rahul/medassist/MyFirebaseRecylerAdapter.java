package info.theh2o.rahul.medassist;

/**
 * Created by Rahulkumat on 4/28/2016.
 */

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.List;
import java.util.Map;

import info.theh2o.rahul.medassist.network.api;
import retrofit.Call;
import retrofit.Retrofit;


public class MyFirebaseRecylerAdapter extends FirebaseRecyclerAdapter<Movie,MyFirebaseRecylerAdapter.MovieViewHolder> {

    private List<Map<String,?>> mDataset;
    private Context mContext ;
    static OnItemClickListener mItemClickListener;
    String url="https://medassist.firebaseio.com";
    public MyFirebaseRecylerAdapter(Class<Movie> modelClass, int modelLayout,
                                    Class<MovieViewHolder> holder, Query ref,Context context,MovieData movieData) {
        super(modelClass,modelLayout,holder,ref);
        this.mContext = context;
        mDataset=movieData.getMovieList();
        // mDataset=new Movie();
    }

    @Override
    protected void populateViewHolder(final MovieViewHolder movieViewHolder, final Movie movie, int i) {

        //TODO: Populate viewHolder by setting the movie attributes to cardview fields
        //movieViewHolder.nameTV.setText(movie.getName());
        movieViewHolder.vTitle.setText(movie.getName());
        movieViewHolder.vDescription.setText((String) movie.getDescription());
        Float rating=movie.getRating()/2;
        movieViewHolder.vBar.setRating(rating);
      //  Picasso.with(mContext).load(url +movie.getUrl()).into(movieViewHolder.vIcon);

        Picasso.with(mContext)
                .load(movie.getUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(movieViewHolder.vIcon, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.v("Picasso Msg", "Succesfully loaded with Cache");
                    }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        Picasso.with(mContext)
                                .load(movie.getUrl())
                                .error(R.drawable.medassist2)
                                .into(movieViewHolder.vIcon, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        Log.v("Picasso", "Could not fetch image");
                                    }
                                });
                    }
                });


    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int position) {
        //Movie cloud=vie

        Map<String,?> movie=mDataset.get(position);
        Log.i("movie",""+movie);
        Uri url= (Uri.parse((String)movie.get("url")));
        Picasso.with(mContext).load(url).into(viewHolder.vIcon);
        viewHolder.bindMovieData(movie);
    }

    //TODO: Populate ViewHolder and add listeners.
    public static class MovieViewHolder extends RecyclerView.ViewHolder{

        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        RatingBar vBar;
        // public CheckBox vCheckbox;

        public ImageView recDotButton;
        public MovieViewHolder(View v)
        {
            super(v);
            vIcon=(ImageView)v.findViewById(R.id.icon);
            vTitle=(TextView)v.findViewById(R.id.title);
            vDescription=(TextView)v.findViewById(R.id.description);
            // vCheckbox=(CheckBox)v.findViewById(R.id.checkbox);
            vBar=(RatingBar)v.findViewById(R.id.movieRatings);
          //  recDotButton = (ImageView) v.findViewById(R.id.dot);
            //Picasso.with(mContext).load(movie.getUrl()).into(vIcon);

            if (recDotButton != null) {
                recDotButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        if (mItemClickListener != null) {
                            mItemClickListener.onSideCardButtonClick(v, getAdapterPosition());
                        }
                    }
                });
            }
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }


            });

            v.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemLongClick(v, getAdapterPosition());
                    }
                    return true;
                }
            });

        }


        public void bindMovieData(Map<String,?> movie)
        {

            vTitle.setText((String) movie.get("name"));
            vDescription.setText((String) movie.get("description"));
            // Uri url= (Uri.parse((String)movie.get("url")));

            float rbarA = (Float.valueOf((String) movie.get("rating")));
            float f = (float)(rbarA/2) ;
            vBar.setRating(f);

        }
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener)
    {
        this.mItemClickListener=mItemClickListener;
    }
    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);

        void onSideCardButtonClick(View view, int position);

    }
}