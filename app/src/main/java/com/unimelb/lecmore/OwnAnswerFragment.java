package com.unimelb.lecmore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;


public class OwnAnswerFragment extends Fragment {

    private DatabaseReference mRef;
    private Button mSubmitBtn;
    private Button mViewOthersBtn;
    private EditText answerEditText;
    private TextView mQuestion;
    private TextView mQuestionVote;
    private int mFollowerNum;
    private String path;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_own_answers, container, false);
        mSubmitBtn = (Button)view.findViewById(R.id.submitBtn);
        mViewOthersBtn = (Button)view.findViewById(R.id.viewAnswersBtn);
        answerEditText = (EditText) view.findViewById(R.id.edit_answer);
        mQuestion = (TextView) view.findViewById(R.id.question);
        mQuestionVote = (TextView) view.findViewById(R.id.vote_num);


        String questionId = getArguments().getString("QUESTIONID");

        String subjectId = LectureView.lecId;
        int lectureSession = LectureView.lectureSession;
        mRef = DatabaseManager.getReference("subjects/" + subjectId + "/lectures/" + lectureSession + "/questionnaire/"+questionId);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    System.out.println("Error in finding question in database.");
                    return;
                }
                mFollowerNum = Integer.parseInt(dataSnapshot.child("followers").getValue().toString());
                String question = dataSnapshot.child("question").getValue().toString();
                mQuestion.setText(question);
                mQuestionVote.setText(String.valueOf(mFollowerNum));

                Button upvoteBtn = (Button) view.findViewById(R.id.vote_up_btn);
                upvoteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRef.child("followers").setValue(String.valueOf(++mFollowerNum));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mViewOthersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment otherAnswerFragment = new OtherAnswersFragment();
                Bundle args = new Bundle();
                args.putString("QUESTIONID",questionId);
                otherAnswerFragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_fragment, otherAnswerFragment ).addToBackStack(null).commit();
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = answerEditText.getText().toString();
                if(answer.equals("")){
                    Toast.makeText(getContext(),"You havent written anything.", Toast.LENGTH_SHORT);
                    return;
                }

                DatabaseReference dr = DatabaseManager.getReference("subjects/" + subjectId + "/lectures/" + lectureSession + "/questionnaire/"+questionId + "/answers/");

                Answer answer1 = new Answer("0", "0", answer, LectureView.userName);
                String uuid = UUID.randomUUID().toString();
                dr.child(uuid).setValue(answer1);

                mViewOthersBtn.performClick();
            }
        });

        return view;
    }
}
