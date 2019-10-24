package com.unimelb.lecmore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackAdminFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_feedback, container, false);

        TextView subjectInfo = view.findViewById(R.id.feedback_subject_id);
        subjectInfo.setText(LectureView.lecId);

        EditText lecNoTextBox = view.findViewById(R.id.lec_no_input);
        EditText lecDateTextBox = view.findViewById(R.id.lec_date_input);
        EditText lecNoteTextBox = view.findViewById(R.id.lec_note_input);
        Switch publicSwitch = view.findViewById(R.id.public_switch);
        Button createNewFeedback = view.findViewById(R.id.create_new_feedback_button);

        createNewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

                String lecNo = lecNoTextBox.getText().toString();
                String lecDate = lecDateTextBox.getText().toString();
                String lecNote = lecNoteTextBox.getText().toString();
                Boolean makeItPublic = publicSwitch.isChecked();

                mRef.child(LectureView.lecId).child("lecNo").setValue(lecNo);
                mRef.child(LectureView.lecId).child("lecNote").setValue(lecNote);
                mRef.child(LectureView.lecId).child("Public").setValue(makeItPublic);

                Toast.makeText(getContext(), "Successfully created", Toast.LENGTH_LONG).show();

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
