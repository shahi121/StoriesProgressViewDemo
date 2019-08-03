package com.shahi.storiesprogressviewdemo;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryViewHolder> {

    private Context context;
    private List<StoryModel> modelList;

    public StoryAdapter(Context context, List<StoryModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.story_item, viewGroup, false);
        return new StoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, final int i) {
        //List<Story> story = modelList.get(i).getStoryList();

        Picasso.get().load(modelList.get(i).getStoryList().get(0).getStoryLink()).into(holder.storyImage);


        Picasso.get().load(modelList.get(i).getUserImage()).into(holder.userStoryPicture);
        holder.userStoryName.setText(modelList.get(i).getName());
        holder.userStoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MainActivity.class)
                        .putExtra("id", String.valueOf(modelList.get(i).getStoryList().get(i))));
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
