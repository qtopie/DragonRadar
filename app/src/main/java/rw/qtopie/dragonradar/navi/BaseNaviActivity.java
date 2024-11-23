package rw.qtopie.dragonradar.navi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.model.NaviLatLng;
import com.google.common.collect.Lists;

import java.util.List;

import rw.qtopie.dragonradar.R;


public abstract class BaseNaviActivity extends Activity implements AMapNaviViewListener {

    protected AMapNaviView mAMapNaviView;
    protected AMapNavi mAMapNavi;

    protected NaviLatLng mEndLatlng = new NaviLatLng(40.084894, 116.603039);
    protected NaviLatLng mStartLatlng = new NaviLatLng(39.825934, 116.342972);
    NaviLatLng p1 = new NaviLatLng(22.525628, 113.924875);//南山地铁站
    protected final List<NaviLatLng> sList = Lists.newArrayList();
    protected final List<NaviLatLng> eList = Lists.newArrayList();
    protected List<NaviLatLng> mWayPointList = Lists.newArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.navi);
        mAMapNaviView = findViewById(R.id.naviView);

        // 设置layout visible=false加上自己的控件就可以定制UI
        AMapNaviViewOptions options = mAMapNaviView.getViewOptions();
        options.setLayoutVisible(false);
        options.setAutoDrawRoute(true);
        options.setAfterRouteAutoGray(true);
        options.setNaviArrowVisible(true);
        options.setLaneInfoShow(true);
        options.setAutoLockCar(true);
        options.setSensorEnable(true);
        options.setAutoDisplayOverview(false);
        mAMapNaviView.setViewOptions(options);

        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setNaviMode(AMapNaviView.NORTH_UP_MODE);
        mAMapNaviView.setTrafficLightsVisible(true);
        mAMapNaviView.setAMapNaviViewListener(this);

        sList.add(mStartLatlng);
        eList.add(mEndLatlng);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNaviView.onPause();

//
//        停止导航之后，会触及底层stop，然后就不会再有回调了，但是讯飞当前还是没有说完的半句话还是会说完
//        mAMapNavi.stopNavi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapNaviView.onDestroy();
    }


    @Override
    public void onNaviSetting() {
        //底部导航设置点击回调
    }

    @Override
    public void onNaviMapMode(int naviMode) {
    }

    @Override
    public void onNaviCancel() {
        finish();
    }


    @Override
    public void onNaviTurnClick() {
        //转弯view的点击回调
    }

    @Override
    public void onNextRoadClick() {
        //下一个道路View点击回调
    }


    @Override
    public void onScanViewButtonClick() {
        //全览按钮点击回调
    }

    @Override
    public void onLockMap(boolean isLock) {
        //锁地图状态发生变化时回调
    }

    @Override
    public void onNaviViewLoaded() {
        Log.d("wlx", "导航页面加载成功");
        Log.d("wlx", "请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑");
    }

    @Override
    public void onMapTypeChanged(int i) {

    }

    @Override
    public void onNaviViewShowMode(int i) {

    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

}
