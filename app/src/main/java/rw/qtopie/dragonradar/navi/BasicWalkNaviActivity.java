package rw.qtopie.dragonradar.navi;

import android.os.Bundle;

import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.view.NextTurnTipView;

import rw.qtopie.dragonradar.R;

public class BasicWalkNaviActivity extends BaseNaviActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navi);
        mAMapNaviView = findViewById(R.id.naviView);
        AMapNaviViewOptions options = mAMapNaviView.getViewOptions();
        options.setSecondActionVisible(false);
        options.setAutoDisplayOverview(false);
        options.setModeCrossDisplayShow(false);

        options.setLayoutVisible(false);
        mAMapNaviView.setViewOptions(options);

        NextTurnTipView mNextTurnTipView = findViewById(R.id.mNextTurnTipView);
        mAMapNaviView.setLazyNextTurnTipView(mNextTurnTipView);

        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
    }


//    @Override
//    public void onInitNaviSuccess() {
////        mAMapNavi.calculateWalkRoute(sList.get(0), eList.get(0));
//    }
//
//    @Override
//    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
//
////        mAMapNavi.startNavi(NaviType.GPS);
//    }
}