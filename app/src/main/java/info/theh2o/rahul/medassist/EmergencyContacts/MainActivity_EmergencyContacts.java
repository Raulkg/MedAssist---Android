package info.theh2o.rahul.medassist.EmergencyContacts;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import info.theh2o.rahul.medassist.R;

public class MainActivity_EmergencyContacts extends AppCompatActivity {

    public static final int PICK_CONTACT    = 1;
    private Button btnContacts;
    private TextView txtContacts1;
    private TextView txtContacts2;
    private ListView videosFound;
    List<ContactBean> items = new ArrayList<ContactBean>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_emercontacts);
        btnContacts = (Button) findViewById(R.id.btn_contacts);
        txtContacts1 = (TextView) findViewById(R.id.txt_contacts_name);
        txtContacts2 = (TextView) findViewById(R.id.txt_contacts_number);
        videosFound = (ListView)findViewById(R.id.videos_found);



        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
               // updateVideosFound();
            }
        });

    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK && data!=null) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String name= c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String number= c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        ContactBean cb=new ContactBean();
                        cb.setTitle(name);
                        cb.setNumber(number);
                        items.add(cb);//this is where the items to the Contact List is added

                        updateContactsFound();
                    }
                }
                break;
        }
    }

    //sets in the adapter and view holder
    private void updateContactsFound(){
        ArrayAdapter<ContactBean> adapter = new ArrayAdapter<ContactBean>(getApplicationContext(), R.layout.rowlayout_emercontacts, items){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.rowlayout_emercontacts, parent, false);
                }

                txtContacts1 = (TextView)convertView.findViewById(R.id.txt_contacts_name);
                txtContacts2 = (TextView)convertView.findViewById(R.id.txt_contacts_number);

                ContactBean contactResult = items.get(position);

                //Picasso.with(getApplicationContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                txtContacts1.setText(contactResult.getTitle());
                txtContacts2.setText(contactResult.getNumber());
                return convertView;
            }
        };

        videosFound.setAdapter(adapter);
    }
}

