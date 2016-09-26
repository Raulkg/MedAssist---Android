package info.theh2o.rahul.medassist;

import com.dd.morphingbutton.MorphingButton;
import com.facebook.AccessToken;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;


import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;


import java.util.Map;

public class LoginActivity extends FirebaseLoginBaseActivity{

    Firebase firebaseRef;
    EditText userNameET;
    EditText passwordET;
    String mName;
    String mId;
    /* String Constants */
    private static final String FIREBASEREF = "https://medassist.firebaseio.com/";
    private static final String FIREBASE_ERROR = "Firebase Error";
    private static final String USER_ERROR = "User Error";
    private static final String LOGIN_SUCCESS = "Login Success";
    private static final String LOGOUT_SUCCESS = "LogOut Success";
    private static final String USER_CREATION_SUCCESS =  "Successfully created user";
    private static final String USER_CREATION_ERROR =  "User creation error";
    private static final String EMAIL_INVALID =  "email is invalid :";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseRef = new Firebase(FIREBASEREF);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameET = (EditText)findViewById(R.id.edit_text_email);
        passwordET = (EditText)findViewById(R.id.edit_text_password);
        Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.showFirebaseLoginPrompt();
            }
        });

         MorphingButton createButton = (MorphingButton) findViewById(R.id.button);
        createButton.setBackgroundColor(getResources().getColor(R.color.Green));
        if (createButton != null) {
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  createUser();
                }
            });
        }
    }

    @Override
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseLoginError) {
        Snackbar snackbar = Snackbar.
                make(userNameET, FIREBASE_ERROR + firebaseLoginError.message, Snackbar.LENGTH_SHORT);
        snackbar.show();
        resetFirebaseLoginPrompt();
    }

    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseLoginError) {
        Snackbar snackbar = Snackbar
                .make(userNameET, USER_ERROR + firebaseLoginError.message, Snackbar.LENGTH_SHORT);
        snackbar.show();
        resetFirebaseLoginPrompt();
    }

    @Override
    public Firebase getFirebaseRef() {
        return firebaseRef;
    }

    @Override
    public void onFirebaseLoggedIn(AuthData authData) {
        switch (authData.getProvider()) {
            case "password":
                mName = (String) authData.getProviderData().get("email");
                break;
            default:
                mName = (String) authData.getProviderData().get("displayName");
                break;
        }

        mId = (String) authData.getUid();
        Toast.makeText(getApplicationContext(), LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        myIntent.putExtra("displayName", mName);
        myIntent.putExtra("UID", mId);
        if(authData.getProvider()!="password")
        myIntent.putExtra("profileImageURL", authData.getProviderData().get("profileImageURL").toString());

        LoginActivity.this.startActivity(myIntent);



    }

    @Override
    public void onFirebaseLoggedOut() {
        Toast.makeText(getApplicationContext(), LOGOUT_SUCCESS, Toast.LENGTH_SHORT).show();
    }


    private void onFacebookAccessTokenChange(AccessToken token) {
        if (token != null) {
            firebaseRef.authWithOAuthToken("facebook", token.getToken(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    // The Facebook user is now authenticated with your Firebase app


                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    // there was an error
                }
            });
        } else {
        /* Logged out of Facebook so do a logout from the Firebase app */
            firebaseRef.unauth();
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        // All providers are optional! Remove any you don't want.
        setEnabledAuthProvider(AuthProviderType.PASSWORD);
        setEnabledAuthProvider(AuthProviderType.GOOGLE);
         setEnabledAuthProvider(AuthProviderType.FACEBOOK);
        //  setEnabledAuthProvider(AuthProviderType.TWITTER);
    }

    // Validate email address for new accounts.
    private boolean isEmailValid(String email) {
        boolean isGoodEmail = (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            userNameET.setError(EMAIL_INVALID + email);
            return false;
        }
        return true;
    }

    // create a new user in Firebase
    public void createUser() {


        if(userNameET.getText() == null ||  !isEmailValid(userNameET.getText().toString())) {
            return;
        }
        firebaseRef.createUser(userNameET.getText().toString(), passwordET.getText().toString(),
                new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        Snackbar snackbar = Snackbar.make(userNameET, USER_CREATION_SUCCESS, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        MorphingButton.Params circle = MorphingButton.Params.create()
                                .duration(500)
                                .cornerRadius(100) // 56 dp
                                .width(200) // 56 dp
                                .height(200) // 56 dp
                                .color((R.color.Green)) // normal state color
                                .colorPressed((R.color.ThickGreen)) // pressed state color
                                .icon(R.drawable.circle); // icon
                        final MorphingButton createButton = (MorphingButton) findViewById(R.id.button);

                        createButton.morph(circle);

                        Handler mHandler = new Handler();
                        mHandler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                createButton.setBackgroundColor(0x00000000);
                            }

                        }, 1000L);


                    }
                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Snackbar snackbar = Snackbar.make(userNameET, USER_CREATION_ERROR, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                });
    }
}
