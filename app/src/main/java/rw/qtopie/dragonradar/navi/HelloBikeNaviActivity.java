package rw.qtopie.dragonradar.navi;

import static rw.qtopie.dragonradar.navi.DragonRouteActivity.DEBUG_TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;

import rw.qtopie.dragonradar.R;

public class HelloBikeNaviActivity extends BaseNaviActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navi);
        mAMapNaviView = findViewById(R.id.naviView);

        // 设置layout visible=false加上自己的控件就可以定制UI
        AMapNaviViewOptions options = mAMapNaviView.getViewOptions();
        options.setLayoutVisible(false);
        options.setAutoDrawRoute(true);
        options.setAfterRouteAutoGray(true);
        options.setNaviArrowVisible(true);
        options.setLaneInfoShow(true);
        options.setAutoLockCar(true);
        options.setSensorEnable(true);
        mAMapNaviView.setViewOptions(options);

        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setNaviMode(AMapNaviView.CAR_UP_MODE);
        mAMapNaviView.setTrafficLightsVisible(true);
        mAMapNaviView.setAMapNaviViewListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d(DEBUG_TAG,"Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d(DEBUG_TAG,"Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d(DEBUG_TAG,"Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d(DEBUG_TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d(DEBUG_TAG,"Movement occurred outside bounds of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mAMapNaviView.onDestroy();
    }

}
