package com.example.android_pdr_master;

public class StepPositioningHandler {

    private LocationHandler mCurrentLocation;
    private static final int eRadius = 6371000; //地球半径

    public LocationHandler getmCurrentLocation() {
        return mCurrentLocation;
    }

    public void setmCurrentLocation(LocationHandler mCurrentLocation) {
        this.mCurrentLocation = mCurrentLocation;
    }

    public LocationHandler computeNextStep(float stepSize,float bearing) {
        bearing = (float) (bearing * Math.PI / 180);
        LocationHandler newLoc = new LocationHandler(mCurrentLocation.getxAxis(), mCurrentLocation.getyAxis());
        float angDistance = stepSize / eRadius;
        double newX = mCurrentLocation.getxAxis() - stepSize*Math.sin(bearing);
        double newY = mCurrentLocation.getyAxis() - stepSize*Math.cos(bearing);
        newLoc.setxAxis(newX);
        newLoc.setyAxis(newY);
        mCurrentLocation = newLoc;
        return newLoc;
    }
}
