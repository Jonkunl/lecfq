package com.unimelb.lecmore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    public static final String EXTRA_Lecture_ID = "com.unimelb.lecmore.lecture_id";

    private RecyclerView mCrimeRecyclerView;
    private LectureAdapter mAdapter;
    private TextView mWelcomeTextView;
    private TextView mInstructionTextView;
    private TextView mHintTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mCrimeRecyclerView = (RecyclerView) findViewById(R.id.lectures_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWelcomeTextView = (TextView) findViewById(R.id.welcomeInfo);
        mInstructionTextView = (TextView) findViewById(R.id.instructionInfo);
        mHintTextView = (TextView) findViewById(R.id.hintInfo);

        Intent intent = getIntent();
        String usertype = intent.getStringExtra("usertype");
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");

        final String welcomeText = "Welcome to the Lecmore, " + name + "!";
        final String instructionText_student = "In Lecmore, you can either submit feedbacks or view feedbacks";
        final String instructionText_staff = "In Lecmore, you can either create feedback session or view feedback history";

        mWelcomeTextView.setText(welcomeText);
        if("student".equals(usertype)){
            mInstructionTextView.setText(instructionText_student);
        }else{
            mInstructionTextView.setText(instructionText_staff);
        }



        updateUI();
    }

    private class LectureHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitleTextView;
        private TextView mLecturerTextView;
        private TextView mLectureIDTextView;
        private Lecture mLecture;

        public LectureHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.lecture_item_lectureTitle);
            mLecturerTextView = (TextView) itemView.findViewById(R.id.lecture_item_lecturer);
            mLectureIDTextView = (TextView) itemView.findViewById(R.id.lecture_item_lectureID);
        }

        public void bindLecture(Lecture lecture) {
            mLecture = lecture;
            mTitleTextView.setText(mLecture.getName());
            mLectureIDTextView.setText(mLecture.getId());
            mLecturerTextView.setText(mLecture.getLecturer());
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(getActivity(), mLecture.getName() + " clicked!", Toast.LENGTH_SHORT).show();
            Intent intent = HomePageActivity.newIntent(HomePageActivity.this, mLecture.getId());
            startActivity(intent);
        }
    }

    private class LectureAdapter extends RecyclerView.Adapter<LectureHolder>{

        private List<Lecture> mLectures;

        public LectureAdapter(List<Lecture> lectures){
            mLectures = lectures;
        }

        @NonNull
        @Override
        public LectureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(HomePageActivity.this);
            View view = layoutInflater.inflate(R.layout.list_item_lecture, parent, false);
            return new LectureHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LectureHolder holder, int position) {
            Lecture lecture = mLectures.get(position);
            holder.bindLecture(lecture);
        }

        @Override
        public int getItemCount() {
            return mLectures.size();
        }
    }

    private void updateUI(){
        final List<Lecture> lectures = new ArrayList<>();
        DatabaseReference subRef = DatabaseManager.getReference("subjects/");

        subRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot subjectNode : dataSnapshot.getChildren()) {
                        String subjectCode = subjectNode.getKey();
                        Lecture lecture = subjectNode.getValue(Lecture.class);
                        lecture.setId(subjectCode);
                        lectures.add(lecture);
                    }
                }
                for(int i=0; i < 100; i++){
                    Lecture lecture = new Lecture();
                    lecture.setName("Lecture #" + i);
                    lecture.setLecturer("lecturer #" + i);

                    lectures.add(lecture);
                }

                if(mAdapter == null){
                    mAdapter = new LectureAdapter(lectures);
                    mCrimeRecyclerView.setAdapter(mAdapter);
                }else{
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public static Intent newIntent(Context packageContext, String lectureId){
        //
        Intent intent = new Intent(packageContext, LectureView.class);
        intent.putExtra(EXTRA_Lecture_ID, lectureId);
        return intent;
    }
}
