package com.unimelb.lecmore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class UserLoginActivity extends AppCompatActivity {

    private Button mLoginBtn;
    private EditText mEmailText;
    private EditText mPasswordText;
    private RadioButton mStudentRadio;

    private DatabaseReference mRef;

    enum Type {
        STUDENT,
        STAFF
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mLoginBtn = (Button) findViewById(R.id.loginBtn);
        mEmailText = (EditText) findViewById(R.id.emailValue);
        mPasswordText = (EditText) findViewById(R.id.passwordValue);
        mStudentRadio = (RadioButton) findViewById(R.id.btnStudent);




        /*mNextPageBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });*/



        mLoginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = mEmailText.getText().toString();
                final String password = mPasswordText.getText().toString();
                Enum<Type> type = mStudentRadio.isChecked() ? Type.STUDENT : Type.STAFF;
                final String usertype;
                if(type == Type.STUDENT){
                    mRef = DatabaseManager.getReference("students/");
                    usertype="student";
                }else {
                    mRef = DatabaseManager.getReference("staff/");
                    usertype = "staff";
                }

                if(email.equals("")){
                    Toast.makeText(UserLoginActivity.this, "Email cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals("")){
                    Toast.makeText(UserLoginActivity.this, R.string.password_hint_empty, Toast.LENGTH_SHORT).show();
                    return;
                }


                Log.d("UserLoginActivity", "Go to database and check login information.");




                Query query = mRef.orderByChild("email").equalTo(email);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){
                            Toast.makeText(UserLoginActivity.this, "Failed to sign in. Incorrect email.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for(DataSnapshot record: dataSnapshot.getChildren()) {
                            String id = record.getKey();
                            User user = record.getValue(User.class);
                            if(user.getPassword().equals(password)){
                                Toast.makeText(UserLoginActivity.this, "Successfully sign in.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserLoginActivity.this, HomePageActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("name", user.getName());
                                intent.putExtra("usertype", usertype);
                                startActivity(intent);
                            }else{
                                Toast.makeText(UserLoginActivity.this, "Failed to sign in. Incorrect email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
