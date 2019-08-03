package com.shahi.storiesprogressviewdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DatabaseReference dbRef;
    private StoryAdapter adapter;
    private List<StoryModel> modelList;
    private RecyclerView recyclerView;

    String link = "https://firebasestorage.googleapis.com/v0/b/fir-application-3cfe4.appspot.com/o/Images%2FIMG-20190525-WA0016.jpg?alt=media&token=4f1a1302-0c50-488a-86ee-0eb295410e42";
    String proLink = "https://firebasestorage.googleapis.com/v0/b/fir-application-3cfe4.appspot.com/o/Images%2FIMG-20190525-WA0018.jpg?alt=media&token=6625713f-8e22-4601-969c-8359b95f3751";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        List<Story> story = new ArrayList<>();
        story.add(new Story("5-25-2019", link));
        final StoryModel model = new StoryModel(proLink, "Shahi", story);

        modelList = new ArrayList<>();

        dbRef = FirebaseDatabase.getInstance().getReference("Movies");
        dbRef.push().setValue(model);

        adapter = new StoryAdapter(this, modelList);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        modelList.clear();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    modelList.add(snapshot.getValue(StoryModel.class));
                }
                //modelList.add(dataSnapshot.getValue(StoryModel.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
