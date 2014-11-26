package com.electricbaconstudios.cutmask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by spacehomunculus on 11/24/14.
 */
public class CutImageView extends ImageView {

    private Context context;

    private Path path = new Path();

    private FrameObservable fObs;

    public CutImageView(Context ctx, FrameObservable fObs) {
        super(ctx);
        context = ctx;
        this.fObs = fObs;
    }

    public CutImageView(Context ctx, AttributeSet attr) {
        super(ctx, attr);
        context = ctx;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        FrameObservable.FrameState fState = fObs.getValue();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b =  ((BitmapDrawable)drawable).getBitmap() ;
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth(), h = getHeight();


        if(fState.state == 0) {
            Bitmap preppedBitmap = prepareBitmap(b, w, h);
            canvas.drawBitmap(preppedBitmap, 0, 0, null);
        } else if(fState.state == 1) {
            Bitmap preppedBitmap = prepareBitmap(b, w, h);
            canvas.drawBitmap(preppedBitmap, 0, 0, null);
            drawUIPath(canvas);
        } else if(fState.state == 2) {
            Bitmap roundBitmap =  generateBitmapFromPoints(b, w, h);
            if(roundBitmap == null) {
                Bitmap preppedBitmap = prepareBitmap(b, w, h);
                canvas.drawBitmap(preppedBitmap, 0, 0, null);
            } else {
                canvas.drawBitmap(roundBitmap, 0, 0, null);
                fObs.setValue(fState.mode, 0, 0, 0, 0, 0);
            }
        }

    }

    public Bitmap prepareBitmap(Bitmap inputBitmap, float width, float height) {
        Bitmap outputBitmap;
        if(inputBitmap.getWidth() != width || inputBitmap.getHeight() != height) {
            outputBitmap = Bitmap.createScaledBitmap(inputBitmap, (int)width, (int)height, false);
        } else {
            outputBitmap = inputBitmap;
        }
        return outputBitmap;
    }

    public Bitmap generateBitmapFromPoints(Bitmap inputBitmap, float width, float height) {

        Path path = generatePath();
        if(path.isEmpty() == false) {
            Bitmap sbmp = prepareBitmap(inputBitmap, width, height);
            Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
                    sbmp.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xffa19774;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.parseColor("#BAB399"));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(path, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(sbmp, rect, rect, paint);


            return output;
        }

        return null;

    }

    public void drawUIPath(Canvas canvas) {
        Path path = generatePath();
        path.setFillType(Path.FillType.EVEN_ODD);
        if(path.isEmpty() == false) {
            final Paint linePaint = new Paint();
            canvas.drawARGB(0, 0, 0, 0);
            linePaint.setStrokeWidth(4);
            linePaint.setColor(Color.RED);
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setAntiAlias(true);

            canvas.drawPath(path, linePaint);
        }
    }

    public Path generatePath() {
        Path path = new Path();

        ArrayList<Point> points = fObs.getPoints();

        if(points.size() > 0) {
            Point begin = points.get(0);
            path.moveTo(begin.x, begin.y);
            for (Point point : points) {
                path.lineTo(point.x, point.y);
            }
            path.lineTo(begin.x, begin.y);
            path.close();
        }
        return path;
    }




}
