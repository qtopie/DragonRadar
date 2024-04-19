package rw.qtopie.dragonradar.navi;

import static rw.qtopie.dragonradar.navi.DragonRouteActivity.DEBUG_TAG;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.wear.ongoing.OngoingActivity;
import androidx.wear.ongoing.Status;

import com.amap.api.maps.AMapException;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.ParallelRoadListener;
import com.amap.api.navi.enums.AMapNaviParallelRoadStatus;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;

import rw.qtopie.dragonradar.MainActivity;
import rw.qtopie.dragonradar.R;

public class DragonRadarNaviService extends Service implements AMapNaviListener, ParallelRoadListener {

    public static final String NOTIFICATION_CHANNEL_ID = "riding_navi_channel_01";
    private static final int NOTIFICATION_ID = 330704;

    private AMapNavi mAMapNavi;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            mAMapNavi = AMapNavi.getInstance(getApplicationContext());

            mAMapNavi.addAMapNaviListener(this);
            mAMapNavi.addParallelRoadListener(this);
            mAMapNavi.setUseInnerVoice(true, true);
        } catch (AMapException e) {
            throw new RuntimeException(e);
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        startForeground();
        Log.d("dragonradar", "started service");
        return Service.START_STICKY_COMPATIBILITY;
    }

    private void startForeground() {
        Log.d("dragonradar", "starting service");
        String titleText = getString(R.string.app_name);
        String text = getString(R.string.app_name);

        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, text, NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager nm = getSystemService(NotificationManager.class);
        nm.createNotificationChannel(channel);

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle().bigText("test").setBigContentTitle("test123");
        Intent launchActivityIntent = new Intent(this.getApplicationContext(), DragonRouteActivity.class);

        PendingIntent activityPendingIntent = PendingIntent.getActivity(
                this.getApplicationContext(),
                0,
                launchActivityIntent,
                PendingIntent.FLAG_IMMUTABLE
        );

//        val cancelIntent = Intent(this, ForegroundOnlyWalkingWorkoutService::class.java)
//        cancelIntent.putExtra(EXTRA_CANCEL_WORKOUT_FROM_NOTIFICATION, true)
//
//        val servicePendingIntent = PendingIntent.getService(
//                this,
//                0,
//                cancelIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
//                )

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this.getApplicationContext(), NOTIFICATION_CHANNEL_ID)
                        .setStyle(bigTextStyle)
                        .setContentTitle(titleText)
                        .setContentText(titleText)
                        .setSmallIcon(R.drawable.ic_walk)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setOngoing(true)
                        .addAction(new NotificationCompat.Action(R.drawable.ic_walk, getString(R.string.app_name),
                                activityPendingIntent))
                        .setCategory(NotificationCompat.CATEGORY_WORKOUT)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setOngoing(true);

        Status ongoingActivityStatus = new Status.Builder()
                // Sets the text used across various surfaces.
                .addTemplate(text)
                .build();

        OngoingActivity ongoingActivity =
                new OngoingActivity.Builder(this.getApplicationContext(), NOTIFICATION_ID, notificationBuilder)
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
                        .build();

        ongoingActivity.apply(this.getApplicationContext());

        int type = ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION;
        startForeground(NOTIFICATION_ID, notificationBuilder.build(), type);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAMapNavi != null) {
            mAMapNavi.stopNavi();
            Log.d(DEBUG_TAG, "navigation stopped");
        }
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {
        this.onDestroy();
    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }

    @Override
    public void onGpsSignalWeak(boolean b) {

    }

    @Override
    public void notifyParallelRoad(AMapNaviParallelRoadStatus aMapNaviParallelRoadStatus) {

    }
}
