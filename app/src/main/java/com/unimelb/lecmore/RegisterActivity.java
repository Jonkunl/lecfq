package com.unimelb.lecmore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button mRegisterBtn;
    private EditText mUsernameText;
    private EditText mPasswordText;
    private EditText mPassword2Text;
    private RadioButton mStudentRadio;
    private EditText mEmailText;

    enum Type {
        STUDENT,
        STAFF
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterBtn = (Button) findViewById(R.id.registerBtn);
        mPassword2Text = (EditText) findViewById(R.id.passwordValue2);
        mUsernameText = (EditText) findViewById(R.id.usernameValue);
        mEmailText = (EditText) findViewById(R.id.emailValue);
        mPasswordText = (EditText) findViewById(R.id.passwordValue);
        mStudentRadio = (RadioButton) findViewById(R.id.btnStudent);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String username = mUsernameText.getText().toString();
                String password = mPasswordText.getText().toString();
                String password2 = mPassword2Text.getText().toString();
                Enum<UserLoginActivity.Type> type = mStudentRadio.isChecked() ? UserLoginActivity.Type.STUDENT : UserLoginActivity.Type.STAFF;

                String username_pattern = "^[a-zA-Z]\\w{5,}$";
                Boolean username_ok = mUsernameText.getText().toString().matches(username_pattern);
                String email_pattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
                Boolean email_ok = mEmailText.getText().toString().matches(email_pattern);
                String password_pattern = ".{6,}";
                Boolean password_ok = mPasswordText.getText().toString().matches(password_pattern);
                Boolean password_confirmed = password.equals(password2);
                if(!username_ok){
                    Toast.makeText(RegisterActivity.this, "Username has to start with a letter and has to be more than length of 6.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!email_ok){
                    Toast.makeText(RegisterActivity.this, "Please enter correct email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password_ok){
                    Toast.makeText(RegisterActivity.this, "Password has to be more than length of 6.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password_confirmed){
                    Toast.makeText(RegisterActivity.this, "Two Password is not identical.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(RegisterActivity.this, "Go to database and check register information.", Toast.LENGTH_SHORT).show();
                // String sql = ...
                // Query(databse, sql)
            }
        });
    }



}
