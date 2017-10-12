package com.example.wlsgns.rnd_jin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    private TextView textViewName; //Textview that shows current user's name
    private Button logout,buttonEditP, buttonPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Initializing views
        logout = (Button) findViewById(R.id.logout);
        buttonPhoto = (Button) findViewById(R.id.buttonPhoto);
        buttonEditP = (Button) findViewById(R.id.buttonEditP);
        textViewName = (TextView) findViewById(R.id.textViewName);

        //Initializing listener to buttons
        logout.setOnClickListener(this);
        buttonEditP.setOnClickListener(this);
        buttonPhoto.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String id = user.getUid();

        dbRef = database.getReferenceFromUrl("https://testing-a2981.firebaseio.com/user").child(user.getUid());

        if(firebaseAuth.getCurrentUser() ==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //Retrieving user data
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInfo = dataSnapshot.getValue(UserInformation.class);
                textViewName.setText("Welcome "+userInfo.fName+" "+userInfo.lName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //displaying current user's first name logged in
        //textViewName.setText("Welcome "+user.getEmail());
        logout = (Button) findViewById(R.id.logout);
    }

    @Override
    public void onClick(View view) {
        if(view == logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        if(view ==buttonEditP){
            startActivity(new Intent(this,ChangeProfile.class));
        }

        if(view==buttonPhoto){
            startActivity(new Intent(this,UploadPhoto.class));
        }
    }
}
