package rw.qtopie.dragonradar.navi;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.AMapNaviViewShowMode;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.enums.TravelStrategy;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviPoi;
import com.amap.api.navi.view.DirectionView;
import com.amap.api.navi.view.NextTurnTipView;

import rw.qtopie.dragonradar.R;

public class HelloBikeNaviActivity extends BaseNaviActivity {

    NextTurnTipView mNextTurnTipView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navi);
        mAMapNaviView = findViewById(R.id.naviView);
        AMapNaviViewOptions options = mAMapNaviView.getViewOptions();

        options.setLayoutVisible(false);
        mAMapNaviView.setViewOptions(options);

        mNextTurnTipView = findViewById(R.id.mNextTurnTipView);
        mAMapNaviView.setLazyNextTurnTipView(mNextTurnTipView);

        mAMapNaviView.setShowMode(AMapNaviViewShowMode.SHOW_MODE_LOCK_CAR);

        DirectionView mDirectionView =  findViewById(R.id.custom_myDirectionView);
        mAMapNaviView.setLazyDirectionView(mDirectionView);


        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mAMapNaviView.onDestroy();
        if (mAMapNavi != null) {
            mAMapNavi.stopNavi();
        }
    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
        super.onCalculateRouteSuccess(aMapCalcRouteResult);

        mAMapNavi.startNavi(NaviType.GPS);

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

        StringBuffer sb = new StringBuffer();
        sb.append("共" + aMapLaneInfo.frontLane.length + "车道");
        for (int i = 0; i < aMapLaneInfo.frontLane.length; i++) {
            //当前车道可以选择的动作
            int background = aMapLaneInfo.backgroundLane[i];
            //当前用户要执行的动作
            int recommend = aMapLaneInfo.frontLane[i];
            //根据文档中每个动作对应的枚举类型，显示对应的图片
            try {
                sb.append("，第" + (i + 1) + "车道为" + array[background]);
                if (recommend != 255) {
                    sb.append("，当前车道可 " + actions[recommend]);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 车道信息说明：
     * <p>
     * 0xFF, 无对应车道
     * 0,    直行
     * 1,    左转
     * 2,    直行+左转
     * 3,    右转
     * 4,    直行+右转
     * 5,    左掉头
     * 6,    左转+右转
     * 7,    直行+左转+右转
     * 8,    右掉头
     * 9,    直行+左掉头
     * 10,   直行+右掉头
     * 11,   左转+左掉头
     * 12,   右转+右掉头
     * 13,   直行+扩展
     * 14,   左转+左掉头+扩展
     * 15,   保留
     * 16,   直行+左转+左掉头
     * 17,   右转+左掉头
     * 18,   左转+右转+左掉头
     * 19,   直行+右转+左掉头
     * 20,   左转+右掉头
     * 21,   公交车道
     * 22,   空车道
     * 23    可变车道
     */

    String[] array = {
            "直行车道"
            , "左转车道"
            , "左转或直行车道"
            , "右转车道"
            , "右转或直行车道"
            , "左掉头车道"
            , "左转或者右转车道"
            , " 左转或右转或直行车道"
            , "右转掉头车道"
            , "直行或左转掉头车道"
            , "直行或右转掉头车道"
            , "左转或左掉头车道"
            , "右转或右掉头车道"
            , "直行并且车道扩展"
            , "左转+左掉头+扩展"
            , "不可以选择该车道"
            , "直行+左转+左掉头车道"
            , "右转+左掉头"
            , "左转+右转+左掉头"
            , "直行+右转+左掉头"
            , "左转+右掉头"
            , "公交车道"
            , "空车道"
            , "可变车道"
    };

    String[] actions = {
            "直行"
            , "左转"
            , "左转或直行"
            , "右转"
            , "右转或这行"
            , "左掉头"
            , "左转或者右转"
            , " 左转或右转或直行"
            , "右转掉头"
            , "直行或左转掉头"
            , "直行或右转掉头"
            , "左转或左掉头"
            , "右转或右掉头"
            , "直行并且车道扩展"
            , "左转+左掉头+扩展"
            , "不可以选择"
            , "直行+左转+左掉头"
            , "右转+左掉头"
            , "左转+右转+左掉头"
            , "直行+右转+左掉头"
            , "左转+右掉头"
            , "公交车道"
            , "空车道"
            , "可变车道"
    };

    /** ------- 导航基本信息的回调 ----- */
    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        super.onNaviInfoUpdate(naviInfo);
        /**
         * 更新路口转向图标
         */
        if(naviInfo.getIconBitmap() != null){
            mNextTurnTipView.setImageBitmap(naviInfo.getIconBitmap());
        }else{
            mNextTurnTipView.setIconType(naviInfo.getIconType());
        }

        /**
         * 更新下一路口 路名及 距离
         */
//        textNextRoadName.setText(naviInfo.getNextRoadName());//路名
//        textNextRoadDistance.setText(NaviUtil.formatKM(naviInfo.getCurStepRetainDistance()));//距离
    }

    @Override
    public void onInitNaviSuccess() {
        LatLng start = new LatLng(sList.get(0).getLatitude(), sList.get(0).getLongitude());

        LatLng end = new LatLng(eList.get(0).getLatitude(), eList.get(0).getLongitude());

        // 构造起点POI
        NaviPoi fromPoi = new NaviPoi("起点", start, "");
        // 构造终点POI
        NaviPoi toPoi = new NaviPoi("终点", end, "");

        mAMapNavi.calculateRideRoute(fromPoi, toPoi, TravelStrategy.SINGLE);
    }

}
