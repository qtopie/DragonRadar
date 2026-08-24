package rw.qtopie.dragonradar.navi

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amap.api.navi.AmapRouteActivity

class DragonRouteActivity : AmapRouteActivity() {
    companion object {
        const val DEBUG_TAG = "dragon-radar-swipe"
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        val context = applicationContext

        val naviService = Intent(context, DragonRadarNaviService::class.java)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
        } else {
            startForegroundService(naviService)
        }

        val bikeIntent = Intent(context, HelloBikeNaviActivity::class.java)
        startActivity(bikeIntent)
    }

//    override fun finish() {
//        super.finish()
//        Log.d(DEBUG_TAG, "route activity finished")
//        if (naviService != null) {
//            stopService(naviService)
//        }
//
//        naviStarted = false
//    }
}
