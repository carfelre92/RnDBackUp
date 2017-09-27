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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeProfile extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    private EditText editTextFName, editTextLName, editTextGender;
    private Button buttonSave,buttonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() ==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //Initializing views
        editTextFName = (EditText) findViewById(R.id.editTextFName);
        editTextLName = (EditText) findViewById(R.id.editTextLName);
        editTextGender = (EditText) findViewById(R.id.editTextGender);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonReturn = (Button) findViewById(R.id.buttonReturn);

        //Firebase instances
        FirebaseUser user = firebaseAuth.getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://testing-a2981.firebaseio.com/user").child(user.getUid());

        //Retrieving user data
        //Initializing the default user information
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInfo = dataSnapshot.getValue(UserInformation.class);
                editTextFName.setText(userInfo.fName);
                editTextLName.setText(userInfo.lName);
                editTextGender.setText(userInfo.gender);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        //Initializing listener to buttons
        buttonReturn.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

    }

    private void saveUserInformation(){
        String fname = editTextFName.getText().toString().trim();
        String lname = editTextLName.getText().toString().trim();
        String gender = editTextGender.getText().toString().trim();
        int auth = 0;

        UserInformation userInformation = new UserInformation(fname, lname, gender, auth);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        dbRef.setValue(userInformation);

        Toast.makeText(this, "Information saved", Toast.LENGTH_LONG).show();
    }



    @Override
    public void onClick(View v) {
        if(v==buttonSave){
            saveUserInformation();
            finish();
        }
        if(v==buttonReturn){
            finish();
        }
    }
}
