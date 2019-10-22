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

        Button newStudentFeedback = findViewById(R.id.feedback_student_button);
        newStudentFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentFeedback(view);
            }
        });


        Button lecView = findViewById(R.id.lecture_view_button);
        lecView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectureView(view);
            }
        });

    }

    public void FeedbackAdminMenu(View view){
        Intent intent = new Intent(this, FeedbackMenu.class);
        this.startActivity(intent);
    }

    public void studentFeedback(View view){
        Intent intent = new Intent(this, FeedbackStudent.class);
        this.startActivity(intent);
    }

    public void lectureView(View view){
        Intent intent = new Intent(this, LectureView.class);
        this.startActivity(intent);
    }

}
