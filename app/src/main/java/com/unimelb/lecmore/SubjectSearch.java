package com.unimelb.lecmore;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class SubjectSearch extends AppCompatActivity {

    private ShakeEventListener shakeEventListener;
    private SensorManager sensorManager;
    private Random rand;

    @Override
    /**
     * activity allows users to search for their subjects by inputting the code of the desired
     * subjects and enter the feedback and questionnaire system of the subject.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_search);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        shakeEventListener = new ShakeEventListener();

        EditText subjectSearchInput = findViewById(R.id.search_subject_code);
        ImageButton searchButton = findViewById(R.id.search_subject_button);
        TextView searchResult = findViewById(R.id.search_result);
        Button resultSubjectButton = findViewById(R.id.subject_result);
        TextView feedbackText = findViewById(R.id.search_feedback_text);
        Button backHomeButton = findViewById(R.id.search_back_home);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectCode = subjectSearchInput.getText().toString();
                DatabaseReference mRef = FirebaseDatabase.getInstance()
                        .getReference("subjects/"+subjectCode);

                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild("name")) {
                            String subjectName = dataSnapshot.child("name").getValue().toString();
                            searchResult.setText("Following Result Found!");
                            resultSubjectButton.setText(subjectCode+": "+subjectName);
                        } else {
                            searchResult.setText("Sorry! No Result Found!");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        resultSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectCode = subjectSearchInput.getText().toString();
                DatabaseReference mRef = FirebaseDatabase.getInstance()
                        .getReference("subjects/"+subjectCode+"/lectures");
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<String> feedbacks = new ArrayList<>();

                        for(DataSnapshot lecInfo : dataSnapshot.getChildren()) {
                            if (lecInfo.hasChild("feedback")) {
                                for (DataSnapshot feedbackNote : lecInfo.child("feedback").getChildren()) {
                                    feedbacks.add(feedbackNote.child("comment").getValue().toString());
                                }
                            }
                        }
                        shakeEventListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
                            @Override
                            public void onShake() {
                                rand = new Random();
                                String randomFeedback = feedbacks.get(rand.nextInt(feedbacks.size()));
                                feedbackText.setText(randomFeedback);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(shakeEventListener);
        super.onPause();
    }

    /**
     * activity for using sensor to detect the shake input from user.
     */
    public static class ShakeEventListener implements SensorEventListener {
        private static final int MIN_FORCE = 10;
        private static final int MIN_DIRECTION_CHANGE = 3;
        private static final int MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE = 200;
        private static final int MAX_TOTAL_DURATION_OF_SHAKE = 400;
        private long mFirstDirectionChangeTime = 0;
        private long mLastDirectionChangeTime;

        private int mDirectionChangeCount = 0;
        private float lastX = 0;
        private float lastY = 0;
        private float lastZ = 0;
        private OnShakeListener mShakeListener;

        public interface OnShakeListener {
            void onShake();
        }

        public void setOnShakeListener(OnShakeListener listener) {
            mShakeListener = listener;
        }

        @Override
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[SensorManager.DATA_X];
            float y = se.values[SensorManager.DATA_Y];
            float z = se.values[SensorManager.DATA_Z];
            float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);
            if (totalMovement > MIN_FORCE) {
                long now = System.currentTimeMillis();
                if (mFirstDirectionChangeTime == 0) {
                    mFirstDirectionChangeTime = now;
                    mLastDirectionChangeTime = now;
                }
                long lastChangeWasAgo = now - mLastDirectionChangeTime;
                if (lastChangeWasAgo < MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE) {
                    mLastDirectionChangeTime = now;
                    mDirectionChangeCount++;
                    lastX = x;
                    lastY = y;
                    lastZ = z;
                    if (mDirectionChangeCount >= MIN_DIRECTION_CHANGE) {
                        long totalDuration = now - mFirstDirectionChangeTime;
                        if (totalDuration < MAX_TOTAL_DURATION_OF_SHAKE) {
                            mShakeListener.onShake();
                            resetShakeParameters();
                        }
                    }
                } else {
                    resetShakeParameters();
                }
            }
        }
        private void resetShakeParameters() {
            mFirstDirectionChangeTime = 0;
            mDirectionChangeCount = 0;
            mLastDirectionChangeTime = 0;
            lastX = 0;
            lastY = 0;
            lastZ = 0;
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }
}
