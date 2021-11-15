package com.example.android_pdr_master;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private TextView mTxtValue1;
    private TextView mTxtValue2;
    private TextView mTxtValue3;

    StepDetectionHandler sdh;
    StepPositioningHandler sph;
    DeviceAttitudeHandler dah;

    boolean isWalking = false;


    LocationHandler lKloc;
    int stepCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtValue1 = (TextView) findViewById(R.id.txt_value1);
        mTxtValue2 = (TextView) findViewById(R.id.txt_value2);
        mTxtValue3 = (TextView) findViewById(R.id.txt_value3);
        lKloc = new LocationHandler(0.0, 0.0);
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sdh = new StepDetectionHandler(sm);
        sdh.setStepListener(mStepDetectionListener);
        dah = new DeviceAttitudeHandler(sm);
        sph = new StepPositioningHandler();
        sph.setmCurrentLocation(lKloc);
        isWalking = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sdh.start();
        dah.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        sdh.stop();
        dah.stop();
    }

    private StepDetectionHandler.StepDetectionListener mStepDetectionListener = new StepDetectionHandler.StepDetectionListener() {
        public void newStep(float stepSize) {
            stepCounter++;
            LocationHandler newloc = sph.computeNextStep(stepSize, dah.orientationVals[0]);
            Log.d("LATLNG", newloc.getxAxis() + " " + newloc.getyAxis()+ " " + dah.orientationVals[0]);
            if (isWalking) {
                DrawMap();
                mTxtValue1.setText("最新位置：" + newloc.getxAxis() +","+ newloc.getyAxis());
                mTxtValue2.setText(String.valueOf(360-dah.orientationVals[0]-90));
                mTxtValue3.setText("走过的步数：" + String.valueOf(stepCounter));
            }
        }
    };
    private void DrawMap() {
        LinearLayout layout=(LinearLayout) findViewById(R.id.root);
        final DrawView view=new DrawView(this);
        view.setMinimumHeight(500);
        view.setMinimumWidth(300);
        //通知view组件重绘
        view.invalidate();
        layout.addView(view);

    }

}

