package io.agora.propeller.ui;

import java.util.ArrayList;

import io.agora.rtc.video.VideoCompositingLayout;

/**
 * Create by xjs
 * _______date : 17/5/19
 * _______description:
 */
public class MyVideoCompositingLayout extends VideoCompositingLayout {
    private ArrayList<Region> regionArrayList = null;

    public MyVideoCompositingLayout() {
        canvasWidth = 1280;
        canvasHeight = 720;
        backgroundColor = "FFFFFF";
        regionArrayList = new ArrayList<>();
        appData = new byte[0];
    }

    public void addRegion(Region region) {
        regionArrayList.add(region);
    }

    public void createRegion() {
        regions = (Region[]) regionArrayList.toArray();
    }

}
