package info.theh2o.rahul.medassist;

/**
 * Created by Rahulkumat on 4/28/2016.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.PopupMenu;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.theh2o.rahul.medassist.network.api;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecycleViewFragment extends Fragment  {
    private String url="https://medassist.firebaseio.com";
    MovieData movieData;
    List<Map<String,?>> movieList;
    // RecycleViewAdapter mRecycleViewAdapter;
    MyFirebaseRecylerAdapter mMyFirebaseRecylerAdapter;
    RecyclerView mRecyclerView;
    RecycleViewFragmentInterface mListener;
    public static final String ARG_SECTION_NUMBER="movie";
    final Firebase ref=new Firebase("https://medassist.firebaseio.com/moviedata");
    public RecycleViewFragment() {
        // Required empty public constructor
    }

    public static RecycleViewFragment newInstance(List<Map<String,?>> movieList)
    {
        RecycleViewFragment frag = new RecycleViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTION_NUMBER, (Serializable) movieList);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieData=new MovieData();
        if(getArguments()!=null)
        {
            movieList=(List<Map<String,?>>) getArguments().getSerializable("movie");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (RecycleViewFragmentInterface) getContext();
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException("listner not initialized");
        }
    }



    public interface RecycleViewFragmentInterface
    {
        public void onListItemSelected(int position, HashMap<String, ?> movie);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if(menu.findItem(R.id.search)==null)
            inflater.inflate(R.menu.recmenu,menu);



        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
        if(search!=null){
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
                @Override
                public boolean onQueryTextSubmit(String query){

                    StringBuffer str = new StringBuffer(query);
                    str.replace(0, 1, String.valueOf(Character.toUpperCase(str.charAt(0))));

                    for(int i = 0; i < str.length(); i++){
                        if(Character.isWhitespace(str.charAt(i))){
                            str.replace(i+1, i+2, String.valueOf(Character.toUpperCase(str.charAt(i+1))));
                            break;
                        }
                    }

                    int position = movieData.findfirst(str.toString());
                    if(position>=0)
                        mRecyclerView.scrollToPosition(position);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String query){
                    return true;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView= inflater.inflate(R.layout.fragment_recycle_view, container, false);
        mRecyclerView=(RecyclerView) rootView.findViewById(R.id.cardList);
        // mRecyclerView.setHasFixedSize(true);

        //movieList = (List<Map<String,?>>) getArguments().getSerializable("movie");
        //movieData = new MovieData(movieList);
        if(movieData.getSize()==0)
        {
            movieData.setAdapter(mMyFirebaseRecylerAdapter);
            movieData.setContext(getActivity());
            movieData.initializeDataFromCloud();

        }
        LinearLayoutManager mLayoutManager =new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        Log.i("frag","========"+movieData.getMovieList());
        // mRecycleViewAdapter=new RecycleViewAdapter(getActivity(),movieData.getMoviesList());
        mMyFirebaseRecylerAdapter=new MyFirebaseRecylerAdapter(Movie.class,R.layout.cardview,
                MyFirebaseRecylerAdapter.MovieViewHolder.class,ref,getActivity(),movieData);
        mRecyclerView.setAdapter(mMyFirebaseRecylerAdapter);


        mMyFirebaseRecylerAdapter.setOnItemClickListener(new MyFirebaseRecylerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                HashMap<String, ?> movie = (HashMap<String, ?>) movieData.getItem(position);
                String id =(String)movie.get("id");
                ref.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, String> movie = (HashMap<String, String>) dataSnapshot.getValue();
                        mListener.onListItemSelected(position, movie);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Log.d("my test", "reading failed");
                    }
                });





            }

            @Override
            public void onItemLongClick(View view, int position) {
                getActivity().startActionMode(new ActionBarCallBack(position));
            }

            @Override
            public void onSideCardButtonClick(View v, final int position) {
                PopupMenu popup = new PopupMenu(getActivity(), v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        HashMap movie;
                        switch (item.getItemId()) {
                            case R.id.deleteitem:

                                //Movie del=mMyFirebaseRecylerAdapter.getItem(position);
                                //ref.child(del.getId()).removeValue();
                                movie= (HashMap) ((HashMap) movieData.getItem(position));
                                movieData.removeItemFromServer(movie);



                                break;
                            case R.id.duplicateitem:
                                movie= (HashMap) ((HashMap) movieData.getItem(position)).clone();
//                                mRecycleViewAdapter.notifyItemInserted(position + 1);
                                //Movie cloud=mMyFirebaseRecylerAdapter.getItem(position);
                                movie.put("id", (String) movie.get("id") + "-new");
                                //cloud.setName(cloud.getName()+"-New");
                                // cloud.setId(cloud.getId()+"-new");
                                //ref.child(cloud.getId()).setValue(cloud);
                                movieData.addItemToServer(movie);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.popmenu, popup.getMenu());
                popup.show();

            }
        });





        adapterAnimation();
        itemAnimation();

        return rootView;

    }

    private void adapterAnimation(){
        AlphaInAnimationAdapter alphaadapter = new AlphaInAnimationAdapter(mMyFirebaseRecylerAdapter);
        ScaleInAnimationAdapter scaleadapter = new ScaleInAnimationAdapter(alphaadapter);
        mRecyclerView.setAdapter(scaleadapter);
    }
    private void itemAnimation(){
        SlideInLeftAnimator animator = new SlideInLeftAnimator();
        animator.setInterpolator(new OvershootInterpolator());
        animator.setAddDuration(300);
        animator.setRemoveDuration(300);
        mRecyclerView.setItemAnimator(animator);
    }

    class ActionBarCallBack implements ActionMode.Callback{

        int position;

        public ActionBarCallBack(int position){
            this.position = position;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode,MenuItem item){
            int id = item.getItemId();
            switch (id){
                case R.id.deleteitem:

                    HashMap<String, Boolean> item1 =
                            (HashMap<String, Boolean>) movieData.getItem(position);

                    movieData.removeItem(position);
                    mMyFirebaseRecylerAdapter.notifyItemRemoved(position);
                    if(movieData.getSize()==0)
                    {
                        mMyFirebaseRecylerAdapter.notifyDataSetChanged();
                    }

                    break;
                case R.id.duplicateitem:
                    movieData.addItem(position + 1, (HashMap) ((HashMap) movieList.get(position)).clone());
                    mMyFirebaseRecylerAdapter.notifyItemInserted(position + 1);
                    mode.finish();
                    break;
                default:
                    break;
            }
            return false;
        }
        @Override
        public boolean onCreateActionMode(ActionMode mode,Menu menu){
            mode.getMenuInflater().inflate(R.menu.popmenu,menu);
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode){
            //mode.getMenuInflater().inflate(R.menu.popupmenu,menu);
            //return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode,Menu menu){

            HashMap hm = (HashMap) movieData.getItem(position);
            mode.setTitle((String)hm.get("name"));
            return false;
        }


    }



}

