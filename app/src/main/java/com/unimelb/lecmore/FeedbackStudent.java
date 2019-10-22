package com.unimelb.lecmore;

<<<<<<< HEAD
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
=======
<<<<<<< HEAD
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
>>>>>>> aaeef8dce45f1d5bc62e7502561cb5da19d35e2d
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
<<<<<<< HEAD
=======
=======
import androidx.appcompat.app.AppCompatActivity;
>>>>>>> aaeef8dce45f1d5bc62e7502561cb5da19d35e2d

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD

public class FeedbackStudent extends AppCompatActivity {
=======
>>>>>>> 222760d12a5f531b96f5762bbf2d720d05415a93

public class MainActivity extends AppCompatActivity {
>>>>>>> aaeef8dce45f1d5bc62e7502561cb5da19d35e2d

    RatingBar mRatingBar;
    TextView mRatingScale;
    EditText mFeedback;
    Button mSendFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.feedback_student);

        mRatingBar = findViewById(R.id.ratingBar);
        mRatingScale = findViewById(R.id.tvRatingScale);
        mFeedback = findViewById(R.id.etFeedback);
        mSendFeedback = findViewById(R.id.btnSubmit);
=======
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        mRatingScale = (TextView) findViewById(R.id.tvRatingScale);
        mFeedback = (EditText) findViewById(R.id.etFeedback);
        mSendFeedback = (Button) findViewById(R.id.btnSubmit);
>>>>>>> aaeef8dce45f1d5bc62e7502561cb5da19d35e2d

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mRatingScale.setText("Very bad");
                        break;
                    case 2:
                        mRatingScale.setText("Need some improvement");
                        break;
                    case 3:
                        mRatingScale.setText("Good");
                        break;
                    case 4:
                        mRatingScale.setText("Great");
                        break;
                    case 5:
                        mRatingScale.setText("Awesome. I love it");
                        break;
                    default:
                        mRatingScale.setText("");
                }
            }
        });

<<<<<<< HEAD
        mFeedback.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });

=======
>>>>>>> aaeef8dce45f1d5bc62e7502561cb5da19d35e2d
        mSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFeedback.getText().toString().isEmpty()) {
<<<<<<< HEAD
                    Toast.makeText(FeedbackStudent.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
                } else {
                    mFeedback.setText("");
                    mRatingBar.setRating(0);
                    Toast.makeText(FeedbackStudent.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
=======
                    Toast.makeText(MainActivity.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
                } else {
                    mFeedback.setText("");
                    mRatingBar.setRating(0);
                    Toast.makeText(MainActivity.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
>>>>>>> aaeef8dce45f1d5bc62e7502561cb5da19d35e2d
                }
            }
        });

    }
<<<<<<< HEAD

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
//        Button createFeedback = findViewById(R.id.feedback_admin_button);
//        createFeedback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FeedbackAdminMenu(view);
//            }
//        });
//}
//
//        public void FeedbackAdminMenu(View view){
//            Intent intent = new Intent(this, FeedbackMenu.class);
//            this.startActivity(intent);
//        }


=======
}
=======
        Button createFeedback = findViewById(R.id.feedback_admin_button);
        createFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedbackAdminMenu(view);
            }
        });
    }

    public void FeedbackAdminMenu(View view){
        Intent intent = new Intent(this, FeedbackMenu.class);
        this.startActivity(intent);
    }

}
>>>>>>> 222760d12a5f531b96f5762bbf2d720d05415a93
>>>>>>> aaeef8dce45f1d5bc62e7502561cb5da19d35e2d
