package com.example.demospringboot;

import cn.hutool.Hutool;
import cn.hutool.core.util.IdcardUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
 * @date 2022/11/16 15:01
 */

@SpringBootTest
public class HuToolTests {

    @Test
    public void testRegAddr() {
        String idcard = "140728991116001";
        System.out.println(IdcardUtil.getProvinceCodeByIdCard(idcard));
        System.out.println(IdcardUtil.getCityCodeByIdCard(idcard));
        System.out.println(IdcardUtil.getDistrictCodeByIdCard(idcard));
    }

    public static void main(String[] args) {
//        Hutool.printAllUtils();
        String idcard = "140728991116001";
        System.out.println(IdcardUtil.getProvinceCodeByIdCard(idcard));
        System.out.println(IdcardUtil.getCityCodeByIdCard(idcard));
        System.out.println(IdcardUtil.getDistrictCodeByIdCard(idcard));
    }

}
