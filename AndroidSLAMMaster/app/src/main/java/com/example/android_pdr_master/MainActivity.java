package com.example.android_pdr_master;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private TextView mTxtValue1;
    private TextView mTxtValue2;
    private TextView mTxtValue3;
    private Button mGenerateButton;
    private Button mRemoveButton;
    private LinearLayout mLayout;

    StepDetectionHandler sdh;
    StepPositioningHandler sph;
    DeviceAttitudeHandler dah;

    boolean isWalking = false;

    LocationHandler lKloc;
    int stepCounter = 0;

    private List<Position> PositionSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PositionSet = new ArrayList<>();

        mTxtValue1 = findViewById(R.id.txt_value1);
        mTxtValue2 = findViewById(R.id.txt_value2);
        mTxtValue3 = findViewById(R.id.txt_value3);
        mGenerateButton = findViewById(R.id.generateButton);
        mRemoveButton = findViewById(R.id.removeButton);
        mLayout = findViewById(R.id.root);

        lKloc = new LocationHandler(0.0, 0.0);
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sdh = new StepDetectionHandler(sm);
        sdh.setStepListener(mStepDetectionListener);
        dah = new DeviceAttitudeHandler(sm);
        sph = new StepPositioningHandler();
        sph.setmCurrentLocation(lKloc);
        isWalking = true;

        mGenerateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });

        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.removeAllViewsInLayout();
                sph.setmCurrentLocation(lKloc);
                stepCounter = 0;
            }
        });
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
                mTxtValue1.setText("最新位置：" + newloc.getxAxis() +","+ newloc.getyAxis());
                mTxtValue2.setText(String.valueOf(360-dah.orientationVals[0]-90));
                mTxtValue3.setText("步数：" + stepCounter);

                PositionSet.add(CoordianteTrans(new Position(newloc.getxAxis().floatValue(),newloc.getyAxis().floatValue())));
            }
        }
    };

    //坐标转换
    private Position CoordianteTrans(Position position){
        Position newPosition = new Position(position.getX()*80/3+600,position.getY()*80/3+960);
        System.out.println("newPosition"+newPosition.getX()+" "+ newPosition.getY());
        return newPosition;
    }

    //绘图
    private void init(){
        final DrawView view=new DrawView(this,PositionSet);
        view.setMinimumHeight(500);
        view.setMinimumWidth(300);
        //通知view组件重绘
        view.invalidate();
        mLayout.addView(view);
    }

}

