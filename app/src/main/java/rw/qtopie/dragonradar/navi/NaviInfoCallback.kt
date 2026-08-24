package rw.qtopie.dragonradar.navi

import android.app.Activity
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import com.amap.api.maps.AMapException
import com.amap.api.navi.AMapNavi
import com.amap.api.navi.AmapNaviPage
import com.amap.api.navi.INaviInfoCallback
import com.amap.api.navi.model.AMapNaviLocation

class NaviInfoCallback(
    private val mainActivity: Activity,
    private val vibrator: Vibrator
) : INaviInfoCallback {

    override fun onInitNaviFailure() {}

    override fun onGetNavigationText(s: String?) {}

    override fun onLocationChange(aMapNaviLocation: AMapNaviLocation?) {
        // TODO 如果长时间没有更新5min,则自动关闭服务, 降低电池消耗
    }

    override fun onArriveDestination(b: Boolean) {}

    override fun onStartNavi(i: Int) {
        vibrator.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    override fun onCalculateRouteSuccess(ints: IntArray?) {}

    override fun onCalculateRouteFailure(i: Int) {}

    override fun onStopSpeaking() {}

    override fun onReCalculateRoute(i: Int) {
        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    override fun onExitPage(i: Int) {
        try {
            val naviPage = AmapNaviPage.getInstance()
            naviPage?.exitRouteActivity()

            val navi = AMapNavi.getInstance(mainActivity.applicationContext)
            navi?.stopNavi()
            mainActivity.finishAffinity()
        } catch (e: AMapException) {
            throw RuntimeException(e)
        }
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    override fun onStrategyChanged(i: Int) {}

    override fun onArrivedWayPoint(i: Int) {}

    override fun onMapTypeChanged(i: Int) {}

    override fun onNaviDirectionChanged(i: Int) {}

    override fun onDayAndNightModeChanged(i: Int) {}

    override fun onBroadcastModeChanged(i: Int) {}

    override fun onScaleAutoChanged(b: Boolean) {}

    override fun getCustomMiddleView(): View? {
        return null
    }

    override fun getCustomNaviView(): View? {
        return null
    }

    override fun getCustomNaviBottomView(): View? {
        return null
    }
}
