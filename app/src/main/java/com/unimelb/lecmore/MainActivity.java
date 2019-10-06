package com.unimelb.lecmore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createFeedback = findViewById(R.id.feedback_admin_button);
        createFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedbackAdminMenu(view);
            }
        });
    }

    public void FeedbackAdminMenu(View view){
        Intent intent = new Intent(this, FeedbackMenu.class);
        this.startActivity(intent);
    }

}
