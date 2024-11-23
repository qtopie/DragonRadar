package rw.qtopie.dragonradar.navi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amap.api.navi.AmapRouteActivity;

public class DragonRouteActivity extends AmapRouteActivity {
    public static final String DEBUG_TAG = "dragon-radar-swipe";

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = this.getApplicationContext();

        Intent naviService = new Intent(context, DragonRadarNaviService.class);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
        } else {
            startForegroundService(naviService);
        }

        Intent bikeIntent = new Intent(context, HelloBikeNaviActivity.class);
        startActivity(bikeIntent);
    }


//    @Override
//    public void finish() {
//        super.finish();
//        Log.d(DEBUG_TAG, "route activity finished");
//        if (naviService != null) {
//            stopService(naviService);
//        }
//
//        naviStarted = false;
//    }
}
