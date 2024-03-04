package rw.qtopie.dragonradar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.VibratorManager;

import androidx.annotation.Nullable;

import com.amap.api.maps.MapsInitializer;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.navi.INaviInfoCallback;

import rw.qtopie.dragonradar.navi.DragonRouteActivity;
import rw.qtopie.dragonradar.navi.NaviInfoCallback;

public class MainActivity extends Activity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置隐私权政策
        Context context = this.getApplicationContext();
        MapsInitializer.updatePrivacyShow(context, true, true);
        MapsInitializer.updatePrivacyAgree(context, true);


        // 组件参数配置
        AmapNaviParams params = new AmapNaviParams(null, null, null, AmapNaviType.RIDE, AmapPageType.ROUTE);
        params.setShowVoiceSetings(false);
        params.setShowExitNaviDialog(false);
        params.setShowRouteStrategyPreferenceView(false);
        params.setTrafficEnabled(true);
        params.setCarDirectionMode(context, 2);
        params.setScaleAutoChangeEnable(context, true);

        // 实时导航
        params.setNaviMode(1);
        params.setNeedDestroyDriveManagerInstanceWhenNaviExit(true);

        VibratorManager vibratorManager = (VibratorManager) getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
        INaviInfoCallback callback = new NaviInfoCallback(this, vibratorManager.getDefaultVibrator());

        // 启动组件
        AmapNaviPage.getInstance()
                .showRouteActivity(context, params, callback, DragonRouteActivity.class);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
