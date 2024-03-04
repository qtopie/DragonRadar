package rw.qtopie.dragonradar.navi;


import android.app.Activity;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;

import com.amap.api.maps.AMapException;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;

public class NaviInfoCallback implements INaviInfoCallback {

    private final Activity mainActivity;
    private final Vibrator vibrator;

    public NaviInfoCallback(Activity activity,
                            Vibrator vibrator) {
        this.mainActivity = activity;
        this.vibrator = vibrator;
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {
        this.vibrator.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {
        try {
            AmapNaviPage naviPage = AmapNaviPage.getInstance();
            if (naviPage != null) {
                naviPage.exitRouteActivity();
            }

            AMapNavi navi = AMapNavi.getInstance(mainActivity.getApplicationContext());
            if (navi != null) {
                navi.stopNavi();
            }
            mainActivity.finishAffinity();
        } catch (AMapException e) {
            throw new RuntimeException(e);
        }
        this.vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onMapTypeChanged(int i) {

    }

    @Override
    public void onNaviDirectionChanged(int i) {

    }

    @Override
    public void onDayAndNightModeChanged(int i) {

    }

    @Override
    public void onBroadcastModeChanged(int i) {

    }

    @Override
    public void onScaleAutoChanged(boolean b) {

    }

    @Override
    public View getCustomMiddleView() {
        return null;
    }

    @Override
    public View getCustomNaviView() {
        return null;
    }

    @Override
    public View getCustomNaviBottomView() {
        return null;
    }
}
