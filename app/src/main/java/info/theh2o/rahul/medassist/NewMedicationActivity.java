package info.theh2o.rahul.medassist;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filterable;
import android.widget.Toast;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import info.theh2o.rahul.medassist.network.api;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class NewMedicationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medication);
        AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompView.setAdapter(new RetrofitAutocompleteAdapter(this, R.layout.list_item));
        autoCompView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

private static List<String> resultList;
    public static ArrayList<String> autocomplete(String input) {
        resultList = new ArrayList<String>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rxnav.nlm.nih.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api MyFirebaseapi = retrofit.create(api.class);
        if(input!="") {
            Call<Drug> call = MyFirebaseapi.getData(input.toString());
            call.enqueue(new Callback<Drug>() {


                @Override
                public void onResponse(Response<Drug> response, Retrofit retrofit) {
                    if (response != null && !response.isSuccess() && response.errorBody() != null){

                        Log.v("error", "");
                        //DO ERROR HANDLING HERE
                        return;
                    }

                    if(response.body().getSuggestionGroup().getSuggestionList() != null) {
                        List<String> result = response.body().getSuggestionGroup().getSuggestionList().getSuggestion();
                        for(String k : result)
                                Log.v("retrofit",k);

                        resultList = (result);

                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });

        }
        resultList.add("hey");
        resultList.add("how");
        for(String m: resultList)
             Log.v("before retunring",m);

        return (ArrayList<String>) resultList;
    }

    class RetrofitAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;

        public RetrofitAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index).toString();
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                          List<String> output =  autocomplete(constraint.toString());
                        for (String item: output)
                        { resultList.add(item);
                        Log.v("values",item);}
                        for(Object x : resultList)
                        Log.v("",x.toString());
                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }






}
