package com.unimelb.lecmore;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class QuestionAdminFragment extends Fragment {

    static int i = 1;

    private Map<String, Boolean> visibleQs;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        visibleQs = new HashMap<>();
        visibleQs.put("2", false);
        visibleQs.put("3", false);

        String q1;
        String q2;
        String q3;

        View view = inflater.inflate(R.layout.questionnaire_admin, container, false);

        TextView subjectId = view.findViewById(R.id.question_admin_subject_id);
        subjectId.setText(LectureView.lecId);

        LinearLayout q2Layout = view.findViewById(R.id.layout_q2);
        EditText q2Text = view.findViewById(R.id.text_q2);

        LinearLayout q3Layout = view.findViewById(R.id.layout_q3);
        EditText q3Text = view.findViewById(R.id.text_q3);

        ImageButton q2DeleteButton = view.findViewById(R.id.delete_button_q2);
        q2DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                q2Text.setText("");
                q2Layout.setVisibility(View.GONE);
                visibleQs.put("2", false);
                i--;
            }
        });

        ImageButton q3DeleteButton = view.findViewById(R.id.delete_button_q3);
        q3DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                q3Text.setText("");
                q3Layout.setVisibility(View.GONE);
                visibleQs.put("3", false);
                i--;
            }
        });


        ImageButton newQuestionButton = view.findViewById(R.id.new_question_button);
        newQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!visibleQs.get("2")){
                    q2Layout.setVisibility(View.VISIBLE);
                    visibleQs.put("2", true);
                    i++;
                }

                else if (!visibleQs.get("3")) {
                    q3Layout.setVisibility(View.VISIBLE);
                    visibleQs.put("3", true);
                    i++;
                }
            }
        });

        Button submitButton = view.findViewById(R.id.new_question_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("feedback");

                switch (i) {
                    case 1: {

                    }
                }
            }
        });


        return view;
    }
}
