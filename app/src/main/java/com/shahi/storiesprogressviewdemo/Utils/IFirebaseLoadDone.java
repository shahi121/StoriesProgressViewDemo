package com.shahi.storiesprogressviewdemo.Utils;

import java.util.List;

public interface IFirebaseLoadDone {
    void onFirebaseLoadSuccess(List<Movies> movies);

    void onFirebaseLoadFailed(String message);
}
