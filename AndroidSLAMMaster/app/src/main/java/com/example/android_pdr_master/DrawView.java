package com.example.android_pdr_master;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.List;

public class DrawView extends View {
    private List<Position> PositionSet;
    public DrawView(Context context, List positionSet) {
        super(context);
        PositionSet = positionSet;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 创建画笔
        Paint p = new Paint();
        p.setStrokeWidth(4);
        p.setTextSize(20);
        p.setColor(Color.BLACK);// 设置颜色

        for(int start = 0,end = 1; end < PositionSet.size(); start++,end++){
            canvas.drawLine(PositionSet.get(start).getX(),PositionSet.get(start).getY(),PositionSet.get(end).getX(),PositionSet.get(end).getY(),p);
        }

        canvas.drawText("start",PositionSet.get(0).getX(),PositionSet.get(0).getY(),p);
        canvas.drawText("end",PositionSet.get(PositionSet.size()-1).getX(),PositionSet.get(PositionSet.size()-1).getY(),p);

    }

}
