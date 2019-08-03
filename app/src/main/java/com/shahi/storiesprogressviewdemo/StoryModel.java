package com.shahi.storiesprogressviewdemo;

import java.util.List;

public class StoryModel {

    private String userImage;
    private String name;
    private List<Story> storyList;

    public StoryModel() {
    }

    public StoryModel(String userImage, String name, List<Story> storyList) {
        this.userImage = userImage;
        this.name = name;
        this.storyList = storyList;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Story> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }
}
