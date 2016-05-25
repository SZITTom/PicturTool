package com.jude.beam.event;

/**
 * Created by SZITTom on 2016/5/9.
 */
public enum EnumEventTag {
    /** 退出app事件 */
    EXIT_APP,

    /** 登录成功事件 */
    LOGIN_SUCCESS,

    /** 隐藏事件 */
    CLOSE_ADD_DELETE,

    /** 退出登录事件 */
    LOGOUT,

    /** 订单支付成功 */
    PAY_ORDER_SUCCESS,

    /** 刷新订单列表 */
    REFRESH_ORDER_LIST,

    /** 城市改变事件 */
    CITY_CHANGE,

    /** 定位成功事件 */
    LOCATION_SUCCESS,

    /** 临时登录状态 */
    TEMP_LOGIN,

    /** 未登录状态 */
    UN_LOGIN,

    /** 启动二维码扫描 */
    START_SCAN_QRCODE,

    /** 上传头像成功 */
    UPLOAD_USER_HEAD_SUCCESS,

    /** 动态详情页面关闭 */
    DYNAMIC_DETAIL_CLOSED;

    public static EnumEventTag valueOf(int index) {
        if (index >= 0 && index < values().length) {
            return values()[index];
        } else {
            return null;
        }
    }
}
