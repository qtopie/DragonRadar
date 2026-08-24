package rw.qtopie.dragonradar

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import com.amap.api.maps.MapsInitializer
import com.amap.api.navi.AmapNaviPage
import com.amap.api.navi.AmapNaviParams
import com.amap.api.navi.AmapNaviType
import com.amap.api.navi.AmapPageType
import rw.qtopie.dragonradar.navi.DragonRouteActivity

class MainActivity : Activity() {

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置隐私权政策
        val context: Context = this.applicationContext
        MapsInitializer.updatePrivacyShow(context, true, true)
        MapsInitializer.updatePrivacyAgree(context, true)

//        // 新的测试代码
//        val bikeIntent = Intent(context, HelloBikeNaviActivity::class.java)
//        startActivity(bikeIntent)

        // 组件参数配置
        val params = AmapNaviParams(null, null, null, AmapNaviType.MOTORCYCLE, AmapPageType.ROUTE)
        params.setShowExitNaviDialog(false)
        params.setShowRouteStrategyPreferenceView(false)
        params.setTrafficEnabled(true)
        params.setCarDirectionMode(context, 2)
        // 禁止播报
        params.setUseInnerVoice(false)
        params.setScaleAutoChangeEnable(context, true)

        // 实时导航
        params.setNaviMode(1)
        params.setNeedDestroyDriveManagerInstanceWhenNaviExit(true)

//        val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
//        val callback: INaviInfoCallback = NaviInfoCallback(this, vibratorManager.getDefaultVibrator())

        // 启动组件
        AmapNaviPage.getInstance()
            .showRouteActivity(context, params, null, DragonRouteActivity::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}
