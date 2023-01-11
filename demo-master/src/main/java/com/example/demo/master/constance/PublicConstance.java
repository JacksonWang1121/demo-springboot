package com.example.demo.master.constance;

public class PublicConstance {

    public static final String ERROR_CODE = "errorCode";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String RESPONSE_CODE_SUCCESS = "0000";  //返回结果码：处理成功
    public static final String RESPONSE_CODE_FAILED = "9999";  //返回结果码：处理异常

    public static final String STATUS_VALID = "A";  //状态：有效
    public static final String STATUS_INVALID = "I";  //状态：失效
    public static final String STATUS_WAIT = "W";  //状态：待生效

    public static final String YES = "Y";  //状态：是
    public static final String NO = "N";  //状态：否

    public static final String CHARSET_DEFAULT = "UTF-8";

    public static final String WX_APP_ID = "wx864bc10fa0a1a2f3";  //开发者ID
    public static final String WX_APP_SECRET = "5c0fd09d3f23205ecb8e80ce621a1de8";  //开发者密钥
    public static final String WX_ACCESS_TOKEN = "h0uEKUppMWzowMKOnO9OOQWeQIQAmkCKpQpeaVZl1so";  //访问令牌

    public static final String WX_GRANT_TYPE = "client_credential";  //授权类型

    public static final String WX_SEND_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/bizsend?access_token=";  //消息通知URL
    public static final String WX_SEND_MSG_VERSION = "developer";  //消息通知，跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版

    public static final String[] PROVINCE = {"北京", "天津", "河北", "山西", "内蒙古", "辽宁", "吉林", "黑龙江", "上海", "江苏",
                                            "浙江", "安徽", "福建", "江西", "山东", "河南", "湖北", "湖南", "广东", "广西",
                                            "海南", "重庆", "四川", "贵州", "云南", "西藏", "陕西", "甘肃", "青海", "宁夏",
                                            "新疆", "台湾", "香港", "澳门"};

    public static final String AREA_TYPE_PROVINCE = "province";  //区域类型：省
    public static final String AREA_TYPE_CITY = "city";  //区域类型：市
    public static final String AREA_TYPE_AREA = "area";  //区域类型：区

    public static final String TRACE_INFO_SEQ = "trace_info_seq";  //序列：交易流水号
    public static final String LOGIN_SEQ = "login_seq";  //序列：登录用户ID

}
