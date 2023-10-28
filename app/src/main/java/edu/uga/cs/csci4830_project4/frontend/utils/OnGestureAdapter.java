package edu.uga.cs.csci4830_project4.frontend.utils;

import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface OnGestureAdapter extends GestureDetector.OnGestureListener {

    default boolean onDown(@NonNull MotionEvent e) {
        return false;
    }


    default void onShowPress(@NonNull MotionEvent e) {

    }


    default boolean onSingleTapUp(@NonNull MotionEvent e) {
        return false;
    }


    default boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX,
                             float distanceY) {
        return false;
    }


    default void onLongPress(@NonNull MotionEvent e) {

    }


    default boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX,
                            float velocityY) {
        return false;
    }
}
