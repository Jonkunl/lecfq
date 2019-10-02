package com.unimelb.lecmore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener{
        private Button mButton;
        private ArrayList<Question> questions;
        private String quest1;
        private String quest2;
    private DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        database = FirebaseDatabase.getInstance().getReference();
        database.child("unimelb-mobile-51").child("comp1").child("lec1").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                questions = new ArrayList<Question>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Question question = noteDataSnapshot.getValue(Question.class);
                    questions.add(question);

                }

            }
            public void onCancelled(DatabaseError error){

            }
        });
        if(questions.size()>0) {
            String question1 = questions.get(0).getQuestion();
            String question2 = questions.get(1).getQuestion();
        }
        /*
        TextView view1 = (TextView)findViewById(R.id.textView3);
        TextView view2 = (TextView)findViewById(R.id.textView4);
        view1.setText(question1);
        view2.setText(question2);

         */
        mButton = findViewById(R.id.submitB);
        mButton.setOnClickListener(this);

    }
    public void submit(View view){

    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.submitB:
                EditText text = (EditText)findViewById(R.id.answer1);
                String answer1 = text.getText().toString();
                EditText text2 = (EditText)findViewById(R.id.answer2);
                String answer2 = text2.getText().toString();
                database.child("comp1").child("lec1").child("question1").child("answer").setValue(answer1);
                database.child("comp1").child("lec1").child("question2").child("answer").setValue(answer2);

        }
    }
}
