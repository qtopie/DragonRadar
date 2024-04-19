package rw.qtopie.dragonradar.navi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amap.api.navi.AmapRouteActivity;

import rw.qtopie.dragonradar.MainActivity;

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
        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            } else {
                startForegroundService(naviService);
            }
        }

        naviStarted = true;
    }


    @Override
    public void onBackPressed() {
        Log.d(DEBUG_TAG, "back button pressed");

        if (naviStarted) {
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
