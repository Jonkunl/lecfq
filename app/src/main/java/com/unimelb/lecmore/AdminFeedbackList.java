package com.unimelb.lecmore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminFeedbackList extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private ArrayList<Feedback> feedback = new ArrayList<>();
    private ArrayList<String> keyMap = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_feedback_list);

        Intent intent = getIntent();
        String message = intent.getStringExtra(FeedbackDateList.EXTRA_MESSAGE);
        String queryDate = "L" + message.substring(6, 10) + message.substring(3, 5) + message.substring(0, 2);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("feedback/" + queryDate);
        System.out.println("feedback/" + queryDate);

        final ListView listView = (ListView) findViewById(R.id.feedback_comment_list);

        TextView textView = findViewById(R.id.random_text);
        textView.setText(message);


        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot feedbackSnapshot : dataSnapshot.getChildren()) {
                    String key = feedbackSnapshot.getKey();
                    if (!keyMap.contains(key)) {
                        keyMap.add(key);
                        feedback.add(feedbackSnapshot.getValue(Feedback.class));
                    }

                    MyAdapter adapter = new MyAdapter(AdminFeedbackList.this, feedback);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FirebaseDatasetHelper", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }

    class MyAdapter extends ArrayAdapter<Feedback> {
        MyAdapter(Context context, ArrayList<Feedback> feedbacks) {
            super(context, 0, feedbacks);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Feedback feedback = getItem(position);

            //check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment, parent, false);
            }

            //lookup view for data population
            TextView date = (TextView) convertView.findViewById(R.id.comment_text);

            date.setText(feedback.getComment());

            return convertView;
        }
    }
}
