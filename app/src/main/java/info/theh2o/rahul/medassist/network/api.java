package info.theh2o.rahul.medassist.network;

import java.util.List;

import info.theh2o.rahul.medassist.Drug;
import info.theh2o.rahul.medassist.Movie;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Rahulkumat on 4/28/2016.
 */
public interface api {




    @GET("REST/spellingsuggestions.json")
    Call<Drug> getData(@Query("name") String drug);
}
