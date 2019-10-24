package com.unimelb.lecmore;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionnaireStudentFragment extends Fragment {

    private Button mButton;
    private ArrayList<Question> questions = new ArrayList<>();
    private String databasename = "unimelb-mobile-51";
    private String subject = "comp1";
    private String lecture = "lec1";
    private String student = "13235";
    private String question1 = "1";
    private String question2 = "ques";
    private DatabaseReference database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questionnaire_student, container, false);

        questions = new ArrayList<Question>();

        database = FirebaseDatabase.getInstance().getReference();

        database.child("questionaire").child(subject).child(lecture).child(student).addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Question q = noteDataSnapshot.getValue(Question.class);
                    questions.add(q);
                    Log.d("STATE", q.question);
                }
                TextView view1 = (TextView) view.findViewById(R.id.textView3);
                TextView view2 = (TextView) view.findViewById(R.id.textView4);
                view1.setText(questions.get(0).question);
                view2.setText(questions.get(1).question);


            }

            public void onCancelled(DatabaseError error) {

            }
        });

        mButton = view.findViewById(R.id.submitB);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitQuestions(view);
            }
        });

        return view;
    }

    private void submitQuestions(View view) {
        switch (view.getId()) {
            case R.id.submitB:
                EditText text = (EditText) view.findViewById(R.id.answer1);
                String answer1 = text.getText().toString();
                EditText text2 = (EditText) view.findViewById(R.id.answer2);
                String answer2 = text2.getText().toString();
                database.child("questionaire").child(subject).child(lecture).child(student).child("question1").child("answer").setValue(answer1);
                database.child("questionaire").child(subject).child(lecture).child(student).child("question2").child("answer").setValue(answer2);
        }
    }
}
