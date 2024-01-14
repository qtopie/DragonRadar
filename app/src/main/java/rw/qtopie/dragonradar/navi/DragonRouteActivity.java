package rw.qtopie.dragonradar.navi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.navi.AmapRouteActivity;

public class DragonRouteActivity extends AmapRouteActivity {
    public static final String DEBUG_TAG = "dragon-radar-swipe";

    Intent bikeIntent;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        bikeIntent = new Intent(this.getApplicationContext(), HelloBikeNaviActivity.class);
    }

    @Override
    public void onBackPressed() {
        Log.d(DEBUG_TAG, "back button pressed");
        startActivity(bikeIntent);
    }
}
