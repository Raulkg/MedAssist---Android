package info.theh2o.rahul.medassist;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

import info.theh2o.rahul.medassist.EmergencyContacts.MainActivity_EmergencyContacts;
import info.theh2o.rahul.medassist.GooglePlaces.GooglePlaceActivity;
import info.theh2o.rahul.medassist.ViewPager.MainActivity_ViewPager;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    TextToSpeech t1;
    EditText ed1;
    ImageButton b1;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;
    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private de.hdodenhof.circleimageview.CircleImageView IV;
    private  Firebase ref = new Firebase("https://medassist.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        final String profilename  = intent.getStringExtra("displayName");
        final String UID  = intent.getStringExtra("UID");
        if(intent.hasExtra("profileImageURL"))
        { String imageURL  = intent.getStringExtra("profileImageURL");
            IV = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.img_logo);
            Picasso.with(getApplicationContext()).load(imageURL).into(IV);}
        toolbar =(Toolbar) findViewById(R.id.toolbar);

        ImageButton button = (ImageButton) this.findViewById(R.id.button);
        button.setColorFilter(Color.argb(60, 125, 145, 0));
        button.setImageAlpha(200);
        mTitle          = (TextView) findViewById(R.id.mainname);
        mTitle.setText(profilename);
        TextView mTitle1 = (TextView) findViewById(R.id.main_textview_title);
        mTitle1.setText(profilename);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout   = (AppBarLayout) findViewById(R.id.main_appbar);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        mAppBarLayout.addOnOffsetChangedListener(this);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);



        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);


                drawerLayout.closeDrawers();


                switch (menuItem.getItemId()) {


                    case R.id.item1:

                        Intent Mapsintent = new Intent(MainActivity.this, GooglePlaceActivity.class);
                        startActivity(Mapsintent);

                        String toSpeak = "Google Places";
                        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);


                        //  getSupportFragmentManager().beginTransaction().replace(R.id.container, AboutmeFragment.newInstance(1)).addToBackStack(null).commit();
                        break;

                    case R.id.item2:
                        Intent intent1 = new Intent(MainActivity.this, SettingsActivity.class);
                        intent1.putExtra("displayName", profilename);
                        intent1.putExtra("UID", UID);
                        startActivity(intent1);

                        Toast.makeText(getApplicationContext(), "Settings!", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.item3:

                        ref.unauth();
                        Toast.makeText(getApplicationContext(), "Logging Out", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item0:

                        Intent intent2 = new Intent(MainActivity.this, MyStatusActivity.class);
                        startActivity(intent2);


                        Toast.makeText(getApplicationContext(), "Displaying Settings", Toast.LENGTH_SHORT).show();
                        break;


                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        break;

                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }


        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open, R.string.close){

            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {


                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        b1=(ImageButton)findViewById(R.id.button);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Reminder to take Crocin";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                Intent recycleIntent = new Intent(MainActivity.this, Activity_RecycleView.class);
                startActivity(recycleIntent);
            }
        });
        ImageButton status,maps,contacts;
        status=(ImageButton)findViewById(R.id.button1);
        maps=(ImageButton)findViewById(R.id.button2);
        contacts=(ImageButton)findViewById(R.id.button5);
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Statusintent = new Intent(MainActivity.this, MainActivity_ViewPager.class);
                startActivity(Statusintent);

                String toSpeak = "Status";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Mapsintent = new Intent(MainActivity.this, GooglePlaceActivity.class);
                startActivity(Mapsintent);

                String toSpeak = "Google Places";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Contactintent = new Intent(MainActivity.this, MainActivity_EmergencyContacts.class);
                startActivity(Contactintent);

                String toSpeak = "Contacts";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });



        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newmedIntent = new Intent(MainActivity.this, NewMedicationActivity.class);
                startActivity(newmedIntent);
                Toast.makeText(getApplicationContext(), "About me ", Toast.LENGTH_SHORT).show();

                Snackbar.make(view, "Adding New Assist", Snackbar.LENGTH_LONG).show();
            }
        });


    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
       //     return true;
      //  }
        return super.onOptionsItemSelected(item);




    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }
    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
