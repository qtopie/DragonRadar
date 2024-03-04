package rw.qtopie.dragonradar.navi;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.amap.api.navi.AmapRouteActivity;

public class DragonRouteActivity extends AmapRouteActivity {
    public static final String DEBUG_TAG = "dragon-radar-swipe";

    private volatile boolean naviStarted;
    private Intent bikeIntent;
    private Intent naviService;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = this.getApplicationContext();
        bikeIntent = new Intent(context, HelloBikeNaviActivity.class);
        naviService = new Intent(context, DragonRadarNaviService.class);
        naviStarted = true;
    }

    @Override
    public void onBackPressed() {
        Log.d(DEBUG_TAG, "back button pressed");

        if (naviStarted) {
            startForegroundService(naviService);
            startActivity(bikeIntent);
        }
    }

    @Override
    public void finish() {
        super.finish();
        Log.d(DEBUG_TAG, "route activity finished");
        if (naviService != null) {
            stopService(naviService);
        }

        naviStarted = false;
    }
}
