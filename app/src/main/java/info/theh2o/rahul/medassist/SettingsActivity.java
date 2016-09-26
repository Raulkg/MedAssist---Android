package info.theh2o.rahul.medassist;
import android.app.AlertDialog;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.DialogInterface;
import com.firebase.client.Config;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

/**
 * Created by Rahulkumat on 4/25/2016.
 */
public class SettingsActivity extends AppCompatActivity{

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    android.support.design.widget.FloatingActionButton btnSelect;
    ImageView ivImage;
    Camera camera;
    private Context mContext ;
    private   EditText editTextAddress;
    private EditText editTextName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mysettings);
        Intent intent = getIntent();
        final String profilename  = intent.getStringExtra("displayName");
        final String UID  = intent.getStringExtra("UID");

        mContext = getApplicationContext();


        btnSelect = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        ivImage = (ImageView) findViewById(R.id.ivImage);
        camera=new Camera();


        findViewById(R.id.fab1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 editTextName = (EditText) findViewById(R.id.Settingsname);
               editTextAddress = (EditText) findViewById(R.id.Settingsex);
                Firebase ref = new Firebase("https://medassist.firebaseio.com");
                String name = editTextName.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                Person person = new Person();
                Bitmap bmp = ((BitmapDrawable)ivImage.getDrawable()).getBitmap();//your image
                ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
                bmp.recycle();
                byte[] byteArray = bYtE.toByteArray();
                String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
                //Adding values
                person.setName(name);
                person.setAddress(address);
                person.setID(UID);
                person.setProfileURL(imageFile);

                //Storing values to firebase
                Firebase ref1 = new Firebase("https://medassist.firebaseio.com/Person");
                ref.child("Person").child(UID).setValue(person);
                Snackbar.make(view, "Saved and Synced Data with Cloud!", Snackbar.LENGTH_LONG).show();


                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            //Getting the data from snapshot
                            Person person = postSnapshot.getValue(Person.class);

                            //Adding it to a string
                            String string = "Name: " + person.getName() + "\nAddress: " + person.getAddress() + "\n\n";
                            editTextName.setText(person.getName());
                            editTextAddress.setText(person.getAddress());

                            byte[] decodedString = Base64.decode(person.getProfileURL(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            ivImage.setImageBitmap(decodedByte);




                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });



            }
        });
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                ivImage.setImageBitmap(camera.onCaptureImageResult(data));

        }
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);
        camera.onImageFromGalleryResult(selectedImagePath);
        ivImage.setImageBitmap(camera.onImageFromGalleryResult(selectedImagePath));
    }



}