package com.electricbaconstudios.cutmask;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by spacehomunculus on 11/24/14.
 */
public class CutFastDetector implements View.OnTouchListener {

    private float mPreviousX = 0;
    private float mPreviousY = 0;

    private FrameObservable fObs;
    private Context context;

    private GestureDetectorCompat mGestureDetectorCompat;
    private CutGestureDetector mGestureDetector;

    public CutFastDetector(Context ctx, FrameObservable fObs) {
        this.context = ctx;
        this.fObs = fObs;
        mGestureDetector = new CutGestureDetector(fObs);
        mGestureDetectorCompat = new GestureDetectorCompat(ctx, mGestureDetector);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        FrameObservable.FrameState obs = fObs.getValue();

        // we only care about long touches for mode 0
//        if(obs.mode == 0) {
//            boolean didEatEvent = mGestureDetectorCompat.onTouchEvent(motionEvent);
//            if(didEatEvent) {
//                return true;
//            }
//        }

        // mode is not changed inside this class
        float x = motionEvent.getRawX();
        float y = motionEvent.getRawY();

        switch (motionEvent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                fObs.getPoints().clear();
                fObs.setValue(obs.mode, 1, 0, 0, motionEvent.getX(), motionEvent.getY());
                break;

            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;
                fObs.setValue(obs.mode, 1, dx, dy, motionEvent.getX(), motionEvent.getY());
                break;

            case MotionEvent.ACTION_UP:
                fObs.setValue(obs.mode, 2, 0, 0, motionEvent.getX(), motionEvent.getY());
                break;

            default:
                fObs.setValue(obs.mode, 0, 0, 0, 0, 0);
                break;
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;

    }
}
