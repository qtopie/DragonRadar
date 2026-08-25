# DragonRadar 系统与交互架构设计 (System & Interaction Design)

基于高德地图 SDK 的 Wear OS 独立/协同骑行导航系统。

---

## 1. 总体架构与交互流转 (High-Level Interaction Architecture)

```mermaid
graph TD
    subgraph WearOS_UI ["Wear OS 页面流转 (Activity & Views)"]
        Main["MainActivity / MapView<br/>(1. 地图定位与目的地选择)"]
        NaviRoute["DragonRouteActivity<br/>(高德路线规划拉起)"]
        HelloBike["HelloBikeNaviActivity / HUD<br/>(2. 骑行巡航态 & 3. HUD控制面板)"]
        Settings["SettingsActivity [待实现]<br/>(4. 蓝牙配对与账号绑定)"]
        SearchPOI["DestinationSearchActivity [待实现]<br/>(目的地搜索与常用地点)"]
    end

    subgraph Background_Service ["后台保活与数据通道"]
        NaviService["DragonRadarNaviService<br/>(前台服务/GPS/震动提醒)"]
        AMapSDK["高德地图 & 导航 SDK<br/>(AMapNavi / AMapLocation)"]
        PhoneSync["DataLayer / 蓝牙通信 [待实现]<br/>(手机端目的地推送与状态同步)"]
    end

    Main -->|发起路线规划| NaviRoute
    NaviRoute -->|启动骑行导航| HelloBike
    Main -.->|打开设置| Settings
    Main -.->|搜索终点| SearchPOI
    HelloBike -.->|打开设置/账号绑定| Settings
    HelloBike -->|绑定前台定位与播报| NaviService
    NaviService <--> AMapSDK
    Settings <--> PhoneSync
```

---

## 2. 核心页面设计与 Activity 状态矩阵 (Activity Implementation Status)

对照 `docs/dragon-radar-design.png` 设计图：

| 交互界面序号 | 界面名称与职责 | 对应 Activity / 类 | 状态 | 待完成工作 / 规划 |
| :--- | :--- | :--- | :---: | :--- |
| **页面 1** | **地图定位与目的地设定**<br/>- 起点（我的位置）与终点输入<br/>- 路况切换、地图缩放(+/-)、收藏入口 | `MainActivity.kt`<br/>`DragonRouteActivity.kt` | ⚠️ 部分实现 | 补充自定义选点/输入框，接入常用地点快捷选择与手机推送接收。 |
| **页面 2 & 3** | **骑行导航与 HUD 指引控制**<br/>- 3D 轨迹巡航、电子罗盘<br/>- HUD 转向卡片、剩余距离/预计用时、车速<br/>- 底部控制面板（停止、重设路线、全览、设置） | `HelloBikeNaviActivity.kt`<br/>`BaseNaviActivity.kt`<br/>`DragonRadarNaviService.kt` | ⚠️ 骨架就绪 | 解除回调注释，联调转向与测距数据刷新，实现“停止/重设路线/全览”手势与按钮响应。 |
| **页面 4** | **设置 / 账号绑定 / 蓝牙配对**<br/>- 蓝牙配对开关与设备扫描<br/>- 手机端账号绑定与数据同步<br/>- 上滑返回手势 | `SettingsActivity.kt` / `DeviceBindActivity.kt` | ❌ 待实现 | 新建独立 Activity，集成 Wearable Data Layer / Bluetooth 状态管理及上滑返回组件。 |
| **辅助页面** | **目的地搜索 / 常用地点选择**<br/>- 关键字搜索 POI<br/>- 家/公司等常用地点快捷列表 | `DestinationSearchActivity.kt` | ❌ 待实现 | 新建搜索 Activity，对接高德搜索 SDK（AMapSearch）及本地常用地点存储。 |

---

## 3. 后台服务与传感器交互 (Background & Telemetry Flow)

1. **DragonRadarNaviService**：作为 Foreground Service，持有 Notification 与 WakeLock，持续监听定位变化及导航转向事件。
2. **触觉反馈 (Haptic Feedback)**：通过 `NaviInfoCallback` 驱动 `VibratorManager`，在接近转弯路口时发出震动脉冲提醒。
3. **低功耗与屏幕常亮**：巡航模式下适度保持屏幕亮起，并在 Wear OS 环境下优化电池开销。
