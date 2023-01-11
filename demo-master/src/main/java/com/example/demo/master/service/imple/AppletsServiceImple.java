package com.example.demo.master.service.imple;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.master.constance.PublicConstance;
import com.example.demo.master.entity.SConfig;
import com.example.demo.master.mapper.SConfigMapper;
import com.example.demo.master.service.IAppletsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("appletsService")
public class AppletsServiceImple implements IAppletsService {

    private static final Logger LOGGER = Logger.getLogger(AppletsServiceImple.class);

    @Resource
    private SConfigMapper sConfigMapper;

    public SConfigMapper getsConfigMapper() {
        return sConfigMapper;
    }

    @Override
    public void sendMsg() {
        try {
            //获取Token调用凭证
            String accessToken = getAccessToken();
            if(!StringUtils.hasText(accessToken)) {
                throw new Exception("[sendMsg]  获取accessToken失败");
            }

            Map<String, Map<String, String>> dataMap = new HashMap<String, Map<String,String>>();

            Map<String, String> m = new HashMap<String, String>();
            m.put("value", "123456");  //申请编号
            dataMap.put("character_string1", m);

            m = new HashMap<String, String>();
            m.put("value", "123456");  //申请编号
            dataMap.put("character_string2", m);

            String urlAccess = PublicConstance.WX_SEND_MSG_URL + PublicConstance.WX_ACCESS_TOKEN;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", accessToken);  //接口调用凭证
            jsonObject.put("touser", "");  //接收者（用户）的 openid
            jsonObject.put("template_id", "");  //所需下发的订阅模板id
            //page 和 miniprogram 同时不填，无跳转；page 和 miniprogram 同时填写，优先跳转小程序；
            jsonObject.put("page", "");  //跳转网页时填写
            jsonObject.put("miniprogram", "");  //跳转小程序时填写，格式如{ "appid": "pagepath": { "value": any } }
            jsonObject.put("data", "");  //data内容
            LOGGER.info("[sendMsg]  请求微信发送消息通知的接口参数:" + jsonObject.toJSONString());

//            String result = HttpUtil.post(urlAccess, jsonObject.toJSONString());
//            Map respMap = (Map) JSONObject.parse(result);
//            LOGGER.info("[sendMsg]  请求微信发送消息通知:" + respMap);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    private String getAccessToken() {
        String accessToken = null;
        try {
            //查询AccessToken是否有效
            SConfig appletsExpiresIn = sConfigMapper.selectByCfgName("applets_expires_in");
            //获取当前时间
            long betweenMs = DateUtil.betweenMs(DateUtil.date().toJdkDate(), DateUtil.parse(appletsExpiresIn.getLastChgDt(), DateUtil.newSimpleFormat(DatePattern.NORM_DATETIME_PATTERN)));
            //在有效时间内
            if (Long.parseLong(appletsExpiresIn.getCfgValue()) * 1000 > betweenMs) {
                SConfig appletsAccessToken = sConfigMapper.selectByCfgName("applets_access_token");
                accessToken = appletsAccessToken.getCfgValue();
                return accessToken;
            }

            //accessToken已过期
            String apiUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + PublicConstance.WX_GRANT_TYPE +"&appid=" + PublicConstance.WX_APP_ID + "&secret=" + PublicConstance.WX_APP_SECRET;
            int index = 1;  //最多重复执行3次
            do {
                try {
                    //查询TOKEN
//                    String response = "{\"access_token\":\"61_sQcLYLKQkM7lrd2wSiU_s3wvZj01XuZiq7RVVL9mSC7XGk_LSJvBIGCteD7LzSWmKCvNhZRT5v85AuzGfW6MYQ1R9bFOM1Wq3n7iqFOVndkB8NZ8ZPA-ks4JYiUp6WtJFSv3G92f_bO27GHWRTAgAHAMOV\",\"expires_in\":7200}";
                    String response = HttpUtil.get(apiUrl);

                    LOGGER.info("[getAccessToken]  第" + index + "次执行  response: " + response);
                    JSONObject jsonObject = JSONObject.parseObject(response);
                    accessToken = jsonObject.containsKey("access_token") ? String.valueOf(jsonObject.get("access_token")) : null;

                    //保存access Token
                    if(StringUtils.hasText(accessToken)) {
                        //获取当前时间
                        String lastChgDt = DateUtil.date().toStringDefaultTimeZone();

                        SConfig appletsAccessToken = new SConfig();
                        appletsAccessToken.setCfgId(100);
                        appletsAccessToken.setCfgValue(accessToken);
                        appletsAccessToken.setLastChgDt(lastChgDt);
                        sConfigMapper.updateByPrimaryKeySelective(appletsAccessToken);

                        appletsExpiresIn = new SConfig();
                        appletsExpiresIn.setCfgId(101);
                        appletsExpiresIn.setCfgValue(String.valueOf(jsonObject.get("expires_in")));
                        appletsExpiresIn.setLastChgDt(lastChgDt);
                        sConfigMapper.updateByPrimaryKeySelective(appletsExpiresIn);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //若获取到TOKEN，则结束循环
                if(StringUtils.hasText(accessToken)) {
                    break;
                }
                //执行下一次循环
                index++;
            } while (index <= 3);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
        return accessToken;
    }
}
