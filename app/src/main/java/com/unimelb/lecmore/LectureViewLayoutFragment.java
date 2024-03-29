package com.unimelb.lecmore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public class LectureViewLayoutFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "com.unimelb.lecmore.MESSAGE";
    static int FEEDBACK_STUDENT_FRAG = R.layout.fragmen_feedack_menu_student;
    static int SUBJECT_HOME = R.layout.subject_home;
    static int QUESTION = R.layout.fragment_question_menu_student;

    static int FEEDBACK_ADMIN = R.layout.fragment_feedback_menu_admin;
    static int QUESTION_ADMIN = R.layout.fragment_question_menu_admin;

    static String LAYOUT_TYPE = "type";

    static String subjectID = LectureView.lecId;

//    private int layout = R.layout.subject_home;
    private int layout = R.layout.subject_home;

    @Nullable
    @Override
    /**
     * generate different views for a certain lecture according to the type of the user and type
     * of function the user using including questionnaire and feedback.
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.getArguments() != null)
            this.layout = getArguments().getInt(LAYOUT_TYPE);

        View fragView = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, fragView);
        initializeMenu(fragView);

        return fragView;
    }

    /**
     * method for creating a new fragment with layout number
     * @param layout int for storing layout number
     * @return the new fragment created.
     */
    static Fragment newInstance(int layout) {
        Fragment fragment = new LectureViewLayoutFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_TYPE, layout);
        fragment.setArguments(bundle);

        return fragment;

    }

    /**
     * Generate the messages and buttons according to the type of layout user in, including the
     * home page, feedback menu and questionnaire menu
     * @param view View for gathering information and setting the messages to display
     */
    private void initializeMenu(View view) {

        if (this.layout == R.layout.subject_home){
            TextView subjectCode = view.findViewById(R.id.subject_home_id);
            subjectCode.setText(LectureView.lecId);

            Button backToLectureList = view.findViewById(R.id.back_to_subject_selection_button);
            backToLectureList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), HomePageActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, LectureView.lecId);
                    intent.putExtra("usertype", LectureView.userType);
                    intent.putExtra("id", LectureView.userId);
                    intent.putExtra("name", LectureView.userName);
                    startActivity(intent);
                }
            });

            DatabaseReference aRef = FirebaseDatabase.getInstance()
                    .getReference("subjects/"+LectureView.lecId+"/lectures");

            aRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int totalScore = 0;
                    int totalRatings = 0;

                    for (DataSnapshot lecInfo : dataSnapshot.getChildren()){
                        for (DataSnapshot userFeedback : lecInfo.child("feedback").getChildren()){

                            Log.w("score", userFeedback.child("rating").getValue().toString());
                            totalScore += Integer
                                    .parseInt(userFeedback.child("rating").getValue().toString());
                            totalRatings += 1;
                        }
                    }

                    TextView avgRating = view.findViewById(R.id.actual_rating);
                    double mAvgRating = (1.0*totalScore)/totalRatings;
                    avgRating.setText(String.valueOf(mAvgRating));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }

        else if (this.layout == R.layout.fragmen_feedack_menu_student){
            final ListView listView = view.findViewById(R.id.feedback_student_list);

            DatabaseReference mRef = FirebaseDatabase.getInstance()
                    .getReference("subjects/"+LectureView.lecId+"/lectures");

            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> keyMap;
                    ArrayList<String> incompleteLectureFeedback = new ArrayList<>();

                    for(DataSnapshot dataNode : dataSnapshot.getChildren()) {
                        keyMap = new ArrayList<>();
                        String lectureNumber = dataNode.getKey();
                        Boolean feedbackCreated = (Boolean) dataNode.child("created").getValue();
                        if (feedbackCreated) {
                            for (DataSnapshot userNode : dataNode.child("feedback").getChildren()){
                                String userId = userNode.getKey();
                                keyMap.add(userId);
                            }

                            if (!keyMap.contains(LectureView.userId)) {
                                incompleteLectureFeedback.add(lectureNumber);
                            }
                        }

                        MyAdapter adapter = new MyAdapter(getActivity(), incompleteLectureFeedback);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                LectureView.lectureSession =  Integer.parseInt(adapterView.getItemAtPosition(i).toString());

                                Fragment feedbackStudent = new FeedbackStudentFragment();
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.layout_fragment, feedbackStudent)
                                        .addToBackStack(null)
                                        .commit();
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("FirebaseDataset", "loadPost:onCancelled", databaseError.toException());
                }
            });
        }

        else if (this.layout == R.layout.fragment_feedback_menu_admin) {

            ArrayList<String> subjects = new ArrayList<>();
            Map<String, Boolean> checkCreation = new HashMap<>();
            Map<String, String> lecDateMap = new HashMap<>();

            TextView subjectId = view.findViewById(R.id.feedback_admin_subject_id);
            subjectId.setText(LectureView.lecId);

            TextView lecNo = view.findViewById(R.id.feedback_admin_lec_no);
            TextView lecDate = view.findViewById(R.id.feedback_admin_lec_date);
            EditText lecNote = view.findViewById(R.id.feedback_admin_lec_note);
            Button proceedButton = view.findViewById(R.id.feedback_admin_proceed_button);

            DatabaseReference mRef = FirebaseDatabase.getInstance()
                    .getReference("subjects/"+LectureView.lecId+"/lectures");
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataNode : dataSnapshot.getChildren()) {
                        String key = dataNode.getKey();
                        subjects.add(key);
                        checkCreation.put(key, (Boolean) dataNode.child("created").getValue());
                        lecDateMap.put(key, (String) dataNode.child("date").getValue());
                    }

                    final Spinner subjectSpinner = view.findViewById(R.id.lecture_spinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_dropdown_item, subjects);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subjectSpinner.setAdapter(adapter);

                    subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String selectedLecNo = subjectSpinner.getItemAtPosition(i).toString();

                            if (checkCreation.get(selectedLecNo)) {
                                lecNo.setText("Lecture: "+selectedLecNo);
                                lecDate.setText(lecDateMap.get(selectedLecNo));
                                lecNote.setVisibility(View.GONE);

                                proceedButton.setText("View Feedbacks");
                                proceedButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LectureView.lectureSession = Integer.parseInt(selectedLecNo);
                                        Fragment feedback_admin = new FeedbackAdminFragment();
                                        getActivity().getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.layout_fragment, feedback_admin)
                                                .addToBackStack(null)
                                                .commit();
                                    }
                                });
                            } else {
                                lecNo.setText("Lecture: "+selectedLecNo);
                                lecDate.setText(lecDateMap.get(selectedLecNo));

                                lecNote.setVisibility(View.VISIBLE);
                                proceedButton.setText("Create New Feedbacks");
                                proceedButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String mLectureNote = lecNote.getText().toString();
                                        mRef.child(selectedLecNo).child("created").setValue(true);
                                        if (!mLectureNote.equals(null)){
                                            mRef.child(selectedLecNo).child("lecture note")
                                                    .setValue(mLectureNote);
                                        }
                                        Toast.makeText(view.getContext(),
                                                "Successfully created feedbacks for Lecture "+selectedLecNo,
                                                Toast.LENGTH_LONG).show();
                                        LectureView.lectureSession = Integer.parseInt(selectedLecNo);
                                        Fragment feedback_admin = new FeedbackAdminFragment();
                                        getActivity().getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.layout_fragment, feedback_admin)
                                                .addToBackStack(null)
                                                .commit();
                                    }
                                });
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        else if (this.layout == R.layout.fragment_question_menu_admin) {
            //TODO
            ArrayList<String> subjects = new ArrayList<>();
            Map<String, Boolean> checkCreation = new HashMap<>();
            Map<String, String> lecDateMap = new HashMap<>();

            TextView subjectId = view.findViewById(R.id.question_admin_subject_id);
            subjectId.setText(LectureView.lecId);

            TextView lecNo = view.findViewById(R.id.question_admin_lec_no);
            TextView lecDate = view.findViewById(R.id.question_admin_lec_date);
            Button proceedButton = view.findViewById(R.id.question_admin_proceed_button);

            DatabaseReference mRef = FirebaseDatabase.getInstance()
                    .getReference("subjects/"+LectureView.lecId+"/lectures");
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataNode : dataSnapshot.getChildren()) {
                        String key = dataNode.getKey();
                        subjects.add(key);
                        checkCreation.put(key, (Boolean) dataNode.child("createdq").getValue());
                        lecDateMap.put(key, (String) dataNode.child("date").getValue());
                    }

                    final Spinner subjectSpinner = view.findViewById(R.id.question_lecture_spinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_dropdown_item, subjects);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subjectSpinner.setAdapter(adapter);

                    subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String selectedLecNo = subjectSpinner.getItemAtPosition(i).toString();
                            LectureView.lectureSession = Integer.parseInt(selectedLecNo);

                            //view questionnaires
                            if (checkCreation.get(selectedLecNo)) {
                                lecNo.setText("Lecture: "+selectedLecNo);
                                lecDate.setText(lecDateMap.get(selectedLecNo));

                                proceedButton.setText("View Questionnaires");
                                proceedButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
//                                        LectureView.lectureSession = Integer.parseInt(selectedLecNo);
//                                        Fragment feedback_admin = new FeedbackAdminFragment();
//                                        getActivity().getSupportFragmentManager()
//                                                .beginTransaction()
//                                                .replace(R.id.layout_fragment, feedback_admin)
//                                                .addToBackStack(null)
//                                                .commit();
                                    }
                                });

                                //create questions
                            } else {
                                lecNo.setText("Lecture: "+selectedLecNo);
                                lecDate.setText(lecDateMap.get(selectedLecNo));

                                proceedButton.setText("Create New Questionnaires");
                                proceedButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Fragment question_admin = new QuestionAdminFragment();
                                        getActivity().getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.layout_fragment, question_admin)
                                                .addToBackStack(null)
                                                .commit();
                                    }
                                });
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
//TODO
        }



    }

    class MyAdapter extends ArrayAdapter<String> {
        MyAdapter(Context context, ArrayList<String> numbers) {
            super(context, 0, numbers);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String number = getItem(position);

            //check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment, parent, false);
            }

            //lookup view for data population
            TextView mNumber = convertView.findViewById(R.id.comment_text);

            mNumber.setText(number);

            return convertView;
        }
    }

    class DateMyAdapter extends ArrayAdapter<Dates> {
        DateMyAdapter (Context context, ArrayList<Dates> dates) {
            super(context, 0, dates);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Dates dates = getItem(position);

            //check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.feedback_date_list, parent, false);
            }

            //lookup view for data population
            ImageView dot = (ImageView) convertView.findViewById(R.id.dot_image);
            TextView date = (TextView) convertView.findViewById(R.id.date_text);

            dot.setImageResource(dates.getDot());
            date.setText(dates.getDate());

            return convertView;
        }
    }



}
