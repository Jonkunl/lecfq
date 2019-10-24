package com.unimelb.lecmore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class LectureView extends AppCompatActivity {

    static String userType;
    static String lecId;
    static String userId;
    static String userName;
    static int lectureSession;
    private static final String STUDENT = "student";
    private static final String STAFF = "staff";

    public static final String EXTRA_Lecture_ID = "com.unimelb.lecmore.lecture_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecture_view);

        lecId = getIntent().getStringExtra(EXTRA_Lecture_ID);
        userType = getIntent().getStringExtra("usertype");
        userId = getIntent().getStringExtra("userId");
        userName = getIntent().getStringExtra("userName");

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemReselectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (userType.equals(STAFF)) {
                    switch (menuItem.getItemId()) {
                        case R.id.subject_id: {
                            Fragment subject_tab = LectureViewLayoutFragment
                                    .newInstance(LectureViewLayoutFragment.SUBJECT_HOME);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.layout_fragment, subject_tab)
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        }

                        case R.id.nav_feedback: {
                            Fragment feedback_tab = LectureViewLayoutFragment
                                    .newInstance(LectureViewLayoutFragment.FEEDBACK_ADMIN);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.layout_fragment, feedback_tab)
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        }

                        case R.id.nav_questionnaire: {
                            Fragment question_tab = LectureViewLayoutFragment
                                    .newInstance(LectureViewLayoutFragment.QUESTION_ADMIN);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.layout_fragment, question_tab)
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        }
                    }
                } else {
                    switch (menuItem.getItemId()) {
                        case R.id.subject_id: {
                            Fragment subject_tab = LectureViewLayoutFragment
                                    .newInstance(LectureViewLayoutFragment.SUBJECT_HOME);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.layout_fragment, subject_tab)
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        }

                        case R.id.nav_feedback: {
                            Fragment feedback_tab = LectureViewLayoutFragment
                                    .newInstance(LectureViewLayoutFragment.FEEDBACK_STUDENT_FRAG);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.layout_fragment, feedback_tab)
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        }

                        case R.id.nav_questionnaire: {
                            Fragment question_tab = LectureViewLayoutFragment
                                    .newInstance(LectureViewLayoutFragment.QUESTION);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.layout_fragment, question_tab)
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        }
                    }

                }

                return false;
            }
        };

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemReselectedListener);


        Menu menu = navigationView.getMenu();
        MenuItem mItem = menu.findItem(R.id.subject_id);
        mItem.setTitle(lecId);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, new LectureViewLayoutFragment())
                .commit();

//        // set new title to the MenuItem
//        nav_camara.setTitle("NewTitleForCamera");
//
//        // do the same for other MenuItems
//        MenuItem nav_gallery = menu.findItem(R.id.nav_gallery);
//        nav_gallery.setTitle("NewTitleForGallery");
//
//        // add NavigationItemSelectedListener to check the navigation clicks
//        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}


