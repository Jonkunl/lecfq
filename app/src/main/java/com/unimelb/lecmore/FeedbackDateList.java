package com.unimelb.lecmore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDateList extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.unimelb.lecmore.MESSAGE";

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private ArrayList<Dates> datelist = new ArrayList<>();
    private ArrayList<String> keyMap = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_history);


        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("feedback");


        final ListView listView = (ListView) findViewById(R.id.date_list);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    String key = keyNode.getKey();
                    if (!keyMap.contains(key)) {
                        keyMap.add(key);
                        datelist.add(new Dates(R.drawable.dot, key.substring(7,9)+"/"+
                                key.substring(5,7)+"/"+key.substring(1,5)));
                    }

                    MyAdapter adapter = new MyAdapter(FeedbackDateList.this, datelist);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Dates item = (Dates) adapterView.getItemAtPosition(i);
                            String date = item.getDate();
                            sendDateTime(view, date);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FirebaseDataset", "loadPost:onCancelled", databaseError.toException());
            }
        });




    }

    private AdapterView.OnItemClickListener dateClickedHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Dates item = (Dates) adapterView.getItemAtPosition(i);
            String date = item.getDate();
            sendDateTime(view, date);
        }
    };

    public void sendDateTime(View view, String date){
        Intent intent = new Intent(this, AdminFeedbackList.class);
        intent.putExtra(EXTRA_MESSAGE, date);
        this.startActivity(intent);
    }

    class MyAdapter extends ArrayAdapter<Dates> {
        MyAdapter (Context context, ArrayList<Dates> dates) {
            super(context, 0, dates);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Dates dates = getItem(position);

            //check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.feedback_date_list, parent, false);
            }

            //lookup view for data population
            ImageView dot = (ImageView) convertView.findViewById(R.id.dot_image);
            TextView date = (TextView) convertView.findViewById(R.id.date_text);

            dot.setImageResource(dates.getDot());
            date.setText(dates.getDate());

            return convertView;
        }
    }
}
