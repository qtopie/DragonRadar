package rw.qtopie.dragonradar.navi

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.IBinder
import android.util.Log
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import androidx.wear.ongoing.OngoingActivity
import androidx.wear.ongoing.Status
import com.amap.api.maps.AMapException
import com.amap.api.navi.AMapNavi
import com.amap.api.navi.AMapNaviListener
import com.amap.api.navi.model.AMapCalcRouteResult
import com.amap.api.navi.model.AMapLaneInfo
import com.amap.api.navi.model.AMapModelCross
import com.amap.api.navi.model.AMapNaviCameraInfo
import com.amap.api.navi.model.AMapNaviCross
import com.amap.api.navi.model.AMapNaviLocation
import com.amap.api.navi.model.AMapNaviRouteNotifyData
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo
import com.amap.api.navi.model.AMapServiceAreaInfo
import com.amap.api.navi.model.AimLessModeCongestionInfo
import com.amap.api.navi.model.AimLessModeStat
import com.amap.api.navi.model.NaviInfo
import rw.qtopie.dragonradar.R

class DragonRadarNaviService : Service(), AMapNaviListener {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "riding_navi_channel_01"
        private const val NOTIFICATION_ID = 330704
    }

    private var mAMapNavi: AMapNavi? = null

    override fun onCreate() {
        super.onCreate()

        try {
            mAMapNavi = AMapNavi.getInstance(applicationContext)

            // 暂时不启用播报
            mAMapNavi!!.setUseInnerVoice(false, false)

            // 设置模拟导航的行车速度
            mAMapNavi!!.setEmulatorNaviSpeed(25)
            mAMapNavi!!.addAMapNaviListener(this)
        } catch (e: AMapException) {
            throw RuntimeException(e)
        }
    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // The service is starting, due to a call to startService()
        startForeground()
        Log.d("dragonradar", "started service")
        return Service.START_STICKY_COMPATIBILITY
    }

    private fun startForeground() {
        Log.d("dragonradar", "starting service")
        val titleText = getString(R.string.app_name)
        val text = getString(R.string.app_name)

        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, text, NotificationManager.IMPORTANCE_DEFAULT)

        val nm = getSystemService(NotificationManager::class.java)
        nm.createNotificationChannel(channel)

        val bigTextStyle = NotificationCompat.BigTextStyle().bigText("test").setBigContentTitle("test123")
        val launchActivityIntent = Intent(applicationContext, DragonRouteActivity::class.java)

        val activityPendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            launchActivityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

//        val cancelIntent = Intent(this, ForegroundOnlyWalkingWorkoutService::class.java)
//        cancelIntent.putExtra(EXTRA_CANCEL_WORKOUT_FROM_NOTIFICATION, true)
//
//        val servicePendingIntent = PendingIntent.getService(
//                this,
//                0,
//                cancelIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
//                )

        val notificationBuilder =
            NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                .setStyle(bigTextStyle)
                .setContentTitle(titleText)
                .setContentText(titleText)
                .setSmallIcon(R.drawable.ic_walk)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setOngoing(true)
                .addAction(
                    NotificationCompat.Action(
                        R.drawable.ic_walk,
                        getString(R.string.app_name),
                        activityPendingIntent
                    )
                )
                .setCategory(NotificationCompat.CATEGORY_WORKOUT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOngoing(true)

        val ongoingActivityStatus: Status = Status.Builder()
            // Sets the text used across various surfaces.
            .addTemplate(text)
            .build()

        val ongoingActivity =
            OngoingActivity.Builder(applicationContext, NOTIFICATION_ID, notificationBuilder)
                // Sets icon that will appear on the watch face in active mode. If it isn't set,
                // the watch face will use the static icon in active mode.
                .setAnimatedIcon(R.drawable.animated_walk)
                // Sets the icon that will appear on the watch face in ambient mode.
                // Falls back to Notification's smallIcon if not set. If neither is set,
                // an Exception is thrown.
                .setStaticIcon(R.drawable.ic_walk)
                // Sets the tap/touch event, so users can re-enter your app from the
                // other surfaces.
                // Falls back to Notification's contentIntent if not set. If neither is set,
                // an Exception is thrown.
                .setTouchIntent(activityPendingIntent)
                // In our case, sets the text used for the Ongoing Activity (more options are
                // available for timers and stop watches).
                .setStatus(ongoingActivityStatus)
                .build()

        ongoingActivity.apply(applicationContext)

        val type = ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
        startForeground(NOTIFICATION_ID, notificationBuilder.build(), type)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mAMapNavi != null) {
            mAMapNavi!!.stopNavi()
            Log.d(DEBUG_TAG, "navigation stopped")
        }
    }

    override fun onInitNaviFailure() {}

    override fun onInitNaviSuccess() {}

    override fun onStartNavi(i: Int) {}

    override fun onTrafficStatusUpdate() {}

    override fun onLocationChange(aMapNaviLocation: AMapNaviLocation?) {}

    override fun onGetNavigationText(i: Int, s: String?) {}

    override fun onGetNavigationText(s: String?) {}

    override fun onEndEmulatorNavi() {}

    override fun onArriveDestination() {
        onDestroy()
    }

    override fun onCalculateRouteFailure(i: Int) {}

    override fun onReCalculateRouteForYaw() {}

    override fun onReCalculateRouteForTrafficJam() {}

    override fun onArrivedWayPoint(i: Int) {}

    override fun onGpsOpenStatus(b: Boolean) {}

    override fun onNaviInfoUpdate(naviInfo: NaviInfo?) {}

    override fun updateCameraInfo(aMapNaviCameraInfos: Array<AMapNaviCameraInfo?>?) {}

    override fun updateIntervalCameraInfo(
        aMapNaviCameraInfo: AMapNaviCameraInfo?,
        aMapNaviCameraInfo1: AMapNaviCameraInfo?,
        i: Int
    ) {
    }

    override fun onServiceAreaUpdate(aMapServiceAreaInfos: Array<AMapServiceAreaInfo?>?) {}

    override fun showCross(aMapNaviCross: AMapNaviCross?) {}

    override fun hideCross() {}

    override fun showModeCross(aMapModelCross: AMapModelCross?) {}

    override fun hideModeCross() {}

    override fun showLaneInfo(aMapLaneInfos: Array<AMapLaneInfo?>?, bytes: ByteArray?, bytes1: ByteArray?) {}

    override fun showLaneInfo(aMapLaneInfo: AMapLaneInfo?) {}

    override fun hideLaneInfo() {}

    override fun onCalculateRouteSuccess(ints: IntArray?) {}

    override fun notifyParallelRoad(i: Int) {}

    override fun OnUpdateTrafficFacility(aMapNaviTrafficFacilityInfos: Array<AMapNaviTrafficFacilityInfo?>?) {}

    override fun OnUpdateTrafficFacility(aMapNaviTrafficFacilityInfo: AMapNaviTrafficFacilityInfo?) {}

    override fun updateAimlessModeStatistics(aimLessModeStat: AimLessModeStat?) {}

    override fun updateAimlessModeCongestionInfo(aimLessModeCongestionInfo: AimLessModeCongestionInfo?) {}

    override fun onPlayRing(i: Int) {}

    override fun onCalculateRouteSuccess(aMapCalcRouteResult: AMapCalcRouteResult?) {}

    override fun onCalculateRouteFailure(aMapCalcRouteResult: AMapCalcRouteResult?) {}

    override fun onNaviRouteNotify(aMapNaviRouteNotifyData: AMapNaviRouteNotifyData?) {}

    override fun onGpsSignalWeak(b: Boolean) {}
}
