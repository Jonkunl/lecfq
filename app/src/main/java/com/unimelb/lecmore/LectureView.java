package com.unimelb.lecmore;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class LectureView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecture_view);

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem mItem = menu.findItem(R.id.subject_id);
        mItem.setTitle("COMP90018");

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

}
