package com.example.demo.master.service.imple;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWTUtil;
import com.example.demo.master.constance.PublicConstance;
import com.example.demo.master.entity.SSaler;
import com.example.demo.master.mapper.CommonMapper;
import com.example.demo.master.mapper.SSalerMapper;
import com.example.demo.master.service.ILoginService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <h2>简述</h2>
 * 	   <ol>无</ol>
 *   <h2>功能描述</h2>
 * 	   <ol>无</ol>
 *   <h2>修改历史</h2>
 *     <ol>无</ol>
 * </p>
 *
 * @author wangjisen
 * @version 1.0
 * @date 2022/12/22 16:52
 */

@Service("loginService")
public class LoginServiceImple implements ILoginService {

    @Resource
    private CommonMapper commonMapper;

    @Resource
    private SSalerMapper sSalerMapper;

    /**
     * 新增登录用户
     * @param salerNo  登录用户名
     * @param salerName  用户姓名
     * @param password  登录密码
     * @param salerTel  联系方式
     * @return  保存结果
     */
    @Override
    public Map<String, String> saveLoginData(String salerNo, String salerName, String password, String salerTel) {
        SSaler sSaler = new SSaler();
        sSaler.setSalerId(commonMapper.getNextValue(PublicConstance.LOGIN_SEQ));  //登录用户ID
        sSaler.setSalerNo(salerNo);  //登录用户名
        sSaler.setSalerName(salerName);  //用户姓名
        sSaler.setPassword(SecureUtil.md5(salerNo + password));  //登录密码
        sSaler.setSalerTel(salerTel);  //联系方式
        sSaler.setLoginStatus(PublicConstance.NO);  //登录状态，默认为登出
        sSaler.setSalerStatus(PublicConstance.STATUS_VALID);  //用户状态，默认为生效
        String curDate = DateUtil.date().toStringDefaultTimeZone();//获取当前时间，格式：yyyy-MM-dd HH:mm:ss
        sSaler.setCrtDt(curDate);  //账号创建时间
        sSaler.setLastChgDt(curDate);  //最后修改时间

        int count = sSalerMapper.insertSelective(sSaler);

        Map<String, String> resultMap = new HashMap<>();
        if(count != 1) {
            resultMap.put("code", String.valueOf(HttpStatus.BAD_REQUEST.value()));
            resultMap.put("message", "保存失败");
            return resultMap;
        }

        resultMap.put("code", String.valueOf(HttpStatus.OK.value()));
        resultMap.put("message", "保存成功");
        return resultMap;
    }

    /**
     * 登录验证
     * @param username  登录用户名
     * @param password  登录密码
     * @return  登录验证结果
     */
    @Override
    public Map<String, String> loginOnAuto(String username, String password) {
        Map<String, String> resultMap = new HashMap<>();
        //账号密码验证
        String md5Str = SecureUtil.md5(username + password);
        int count = sSalerMapper.loginOnAuto(username, md5Str);
        if(count != 1) {
            resultMap.put("code", String.valueOf(HttpStatus.BAD_REQUEST.value()));
            resultMap.put("message", "用户名和密码不一致，不允许登录");
            return resultMap;
        }

        //验证成功后发送token
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("password", md5Str);
        payload.put("authToken", SecureUtil.md5(md5Str + "_" + DateUtil.date().toStringDefaultTimeZone()));  //二重验证
        String token = JWTUtil.createToken(payload, md5Str.getBytes());
        if(token == null) {
            resultMap.put("code", String.valueOf(HttpStatus.BAD_REQUEST.value()));
            resultMap.put("message", "认证失败");
            return resultMap;
        }

        resultMap.put("code", String.valueOf(HttpStatus.OK.value()));
        resultMap.put("message", "认证成功");
        resultMap.put("token", token);
        return resultMap;
    }
}
