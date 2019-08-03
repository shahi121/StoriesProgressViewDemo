package com.shahi.storiesprogressviewdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahi.storiesprogressviewdemo.Utils.IFirebaseLoadDone;
import com.shahi.storiesprogressviewdemo.Utils.Movies;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class MainActivity extends AppCompatActivity implements IFirebaseLoadDone {

    private StoriesProgressView progressView;
    private ImageView imageView;
    private IFirebaseLoadDone firebaseLoadDone;
    private int count = 0;
    private DatabaseReference dbRef;
    private long pressTime = 0L;
    private long limit = 500L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        if (getIntent() != null) {
            String id = getIntent().getStringExtra("id");
            Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();
        }

        dbRef = FirebaseDatabase.getInstance().getReference("Story");

        firebaseLoadDone = this;

        progressView = (StoriesProgressView) findViewById(R.id.stories);
        imageView = (ImageView) findViewById(R.id.imageView);

        onLoad();

        // bind reverse view
        View reverse = (View) findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressView.reverse();
            }
        });
        reverse.setOnTouchListener(onTouchListener);

        // bind skip view
        View skip = (View) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressView.skip();
            }
        });
        skip.setOnTouchListener(onTouchListener);
    }

    private void onLoad() {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Movies> moviesList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Movies movies = snapshot.getValue(Movies.class);
                    moviesList.add(movies);
                }

                firebaseLoadDone.onFirebaseLoadSuccess(moviesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                firebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onFirebaseLoadSuccess(final List<Movies> movies) {
        progressView.setStoriesCount(movies.size());
        progressView.setStoryDuration(2000L);
        Picasso.get().load(movies.get(0).getImage()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressView.startStories();
                progressView.setStoriesListener(new StoriesProgressView.StoriesListener() {
                    @Override
                    public void onNext() {
                        if (count < movies.size()) {
                            count++;
                            Picasso.get().load(movies.get(count).getImage()).into(imageView);
                        }
                    }

                    @Override
                    public void onPrev() {
                        if (count > 0) {
                            count--;
                            Picasso.get().load(movies.get(count).getImage()).into(imageView);
                        }
                    }

                    @Override
                    public void onComplete() {
                        count = 0;
                        Toast.makeText(MainActivity.this, "Finished", Toast.LENGTH_SHORT).show();
                        onLoad();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(MainActivity.this, "onError " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    progressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    progressView.resume();
                    return limit < now - pressTime;
            }
            return false;
        }
    };

    @Override
    protected void onDestroy() {
        // Very important !
        progressView.destroy();
        super.onDestroy();
    }

}
