package com.electricbaconstudios.cutmask;

import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CutView cutView = new CutView(this, null, 0);

        FrameLayout.LayoutParams x = new FrameLayout.LayoutParams(200, 200);
        x.setMargins(100, 100, 0, 0);
        cutView.setLayoutParams(x);


        CutView cutView1 = new CutView(this, null, 1);

        FrameLayout.LayoutParams x1 = new FrameLayout.LayoutParams(200, 200);
        x1.setMargins(0, 0, 0, 0);
        cutView1.setLayoutParams(x1);



        FrameLayout fL = (FrameLayout)findViewById(R.id.linLayout);
        fL.addView(cutView);
        fL.addView(cutView1);


    }


}
