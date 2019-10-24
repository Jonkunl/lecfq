package com.unimelb.lecmore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Button mSignoutBtn;

    private String usertype;
    private String id;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mCrimeRecyclerView = (RecyclerView) findViewById(R.id.lectures_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWelcomeTextView = (TextView) findViewById(R.id.welcomeInfo);
        mInstructionTextView = (TextView) findViewById(R.id.instructionInfo);
        mHintTextView = (TextView) findViewById(R.id.hintInfo);
        mSignoutBtn = (Button) findViewById(R.id.signoutBtn);

        Intent intent = getIntent();
        usertype = intent.getStringExtra("usertype");
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");

        if(savedInstanceState != null){
            usertype = savedInstanceState.getString("usertype", "student");
            name = savedInstanceState.getString("name", "unknown");
            id = savedInstanceState.getString("id", "randomID");
        }



        final String welcomeText = "Welcome to the Lecmore, " + name + "!";
        final String instructionText_student = "In Lecmore, you can either submit feedbacks or view feedbacks.";
        final String instructionText_staff = "In Lecmore, you can either create feedback session or view feedback history";

        mWelcomeTextView.setText(welcomeText);
        if("student".equals(usertype)){
            mInstructionTextView.setText(instructionText_student);
        }else{
            mInstructionTextView.setText(instructionText_staff);
        }



        updateUI();

        mSignoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, UserLoginActivity.class);
                startActivity(intent);
            }
        });
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
            Intent intent = HomePageActivity.newIntent(HomePageActivity.this, mLecture.getId(), usertype, name);
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

    @Override public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i("HomePageActivity", "onSaveInstanceState");
        savedInstanceState.putString("name", name);
        savedInstanceState.putString("id", id);
        savedInstanceState.putString("usertype", usertype);
    }

    public static Intent newIntent(Context packageContext, String lectureId, String usertype, String name){
        //
        Intent intent = new Intent(packageContext, LectureView.class);
        intent.putExtra(EXTRA_Lecture_ID, lectureId);
        intent.putExtra("usertype", usertype);
        intent.putExtra("userName", name);
        return intent;
    }
}
