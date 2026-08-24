package rw.qtopie.dragonradar.navi

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Window
import com.amap.api.navi.AMapNavi
import com.amap.api.navi.AMapNaviView
import com.amap.api.navi.AMapNaviViewListener
import com.amap.api.navi.AMapNaviViewOptions
import com.amap.api.navi.model.NaviLatLng
import com.google.common.collect.Lists
import rw.qtopie.dragonradar.R
import java.util.List

abstract class BaseNaviActivity : Activity(), AMapNaviViewListener {

    protected var mAMapNaviView: AMapNaviView? = null
    protected var mAMapNavi: AMapNavi? = null

    protected var mEndLatlng: NaviLatLng = NaviLatLng(40.084894, 116.603039)
    protected var mStartLatlng: NaviLatLng = NaviLatLng(39.825934, 116.342972)
    var p1: NaviLatLng = NaviLatLng(22.525628, 113.924875) // 南山地铁站
    protected val sList: List<NaviLatLng> = Lists.newArrayList()
    protected val eList: List<NaviLatLng> = Lists.newArrayList()
    protected var mWayPointList: List<NaviLatLng> = Lists.newArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.navi)
        mAMapNaviView = findViewById(R.id.naviView)

        // 设置layout visible=false加上自己的控件就可以定制UI
        val options: AMapNaviViewOptions = mAMapNaviView!!.viewOptions
        options.setLayoutVisible(false)
        options.setAutoDrawRoute(true)
        options.setAfterRouteAutoGray(true)
        options.setNaviArrowVisible(true)
        options.setLaneInfoShow(true)
        options.setAutoLockCar(true)
        options.setSensorEnable(true)
        options.setAutoDisplayOverview(false)
        mAMapNaviView!!.viewOptions = options

        mAMapNaviView!!.onCreate(savedInstanceState)
        mAMapNaviView!!.setNaviMode(AMapNaviView.NORTH_UP_MODE)
        mAMapNaviView!!.setTrafficLightsVisible(true)
        mAMapNaviView!!.setAMapNaviViewListener(this)

        sList.add(mStartLatlng)
        eList.add(mEndLatlng)
    }

    override fun onResume() {
        super.onResume()
        mAMapNaviView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mAMapNaviView!!.onPause()

//
//        停止导航之后，会触及底层stop，然后就不会再有回调了，但是讯飞当前还是没有说完的半句话还是会说完
//        mAMapNavi.stopNavi();
    }

    override fun onDestroy() {
        super.onDestroy()
        mAMapNaviView!!.onDestroy()
    }

    override fun onNaviSetting() {
        // 底部导航设置点击回调
    }

    override fun onNaviMapMode(naviMode: Int) {}

    override fun onNaviCancel() {
        finish()
    }

    override fun onNaviTurnClick() {
        // 转弯view的点击回调
    }

    override fun onNextRoadClick() {
        // 下一个道路View点击回调
    }

    override fun onScanViewButtonClick() {
        // 全览按钮点击回调
    }

    override fun onLockMap(isLock: Boolean) {
        // 锁地图状态发生变化时回调
    }

    override fun onNaviViewLoaded() {
        Log.d("wlx", "导航页面加载成功")
        Log.d("wlx", "请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑")
    }

    override fun onMapTypeChanged(i: Int) {}

    override fun onNaviViewShowMode(i: Int) {}

    override fun onNaviBackClick(): Boolean {
        return false
    }
}
