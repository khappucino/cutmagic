package com.electricbaconstudios.cutmask;

import android.gesture.Gesture;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import java.util.Observable;

/**
 * Created by spacehomunculus on 11/23/14.
 */
public class CutGestureDetector extends GestureDetector.SimpleOnGestureListener {

    private float mPreviousX;
    private float mPreviousY;

    private FrameObservable fObs;


    public CutGestureDetector(FrameObservable obs) {
        fObs = obs;
    }

    @Override
    public void onLongPress (MotionEvent e) {
        FrameObservable.FrameState currentFobs = fObs.getValue();
        int val = 0;
        if(currentFobs.mode == 0) {
            val = 1;
        } else {
            val = 0;
        }
        fObs.setValue(val, 0, 0, 0, 0, 0);
    }


}
