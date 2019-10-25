package com.unimelb.lecmore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OtherAnswersFragment extends Fragment {

    private DatabaseReference mRef;
    private AnswerAdapter mAdapter;
    private TextView mQuestion;
    private TextView mQuestionVote;
    private int mFollowerNum;


    @Nullable
    @Override
    /**
     * Methods for allowing users to view the answers of certain questionnaire from other users by
     * monitoring the data change of questionnaires stored int he database
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_answers, container, false);

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

                String question = dataSnapshot.child("question").getValue().toString();
                mQuestion.setText(question);
                mFollowerNum = Integer.parseInt(dataSnapshot.child("followers").getValue().toString());
                mQuestionVote.setText(String.valueOf(mFollowerNum));

                Button upvoteBtn = (Button) view.findViewById(R.id.vote_up_questionBtn);
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

        DatabaseReference ref2 = DatabaseManager.getReference("subjects/" + subjectId + "/lectures/" + lectureSession + "/questionnaire/"+questionId+"/answers/");
        // ref.
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    System.out.println("Error in finding answers in database.");
                    return;
                }

                RecyclerView answersRecyclerView = (RecyclerView) view.findViewById(R.id.answers_recycler_view);
                answersRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                List<Answer> answers = new ArrayList<>();
                for (DataSnapshot record : dataSnapshot.getChildren()) {
                    String answerId = record.getKey();
                    Answer answer = record.getValue(Answer.class);
                    answer.setId(answerId);
                    answers.add(answer);


                    if(mAdapter == null){
                        mAdapter = new AnswerAdapter(answers);
                        answersRecyclerView.setAdapter(mAdapter);
                    }else{
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        return view;
    }

   /* class MyAdapter extends ArrayAdapter<Answer> {

        public MyAdapter(@NonNull Context context, List<Answer> answers) {
            super(context, 0, answers);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Answer answer = getItem(position);

            //check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_answer, parent, false);
            }

            ((TextView) convertView.findViewById(R.id.upvotes_num)).setText(String.valueOf(answer.getUpvotes()));
            ((TextView) convertView.findViewById(R.id.downvotes_num)).setText(String.valueOf(answer.getDownvotes()));
            ((TextView) convertView.findViewById(R.id.answer)).setText(answer.getAnswer());
            ((TextView) convertView.findViewById(R.id.answerer)).setText(answer.getAnswerer());

            return convertView.findViewById(R.id.answer_item_layout);
        }
    }*/

    private class AnswerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mUpvotesTextView;
        private TextView mDownvotesTextView;
        private TextView mAnswerTextView;
        private TextView mAnswererTextView;
        private Button mSupportBtn;
        private Button mOpposeBtn;
        private Answer mAnswer;
        private DatabaseReference mLittleRef;
        public AnswerHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mUpvotesTextView = (TextView) itemView.findViewById(R.id.upvotes_num);
            mDownvotesTextView = (TextView) itemView.findViewById(R.id.downvotes_num);
            mAnswerTextView = (TextView) itemView.findViewById(R.id.answer);
            mAnswererTextView = (TextView) itemView.findViewById(R.id.answerer);
            mSupportBtn = (Button) itemView.findViewById(R.id.vote_up_button);
            mOpposeBtn = (Button) itemView.findViewById(R.id.vote_down_button);
            mLittleRef = DatabaseManager.getReference("subjects/" + LectureView.lecId + "/lectures/" + LectureView.lectureSession + "/questionnaire/"+getArguments().getString("QUESTIONID")+"/answers/");


        }

        public void bindAnswer(Answer answer) {
            mAnswer = answer;
            mUpvotesTextView.setText(String.valueOf(answer.getUpvotes()));
            mDownvotesTextView.setText(String.valueOf(answer.getDownvotes()));
            mAnswerTextView.setText(answer.getAnswer());
            mAnswererTextView.setText(" by " + answer.getAnswerer());
            mAnswererTextView.setPaintFlags(mAnswererTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(getActivity(), mLecture.getName() + " clicked!", Toast.LENGTH_SHORT).show();
            // todo
        }
    }

    private class AnswerAdapter extends RecyclerView.Adapter<AnswerHolder>{

        private List<Answer> mAnswers;

        public AnswerAdapter(List<Answer> answers){
            mAnswers = answers;
        }

        @NonNull
        @Override
        public AnswerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View view = layoutInflater.inflate(R.layout.list_item_answer, parent, false);
            return new AnswerHolder(view.findViewById(R.id.answer_item_layout));
        }

        @Override
        public void onBindViewHolder(@NonNull AnswerHolder holder, int position) {
            Answer answer = mAnswers.get(position);
            holder.bindAnswer(answer);


            holder.mLittleRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final String supportNum = dataSnapshot.child(answer.getId()).child("upvotes").getValue().toString();
                    holder.mUpvotesTextView.setText(supportNum);

                    final String opposeNum = dataSnapshot.child(answer.getId()).child("downvotes").getValue().toString();
                    String text = "-"+opposeNum;
                    holder.mDownvotesTextView.setText(text);

                    holder.mSupportBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.mLittleRef.child(answer.getId()).child("upvotes").setValue(String.valueOf(Integer.parseInt(supportNum)+1));
                        }
                    });
                    holder.mOpposeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.mLittleRef.child(answer.getId()).child("downvotes").setValue(String.valueOf(Integer.parseInt(opposeNum)+1));
                        }
                    });
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

        @Override
        public int getItemCount() {
            return mAnswers.size();
        }
    }

}
