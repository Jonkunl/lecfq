package com.unimelb.lecmore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewFeedbackAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_feedback);

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("admin/kunl/subjects");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<String> subjects = new ArrayList<>();

                for (DataSnapshot dataNode : dataSnapshot.getChildren()) {
                    String key = dataNode.getKey();
                    subjects.add(key);
                }

                final Spinner subjectSpinner = findViewById(R.id.subject_spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item, subjects);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subjectSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FirebaseDataset", "loadPost:onCancelled", databaseError.toException());
            }
        });



        final Button createNewFeedback = findViewById(R.id.create_new_feedback_button);
        createNewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFeedback(view);
            }
        });
    }

    private void createFeedback(View view){

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

        Spinner subjectSpinner = findViewById(R.id.subject_spinner);
        String subject = subjectSpinner.getSelectedItem().toString();

        EditText lecNoTextBox = findViewById(R.id.lec_no_input);
        String lecNo = lecNoTextBox.getText().toString();

        EditText lecDateTextBox = findViewById(R.id.lec_date_input);
        String lecDate = lecDateTextBox.getText().toString();

        EditText lecNoteTextBox = findViewById(R.id.lec_note_input);
        String lecNote = lecNoteTextBox.getText().toString();

        Switch publicSwitch = findViewById(R.id.public_switch);
        Boolean makeItPublic = publicSwitch.isChecked();

        mRef.child(subject).child("lecNo").setValue(lecNo);
        mRef.child(subject).child("lecNote").setValue(lecNote);
        mRef.child(subject).child("Public").setValue(makeItPublic);

        Toast.makeText(getApplicationContext(), "Successfully created", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, FeedbackMenu.class);
        this.startActivity(intent);
    }
}
