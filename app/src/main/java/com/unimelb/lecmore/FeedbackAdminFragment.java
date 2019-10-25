package com.unimelb.lecmore;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
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

import java.util.ArrayList;

public class FeedbackAdminFragment extends Fragment {

    private ArrayList<Feedback> feedback = new ArrayList<>();
    private ArrayList<String> comments = new ArrayList<>();

    @Nullable
    @Override
    /**
     * Method for retrieving the feedbacks from cloud database for the administrator side when the
     * activity is created
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedback_history, container, false);

        ListView listView = view.findViewById(R.id.feedback_date_list);
        DatabaseReference mRef = FirebaseDatabase.getInstance().
                getReference("subjects/"+LectureView.lecId+"/lectures/"+LectureView.lectureSession+"/feedback");

        TextView textView = view.findViewById(R.id.feedback_subject_id);
        textView.setText(LectureView.lecId+ ": Lecture "+LectureView.lectureSession);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataNode : dataSnapshot.getChildren()) {
                    comments.add(dataNode.child("comment").getValue().toString());
                }

                MyAdapter adapter = new MyAdapter(view.getContext(), comments);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return view;
    }

    /**
     * Adapter for display the comments of the feedback on the text views.
     */
    class MyAdapter extends ArrayAdapter<String> {
        MyAdapter(Context context, ArrayList<String> comments) {
            super(context, 0, comments);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String comment = getItem(position);

            //check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment, parent, false);
            }

            //lookup view for data population
            TextView mComment = convertView.findViewById(R.id.comment_text);

            mComment.setText(comment);

            return convertView;
        }
    }
}
