package rw.qtopie.dragonradar.navi;

import static rw.qtopie.dragonradar.navi.DragonRouteActivity.DEBUG_TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.view.NextTurnTipView;

import rw.qtopie.dragonradar.R;

public class HelloBikeNaviActivity extends BaseNaviActivity {

    private TextView textNextRoadDistance;//下个路口距离
    private NextTurnTipView nextTurnTipView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nextTurnTipView = findViewById(R.id.nextTurnView);
        mAMapNaviView.setLazyNextTurnTipView(nextTurnTipView);

        textNextRoadDistance = findViewById(R.id.text_next_road_distance);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(DEBUG_TAG, "Movement occurred outside bounds of current screen element");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }


//    @Override
//    public void onInitNaviSuccess() {
////        boolean ok = mAMapNavi.calculateRideRoute(p1);
////        Log.d(DEBUG_TAG, "inited navi: " + ok);
//    }

//    @Override
//    public void onCalculateRouteSuccess(AMapCalcRouteResult var1) {
//        super.onCalculateRouteSuccess(var1);
//        mAMapNavi.startNavi(NaviType.GPS);
//    }

    @Override
    public void onBackPressed() {
        Log.d(DEBUG_TAG, "back button pressed");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mAMapNaviView.onDestroy();
    }

//    @Override
//    public void onNaviInfoUpdate(NaviInfo naviInfo) {
//        super.onNaviInfoUpdate(naviInfo);
//
//        /**
//         * 更新路口转向图标
//         */
//        if (naviInfo.getIconBitmap() != null) {
//            nextTurnTipView.setImageBitmap(naviInfo.getIconBitmap());
//        } else {
//            nextTurnTipView.setIconType(naviInfo.getIconType());
//        }
//
//        // 更新下一路口 路名及 距离
//        textNextRoadDistance.setText(formatKM(naviInfo.getCurStepRetainDistance()));
//    }
//
//    public static String formatKM(int d) {
//        if (d == 0) {
//            return "0m";
//        } else if (d < 100) {
//            return d + "m";
//        } else if (d < 1000) {
//            return d + "m";
//        } else if (d < 10000) {
//            return (d / 10) * 10 / 1000.0D + "km";
//        } else if (d < 100000) {
//            return (d / 100) * 100 / 1000.0D + "km";
//        }
//        return (d / 1000) + "km";
//    }

}
