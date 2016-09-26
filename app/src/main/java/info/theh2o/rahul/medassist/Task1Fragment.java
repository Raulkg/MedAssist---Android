package info.theh2o.rahul.medassist;

/**
 * Created by Rahulkumat on 4/28/2016.
 */

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Task1Fragment extends Fragment {
    public static final String ARG_MOVIE="movie";
    public static final String ARG_SECTION_NUMBER="section_number";
    public MovieData data=null;
    public HashMap<String,?> movie;
    private Context mContext;
    public static Task1Fragment newInstance(HashMap<String,?> movie) {
        Task1Fragment frag = new Task1Fragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, movie);
        frag.setArguments(args);
        return frag;
    }

    public Task1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            movie=(HashMap<String,?>)getArguments().getSerializable("movie");
        }
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Task1View=null;
        Task1View= inflater.inflate(R.layout.fragment_task1, container, false);
        data=new MovieData();

       String movieId=(String)movie.get("url");
        String movieName=(String)movie.get("name");
        String description=(String)movie.get("description");
        String length=(String)movie.get("length");
        String year=(String)movie.get("year");
        float rbarA = (Float.valueOf((String) movie.get("rating")));
        float f = (float)(rbarA/2) ;

        String ratTex=String.valueOf(movie.get("rating"));
        String director=(String)movie.get("director");
        String stars=(String)movie.get("stars");
        Uri ul = (Uri.parse(movieId));
        final ImageView imgView=(ImageView) Task1View.findViewById(R.id.MovieImg);
        Picasso.with(mContext).load(ul).into(imgView);


        final TextView movieTex=(TextView) Task1View.findViewById(R.id.MovieName);
        movieTex.setText(movieName);

        final TextView desc=(TextView) Task1View.findViewById(R.id.Description);
        desc.setText(description);

        final TextView dir=(TextView) Task1View.findViewById(R.id.Director);
        dir.setText(director);

        final TextView len=(TextView) Task1View.findViewById(R.id.min);
        len.setText(length);

        final TextView yearr=(TextView) Task1View.findViewById(R.id.year);
        yearr.setText(year);
        final TextView starss=(TextView) Task1View.findViewById(R.id.CastName);
        starss.setText(stars);

        final RatingBar ratingg=(RatingBar)Task1View.findViewById(R.id.ratingBar);

        ratingg.setRating(f);

        final TextView ratingNu=(TextView) Task1View.findViewById(R.id.RatNum);
        ratingNu.setText(ratTex);

        return Task1View;

    }

}
