package com.electricbaconstudios.cutmask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by spacehomunculus on 11/22/14.
 */
public class CutView extends FrameLayout implements Observer {

    private CutFastDetector fastDetector;

    private Context context;

    private FrameObservable fObs;

    private CutImageView img;

    private int mode;

    public CutView(Context ctx) {
        super(ctx);
        context = ctx;
        setupViews();
    }

    public CutView(Context ctx, AttributeSet attr, int mode) {
        super(ctx, attr);
        context = ctx;
        this.mode = mode;
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        float touchSlop = configuration.getScaledTouchSlop();

        setupGestureRecognizers();
        setupViews();
    }




    private void setupViews() {
        img = new CutImageView(context, fObs);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageResource(R.drawable.ic_launcher);
        img.setLayoutParams(new LayoutParams(200, 200));
        img.setBackgroundColor(Color.TRANSPARENT);
        addView(img);
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    private void setupGestureRecognizers() {
        fObs = new FrameObservable();
        fObs.setValue(mode, 0, 0, 0, 0, 0);
        fObs.addObserver(this);
        fastDetector = new CutFastDetector(context, fObs);
        this.setOnTouchListener(fastDetector);

    }


    @Override
    public void update(Observable observable, Object o) {
        if(observable == fObs) {
            FrameObservable.FrameState fState = fObs.getValue();

            if(fState.mode == 0) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
                int left = (int) fState.panLeft + params.leftMargin;
                int top = (int) fState.panTop + params.topMargin;
                params.setMargins(left, top, 0, 0);
                setLayoutParams(params);
            } else if(fState.mode == 1) {
                if(fState.state != 0) {
                    img.invalidate();
                }

            }
        }

    }



}
