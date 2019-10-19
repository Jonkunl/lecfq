package com.unimelb.lecmore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackMenu extends AppCompatActivity {

    DatabaseReference feedbackRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_menu);

        feedbackRef = FirebaseDatabase.getInstance().getReference();

        Button createFeedback = findViewById(R.id.new_feedback_button);
        createFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewFeedback(view);
            }
        });

        Button viewFeedback = findViewById(R.id.view_feedback_button);
        viewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFeedbackHistory(view);
            }
        });
    }

    public void createNewFeedback(View view){
        Intent intent = new Intent(this, NewFeedbackAdmin.class);
        this.startActivity(intent);
    }

    public void viewFeedbackHistory(View view){
        Intent intent = new Intent(this, FeedbackDateList.class);
        this.startActivity(intent);
    }
}
