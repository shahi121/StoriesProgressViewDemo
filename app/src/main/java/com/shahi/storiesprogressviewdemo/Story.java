package com.shahi.storiesprogressviewdemo;

public class Story {

    private String storyTime;
    private String storyLink;

    public Story() {
    }

    public Story(String storyTime, String storyLink) {
        this.storyTime = storyTime;
        this.storyLink = storyLink;
    }

    public String getStoryTime() {
        return storyTime;
    }

    public void setStoryTime(String storyTime) {
        this.storyTime = storyTime;
    }

    public String getStoryLink() {
        return storyLink;
    }

    public void setStoryLink(String storyLink) {
        this.storyLink = storyLink;
    }
}
