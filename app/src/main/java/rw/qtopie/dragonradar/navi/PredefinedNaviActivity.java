package rw.qtopie.dragonradar.navi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviTheme;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;

public class PredefinedNaviActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置隐私权政策
        Context context = this.getApplicationContext();
        MapsInitializer.updatePrivacyShow(context, true, true);
        MapsInitializer.updatePrivacyAgree(context, true);


//终点

        LatLng s1 = new LatLng(40.084894,116.603039);
        LatLng t1 = new LatLng(39.825934,116.342972);

        Poi start = new Poi("start", s1, "");
        Poi end = new Poi("end", t1, "");

// 组件参数配置
        AmapNaviParams params = new AmapNaviParams(start, null, end, AmapNaviType.DRIVER, AmapPageType.NAVI);
        params.setShowVoiceSetings(false);
        params.setShowExitNaviDialog(false);
        params.setUseInnerVoice(false);
        params.setTheme(AmapNaviTheme.BLACK);
        params.setShowCrossImage(false);
        params.setShowRouteStrategyPreferenceView(false);
        params.setDrawBackUpOverlay(false);

// 启动组件
        AmapNaviPage.getInstance()
                .showRouteActivity(getApplicationContext(), params, null);
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
