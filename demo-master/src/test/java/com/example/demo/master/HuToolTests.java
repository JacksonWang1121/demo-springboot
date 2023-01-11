package com.example.demo.master;

import cn.hutool.Hutool;
import cn.hutool.core.util.IdcardUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

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

//@SpringBootTest
@RunWith(SpringRunner.class)
public class HuToolTests {

    @Test
    public void testRegAddr() {
        String idcard = "140728991116001";
        System.out.println(IdcardUtil.getProvinceCodeByIdCard(idcard));
        System.out.println(IdcardUtil.getCityCodeByIdCard(idcard));
        System.out.println(IdcardUtil.getDistrictCodeByIdCard(idcard));
    }

    @Test
    public void testPrintAllUtils() {
        Hutool.printAllUtils();
    }

    public static void main(String[] args) {
        Hutool.printAllUtils();

    }

}
