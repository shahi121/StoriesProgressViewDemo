package com.shahi.storiesprogressviewdemo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout userStoryImage;
    public ImageView storyImage;
    public CircleImageView userStoryPicture;
    public TextView userStoryName;


    public StoryViewHolder(@NonNull View itemView) {
        super(itemView);

        userStoryImage = (RelativeLayout)itemView.findViewById(R.id.userStoryImage);
        storyImage = (ImageView)itemView.findViewById(R.id.storyImage);
        userStoryPicture = (CircleImageView)itemView.findViewById(R.id.userStoryPicture);
        userStoryName = (TextView)itemView.findViewById(R.id.userStoryName);
    }
}
