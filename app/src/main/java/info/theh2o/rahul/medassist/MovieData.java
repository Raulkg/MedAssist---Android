package info.theh2o.rahul.medassist;

/**
 * Created by Rahulkumat on 4/28/2016.
 */

import java.util.Map;


        import android.content.Context;
        import android.util.Log;

        import com.firebase.client.ChildEventListener;
        import com.firebase.client.DataSnapshot;
        import com.firebase.client.Firebase;
        import com.firebase.client.FirebaseError;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class MovieData {

    List<Map<String,?>> moviesList;
    public static String FIREBASE="https://medassist.firebaseio.com/moviedata";
    MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;
    Context mContext;
    Firebase mref;

    public List<Map<String, ?>> getMoviesList() {
        return moviesList;
    }

    public int getSize(){
        return moviesList.size();
    }

    public int findfirst(String str){
        int i,pos=0;
        for(i=0;i<moviesList.size();i++){
            HashMap mov = getItem(i);
            String title = (String)mov.get("name");
            if(title.contains(str)){
                pos = i;
                break;
            }
            else
                continue;
        }
        return pos;
    }

    public void removeItem(int i)
    {
        //HashMap<String,?> movie=(HashMap)moviesList.get(i);
        //if((Boolean)(movie.get("selection"))==true)
        moviesList.remove(i);
    }
    public void addItem(int position,HashMap map)
    {
        moviesList.add(position, map);
    }
    public HashMap getItem(int i){
        if (i >=0 && i < moviesList.size()){
            return (HashMap) moviesList.get(i);
        } else return null;
    }

    public MovieData(){

        moviesList=new ArrayList<Map<String,?>>();
        mContext=null;
        myFirebaseRecylerAdapter=null;
        mref=new Firebase(FIREBASE);
    }

    public List<Map<String,?>> getMovieList()
    {
        return moviesList;
    }
    public void setAdapter(MyFirebaseRecylerAdapter mAdapter)
    {
        myFirebaseRecylerAdapter=mAdapter;
    }
    public void setContext(Context context)
    {
        mContext=context;
    }
    public Firebase getFirebaseRef()
    {
        return mref;
    }
    public MovieData(List<Map<String, ?>> movieList)
    {
        this.moviesList=movieList;
    }
    private HashMap createMovie(String name, int image, String description, String year,
                                String length, double rating, String director, String stars, String url) {
        HashMap movie = new HashMap();
        movie.put("image",image);
        movie.put("name", name);
        movie.put("description", description);
        movie.put("year", year);
        movie.put("length",length);
        movie.put("rating",rating);
        movie.put("director",director);
        movie.put("stars",stars);
        movie.put("url",url);
        movie.put("selection",false);
        return movie;
    }

    public void initializeDataFromCloud()
    {
        moviesList.clear();
        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap<String, String> movie = (HashMap<String, String>) dataSnapshot.getValue();
                onItemAddedToCloud(movie);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                HashMap<String, String> movie = (HashMap<String, String>) dataSnapshot.getValue();
                onItemUpdatedToCloud(movie);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                HashMap<String, String> movie = (HashMap<String, String>) dataSnapshot.getValue();
                onItemRemovedFromCloud(movie);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void onItemRemovedFromCloud(HashMap item)
    {
        int position=-1;
        String id=(String)item.get("id");
        for(int i=0;i<moviesList.size();i++)
        {
            HashMap movie=(HashMap)moviesList.get(i);
            String mid=(String)movie.get("id");
            if(mid.equals(id))
            {
                position=i;
                break;
            }
        }
        if(position!=-1)
        {
            moviesList.remove(position);
            Log.d("notifyremoved", id);
            if(myFirebaseRecylerAdapter!=null)
            {
                myFirebaseRecylerAdapter.notifyItemRemoved(position);
            }

        }
    }

    public void onItemAddedToCloud(HashMap item)
    {
        int position=0;
        String id=(String)item.get("id");
        for(int i=0;i<moviesList.size();i++)
        {
            HashMap movie=(HashMap)moviesList.get(i);
            String mid=(String)movie.get("id");
            if(mid.equals(id))
            {
                return;
            }
            if(mid.compareTo(id)<0)
            {
                position=i+1;
            }
            else
                break;
        }

        moviesList.add(position,item);
        Log.d("notify insert", id);
        if(myFirebaseRecylerAdapter!=null)
        {
            myFirebaseRecylerAdapter.notifyItemInserted(position);
        }


    }

    public void onItemUpdatedToCloud(HashMap item)
    {
        String id=(String)item.get("id");
        for(int i=0;i<moviesList.size();i++)
        {
            HashMap movie=(HashMap)moviesList.get(i);
            String mid=(String)movie.get("id");
            if(mid.equals(id))
            {
                moviesList.remove(i);
                moviesList.add(i, item);
                Log.d("notify update", id);
                if(myFirebaseRecylerAdapter!=null)
                {
                    myFirebaseRecylerAdapter.notifyItemInserted(i);
                }
                break;
            }

        }




    }

    public void removeItemFromServer(Map<String,?> movie)
    {
        if(movie!=null)
        {
            String id=(String)movie.get("id");
            mref.child(id).removeValue();
        }
    }
    public void addItemToServer(Map<String,?> movie)
    {
        if(movie!=null)
        {
            String id=(String)movie.get("id");
            mref.child(id).setValue(movie);
        }
    }
}
