package rw.qtopie.dragonradar.navi;

import static rw.qtopie.dragonradar.navi.DragonRouteActivity.DEBUG_TAG;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;

import rw.qtopie.dragonradar.R;

public class PredefinedNaviActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置隐私权政策
        Context context = this.getApplicationContext();
        MapsInitializer.updatePrivacyShow(context, true, true);
        MapsInitializer.updatePrivacyAgree(context, true);


//终点

        LatLng s1 = new LatLng(40.084894, 116.603039);
        LatLng t1 = new LatLng(39.825934, 116.603039);

        Poi start = new Poi("start", s1, "");
        Poi end = new Poi("end", t1, "");

// 组件参数配置
        AmapNaviParams params = new AmapNaviParams(start, null, end, AmapNaviType.RIDE, AmapPageType.NAVI);
        params.setShowVoiceSetings(false);
        params.setShowExitNaviDialog(false);
        params.setUseInnerVoice(false);
        params.setShowCrossImage(false);
        params.setShowRouteStrategyPreferenceView(false);
        params.setDrawBackUpOverlay(false);


        INaviInfoCallback callback = new INaviInfoCallback() {
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
//                startActivity(bikeIntent);
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
                View view = findViewById(R.id.naviView);
                view.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        // Respond to touch events.
                        Log.d(DEBUG_TAG, "Action was DOWN");
                        return true;
                    }
                });

                return view;
            }

            @Override
            public View getCustomNaviBottomView() {
                return null;
            }
        };

// 启动组件
        AmapNaviPage.getInstance()
                .showRouteActivity(getApplicationContext(), params, callback, DragonRouteActivity.class);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
