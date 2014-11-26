package com.electricbaconstudios.cutmask;

import android.graphics.Point;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by spacehomunculus on 11/23/14.
 */
public class FrameObservable extends Observable {


    public class FrameState {
        public FrameState(){}
        public int state;
        public int mode;
        public float panLeft;
        public float panTop;
        public float cutLeft;
        public float cutTop;

    }

    private ArrayList<Point> points = new ArrayList<Point>();

    private FrameState fState;

    public FrameObservable() {
        fState = new FrameState();
    }

    public FrameState getValue() {
        return fState;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }


    public void setValue(int mode, int state, float panLeft, float panTop, float cutLeft, float cutTop) {
        fState.mode = mode;
        fState.state = state;


        fState.panLeft = panLeft;
        fState.panTop = panTop;
        fState.cutLeft = cutLeft;
        fState.cutTop = cutTop;

        if(mode == 1) {
            points.add(new Point((int)cutLeft, (int)cutTop));
        }
        setChanged();
        notifyObservers();
    }

}
