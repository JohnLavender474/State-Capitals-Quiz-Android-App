package edu.uga.cs.csci4830_project4.frontend.async;

public interface AsyncTaskCallback<T> {
    void onAsyncTaskComplete(T result);
}
