package rw.qtopie.dragonradar.navi

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView
import androidx.annotation.Nullable
import com.amap.api.navi.view.NextTurnTipView
import rw.qtopie.dragonradar.R

class HelloBikeNaviActivity : BaseNaviActivity() {

    companion object {
        const val DEBUG_TAG = "dragon-radar-hello-bike"
    }

    private var textNextRoadDistance: TextView? = null // 下个路口距离
    private var nextTurnTipView: NextTurnTipView? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nextTurnTipView = findViewById(R.id.nextTurnView)
        mAMapNaviView!!.setLazyNextTurnTipView(nextTurnTipView)

        textNextRoadDistance = findViewById(R.id.text_next_road_distance)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(DEBUG_TAG, "Action was DOWN")
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(DEBUG_TAG, "Action was MOVE")
                return true
            }
            MotionEvent.ACTION_UP -> {
                Log.d(DEBUG_TAG, "Action was UP")
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d(DEBUG_TAG, "Action was CANCEL")
                return true
            }
            MotionEvent.ACTION_OUTSIDE -> {
                Log.d(DEBUG_TAG, "Movement occurred outside bounds of current screen element")
                return true
            }
            else -> return super.onTouchEvent(event)
        }
    }

//    override fun onInitNaviSuccess() {
////        val ok = mAMapNavi.calculateRideRoute(p1)
////        Log.d(DEBUG_TAG, "inited navi: $ok")
//    }

//    override fun onCalculateRouteSuccess(var1: AMapCalcRouteResult) {
//        super.onCalculateRouteSuccess(var1)
//        mAMapNavi.startNavi(NaviType.GPS)
//    }

    override fun onBackPressed() {
        Log.d(DEBUG_TAG, "back button pressed")
    }

    override fun onDestroy() {
        super.onDestroy()
        // 在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mAMapNaviView!!.onDestroy()
    }

//    override fun onNaviInfoUpdate(naviInfo: NaviInfo) {
//        super.onNaviInfoUpdate(naviInfo)
//
//        /**
//         * 更新路口转向图标
//         */
//        if (naviInfo.iconBitmap != null) {
//            nextTurnTipView!!.setImageBitmap(naviInfo.iconBitmap)
//        } else {
//            nextTurnTipView!!.iconType = naviInfo.iconType
//        }
//
//        // 更新下一路口 路名及 距离
//        textNextRoadDistance!!.text = formatKM(naviInfo.curStepRetainDistance)
//    }
//
//    companion object {
//        fun formatKM(d: Int): String {
//            if (d == 0) {
//                return "0m"
//            } else if (d < 100) {
//                return d.toString() + "m"
//            } else if (d < 1000) {
//                return d.toString() + "m"
//            } else if (d < 10000) {
//                return (d / 10) * 10 / 1000.0.toString() + "km"
//            } else if (d < 100000) {
//                return (d / 100) * 100 / 1000.0.toString() + "km"
//            }
//            return (d / 1000).toString() + "km"
//        }
//    }
}
