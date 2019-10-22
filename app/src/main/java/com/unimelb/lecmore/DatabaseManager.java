package com.unimelb.lecmore;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseManager {

    private static FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    public static DatabaseReference getReference(String path){
        return mDatabase.getReference(path);
    }


}
