package com.unimelb.lecmore;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

import butterknife.ButterKnife;

public class LectureViewLayoutFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "com.unimelb.lecmore.MESSAGE";
    static int FEEDBACK_STUDENT_FRAG = R.layout.fragmen_feedack_menu_student;
    static int SUBJECT_HOME = R.layout.subject_home;
    static int QUESTION = R.layout.fragment_question_menu_student;

    static int FEEDBACK_ADMIN = R.layout.fragment_feedback_menu_admin;
    static int QUESTION_ADMIN = R.layout.fragment_question_menu_admin;

    static String LAYOUT_TYPE = "type";

//    private int layout = R.layout.subject_home;
    private int layout = R.layout.subject_home;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.getArguments() != null)
            this.layout = getArguments().getInt(LAYOUT_TYPE);

        View fragView = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, fragView);
        initializeMenu(fragView);

        return fragView;
    }

    static Fragment newInstance(int layout) {
        Fragment fragment = new LectureViewLayoutFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_TYPE, layout);
        fragment.setArguments(bundle);

        return fragment;

    }

    private void initializeMenu(View view) {

        if (this.layout == R.layout.fragmen_feedack_menu_student){
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("feedback");

            final ListView listView = view.findViewById(R.id.feedback_date_list);

            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> keyMap = new ArrayList<>();
                    ArrayList<Dates> datelist = new ArrayList<>();

                    for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                        String key = keyNode.getKey();
                        if (!keyMap.contains(key)) {
                            keyMap.add(key);
                            datelist.add(new Dates(R.drawable.dot, key.substring(7,9)+"/"+
                                    key.substring(5,7)+"/"+key.substring(1,5)));
                        }

                        MyAdapter adapter = new MyAdapter(getActivity(), datelist);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Dates item = (Dates) adapterView.getItemAtPosition(i);
                            String date = item.getDate();

                            //TODO integrating with database
                            Fragment random_tab = new FeedbackStudentFragment();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.layout_fragment, random_tab)
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

        else if (this.layout == R.layout.fragment_question_menu_student){
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("feedback");

            final ListView listView = view.findViewById(R.id.question_date_list);

            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> keyMap = new ArrayList<>();
                    ArrayList<Dates> datelist = new ArrayList<>();

                    for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                        String key = keyNode.getKey();
                        if (!keyMap.contains(key)) {
                            keyMap.add(key);
                            datelist.add(new Dates(R.drawable.dot, key.substring(7,9)+"/"+
                                    key.substring(5,7)+"/"+key.substring(1,5)));
                        }

                        MyAdapter adapter = new MyAdapter(getActivity(), datelist);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            //TODO validating the code

                            Dates item = (Dates) adapterView.getItemAtPosition(i);
                            String date = item.getDate();

                            Fragment random_tab = new QuestionnaireStudentFragment();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.layout_fragment, random_tab)
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
            Button newFeedbackButton = view.findViewById(R.id.create_feedback_button);
            newFeedbackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment AdminNewFeedback = new FeedbackAdminFragment();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.layout_fragment, AdminNewFeedback)
                            .addToBackStack(null)
                            .commit();
                }
            });

            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("feedback");
            final ListView listView = view.findViewById(R.id.feedback_admin_date_list);

            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> keyMap = new ArrayList<>();
                    ArrayList<Dates> datelist = new ArrayList<>();

                    for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                        String key = keyNode.getKey();
                        if (!keyMap.contains(key)) {
                            keyMap.add(key);
                            datelist.add(new Dates(R.drawable.dot, key.substring(7,9)+"/"+
                                    key.substring(5,7)+"/"+key.substring(1,5)));
                        }

                        MyAdapter adapter = new MyAdapter(getActivity(), datelist);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                //TODO validating the code

                                Dates item = (Dates) adapterView.getItemAtPosition(i);
                                String date = item.getDate();

                                Fragment random_tab = new FeedbackStudentFragment();
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.layout_fragment, random_tab)
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

        else if (this.layout == R.layout.fragment_question_menu_admin) {
            Button newQuestionButton = view.findViewById(R.id.create_question_button);
            newQuestionButton.setOnClickListener(new View.OnClickListener() {
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

            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("feedback");

            final ListView listView = view.findViewById(R.id.question_admin_date_list);

            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> keyMap = new ArrayList<>();
                    ArrayList<Dates> datelist = new ArrayList<>();

                    for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                        String key = keyNode.getKey();
                        if (!keyMap.contains(key)) {
                            keyMap.add(key);
                            datelist.add(new Dates(R.drawable.dot, key.substring(7,9)+"/"+
                                    key.substring(5,7)+"/"+key.substring(1,5)));
                        }

                        MyAdapter adapter = new MyAdapter(getActivity(), datelist);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                //TODO validating the code

                                Dates item = (Dates) adapterView.getItemAtPosition(i);
                                String date = item.getDate();

//                                Fragment random_tab = new QuestionnaireStudentFragment();
//                                getActivity().getSupportFragmentManager()
//                                        .beginTransaction()
//                                        .replace(R.id.layout_fragment, random_tab)
//                                        .addToBackStack(null)
//                                        .commit();
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



    }

    class MyAdapter extends ArrayAdapter<Dates> {
        MyAdapter (Context context, ArrayList<Dates> dates) {
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

    private void createNewQuestionnaire(View view){

    }



}
