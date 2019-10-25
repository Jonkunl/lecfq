package com.unimelb.lecmore;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseManager {

    private static FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    /**
     * Method for returning the Reference of the require path from database
     * @param path String for storing the path
     * @return A string stores the reference of the database according to the path provided
     */
    public static DatabaseReference getReference(String path){
        return mDatabase.getReference(path); // mDatabase.getReference("posts/postid/time")
    }


}
