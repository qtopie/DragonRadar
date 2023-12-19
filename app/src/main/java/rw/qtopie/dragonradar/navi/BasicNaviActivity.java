package rw.qtopie.dragonradar.navi;

import android.os.Bundle;

import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.view.NextTurnTipView;

import rw.qtopie.dragonradar.R;

public class BasicNaviActivity extends BaseNaviActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navi);
        mAMapNaviView = findViewById(R.id.naviView);
        AMapNaviViewOptions options = mAMapNaviView.getViewOptions();

        // 设置layout visible=false加上自己的控件就可以定制UI
        options.setLayoutVisible(false);
        mAMapNaviView.setViewOptions(options);

        NextTurnTipView mNextTurnTipView = findViewById(R.id.mNextTurnTipView);
        mAMapNaviView.setLazyNextTurnTipView(mNextTurnTipView);

        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
    }
}
