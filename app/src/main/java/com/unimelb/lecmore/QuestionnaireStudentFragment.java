package com.unimelb.lecmore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionnaireStudentFragment extends Fragment {

    private Button mButton;
    private ArrayList<Question> questions = new ArrayList<>();

    private DatabaseReference mRef;

    @Nullable
    @Override
    /**
     * generate layouts for students to give answers for the questionnaires from their subjects.
     * A function for students to vote up or vote down also provided.
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questionnaire_student, container, false);

        String subjectId = LectureView.lecId;
        int lectureSession = LectureView.lectureSession;
        mRef = DatabaseManager.getReference("subjects/" + subjectId + "/lectures/" + lectureSession + "/questionnaire/");
        // ref.
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    System.out.println("Error in finding questionnaire in database.");
                    return;
                }
                LinearLayout questionsLayout = (LinearLayout) view.findViewById(R.id.questionnaire_questions_layout);

                for (DataSnapshot record : dataSnapshot.getChildren()) {

                    String questionId = record.getKey();
                    System.out.println("stf: "+questionId);
                    String question = record.child("question").getValue().toString();
                    int followerNum = Integer.parseInt(record.child("followers").getValue().toString());

                    View questionView = inflater.inflate(R.layout.list_item_question, container, false);
                    ((TextView)questionView.findViewById(R.id.upvotes_num)).setText(String.valueOf(followerNum));
                    ((TextView)questionView.findViewById(R.id.question)).setText(question);
                    Button answerItBtn = (Button) questionView.findViewById(R.id.answer_it_btn);
                    answerItBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Fragment ownAnswerFragment = new OwnAnswerFragment();
                            Bundle args = new Bundle();
                            args.putString("QUESTIONID",questionId);
                            ownAnswerFragment.setArguments(args);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_fragment, ownAnswerFragment ).addToBackStack(null).commit();
                        }
                    });

                    Button upvoteBtn = (Button) questionView.findViewById(R.id.vote_up_button);
                    upvoteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int followerNum = Integer.parseInt(record.child("followers").getValue().toString());
                            mRef.child(questionId).child("followers").setValue(followerNum + 1);
                        }
                    });

                    LinearLayout answerItemLayout = questionView.findViewById(R.id.question_item_layout);
                    if((questionsLayout).getChildCount() == 2)
                        (questionsLayout).removeAllViews();
                    questionsLayout.addView(answerItemLayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;

    }
}
