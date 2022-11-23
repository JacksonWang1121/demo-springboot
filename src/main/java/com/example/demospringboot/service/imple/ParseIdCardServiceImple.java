package com.example.demospringboot.service.imple;

import com.example.demospringboot.constance.PublicConstance;
import com.example.demospringboot.entity.SArea;
import com.example.demospringboot.mapper.SAreaMapper;
import com.example.demospringboot.service.IParseIdCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <h2>简述</h2>
 * 	   <ol>身份证解析服务实现</ol>
 *   <h2>功能描述</h2>
 * 	   <ol>无</ol>
 *   <h2>修改历史</h2>
 *     <ol>无</ol>
 * </p>
 *
 * @author wangjisen
 * @version 1.0
 * @date 2022/11/18 14:20
 */

@Service("parseIdCardService")
public class ParseIdCardServiceImple implements IParseIdCardService {

    //logback日志
    private static final Logger LOGGER_LOGBACK = LoggerFactory.getLogger(ParseIdCardServiceImple.class);

    @Resource
    private SAreaMapper sAreaMapper;

    @Override
    public Map<String, String> parseIdCard(String applCode, String regAddr) throws Exception {
        LOGGER_LOGBACK.info("[ParseIdCardComponent解析OCR身份证信息]申请编号【" + applCode + "】开始执行>>>>>>>>>>>>>>>>>");
        String errorCode = "ERR2211171757";
        String errorMessage = "身份证解析失败";
        Map<String, String> regAddrMap = new HashMap<String , String>();
        try {
            if(StringUtils.hasText(regAddr)) {
                errorCode = PublicConstance.RESPONSE_CODE_FAILED;
                errorMessage = "户籍地址不能为空";
                return regAddrMap;
            }

            /*
             * OCR地址串截取规则：
             * 将“地址串”按“省”“市”“区”“详细地址”分别截取。
             * 省级区划结尾包含以下情况：省、市、自治区、行政区；
             * 市级区划结尾包含以下情况：市、自治州、盟、地区、行政单位（对于四大直辖市需特殊处理，同省级匹配落库）；
             * 区级区划结尾包含以下情况：区、县、旗、市；
             * 对于未能正常匹配的省市区置为空，并将未匹配及剩余部分展示在“详细地址”栏位
             */
            String regProvince = "";  //户籍省
            String regCity = "";  //户籍市
            String regArea = "";  //户籍区
            //请求参数集合
            SArea sAreaParam = new SArea();
            //查询结果集合
            SArea sAreaResult = null;

            //解析省份
            for (int i = 0; i < PublicConstance.PROVINCE.length; i++) {
                //模糊匹配
                if(regAddr.startsWith(PublicConstance.PROVINCE[i])) {
                    sAreaParam.setAreaName(PublicConstance.PROVINCE[i] + "%");  //户籍省
                    sAreaParam.setAreaType(PublicConstance.AREA_TYPE_PROVINCE);  //区域类型：省
                    sAreaParam.setAreaSts(PublicConstance.STATUS_VALID);  //生效
                    //查询户籍省
                    sAreaResult = sAreaMapper.selectBySArea(sAreaParam);
                    if(sAreaResult != null) {
                        regProvince = sAreaResult.getAreaCode();  //省份代码
                    }
                    //截取省份（不包含）后面的户籍地址，注意：判断的顺序也很重要！！！
                    if(regAddr.startsWith("北京市") || regAddr.startsWith("天津市") || regAddr.startsWith("上海市") || regAddr.startsWith("重庆市")) {
                        //仅限直辖市
                        regAddr = regAddr.substring(regAddr.indexOf("市") + "市".length());
                    } else {
                        if(regAddr.contains("自治区")) {
                            regAddr = regAddr.substring(regAddr.indexOf("自治区") + "自治区".length());
                        } else if(regAddr.contains("行政区")) {
                            regAddr = regAddr.substring(regAddr.indexOf("行政区") + "行政区".length());
                        } else if(regAddr.contains("省")) {
                            regAddr = regAddr.substring(regAddr.indexOf("省") + "省".length());
                        } else {
                            regAddr = regAddr.substring(PublicConstance.PROVINCE[i].length());
                        }
                    }
                    break;
                }
            }

            //解析城市，截取城市名称，可能为空，注意：判断的顺序也很重要！！！
            String regCityTemp = "";
            String regAreaTemp = "";  //县级市需要特殊处理
            boolean regCitySub = false;  //是否直接截取的前两位，代表城市名称
            if(regAddr.contains("行政单位")) {
                regCityTemp = regAddr.substring(0, regAddr.indexOf("行政单位") + "行政单位".length());
                regAddr = regAddr.substring(regAddr.indexOf("行政单位") + "行政单位".length());
            } else if(regAddr.contains("自治州")) {
                regCityTemp = regAddr.substring(0, regAddr.indexOf("自治州") + "自治州".length());
                regAddr = regAddr.substring(regAddr.indexOf("自治州") + "自治州".length());
            } else if(regAddr.contains("盟")) {
                regCityTemp = regAddr.substring(0, regAddr.indexOf("盟") + "盟".length());
                regAddr = regAddr.substring(regAddr.indexOf("盟") + "盟".length());
            } else if(regAddr.contains("地区")) {
                regCityTemp = regAddr.substring(0, regAddr.indexOf("地区") + "地区".length());
                regAddr = regAddr.substring(regAddr.indexOf("地区") + "地区".length());
            } else if(regAddr.contains("市")) {
                regCityTemp = regAddr.substring(0, regAddr.indexOf("市") + "市".length());
                regAreaTemp = regCityTemp;
                regAddr = regAddr.substring(regAddr.indexOf("市") + "市".length());
            } else {
                //没有获取到城市名称，直接截取前两位
                regCityTemp = regAddr.substring(0, 2);
                regCitySub = true;
            }
            //请求参数集合
            sAreaParam.clear();
            sAreaParam.setAreaName(regCityTemp + "%");  //户籍市
            sAreaParam.setAreaType(PublicConstance.AREA_TYPE_CITY);  //区域类型：市
            sAreaParam.setAreaSts(PublicConstance.STATUS_VALID);  //生效
            //户籍省不为空
            if(StringUtils.hasText(regProvince)) {
                sAreaParam.setAreaParentCode(regProvince);
            }
            //查询户籍市
            sAreaResult = sAreaMapper.selectBySArea(sAreaParam);
            if(sAreaResult != null) {
                regCity = sAreaResult.getAreaCode();  //城市代码
                //直接截取的前两位
                if(regCitySub) {
                    //若户籍地址是以查询出的城市名称为开头，则截取该城市名称（不包含）后面的户籍地址
                    if(regAddr.startsWith(sAreaResult.getAreaName())) {
                        regAddr = regAddr.substring(regAddr.indexOf(sAreaResult.getAreaName()) + sAreaResult.getAreaName().length());
                    }
                }
                if(StringUtils.hasText(regProvince)) {
                    regProvince = sAreaResult.getAreaParentCode();  //上级区域代码（省份）
                }
            } else {
                //县级市查询
                if(StringUtils.hasText(regAreaTemp)) {
                    //请求参数集合
                    sAreaParam.clear();
                    sAreaParam.setAreaName(regAreaTemp + "%");  //户籍区
                    sAreaParam.setAreaType(PublicConstance.AREA_TYPE_AREA);  //区域类型：区
                    sAreaParam.setAreaSts(PublicConstance.STATUS_VALID);  //生效
                    //查询户籍区
                    sAreaResult = sAreaMapper.selectBySArea(sAreaParam);
                    if(sAreaResult != null) {
                        regArea = sAreaResult.getAreaCode();  //县区代码
                        regCity = sAreaResult.getAreaParentCode();  //上级区域代码（城市）
                    }
                }
            }

            //类似县级市的区域，无需再解析县区
            if(StringUtils.hasText(regArea)) {
                //解析县区，截取县区名称，可能为空，注意：判断的顺序也很重要！！！
                regAreaTemp = "";
                boolean regAreaSub = false;  //是否直接截取的前两位，代表县区名称
                if(regAddr.contains("旗")) {
                    regAreaTemp = regAddr.substring(0, regAddr.indexOf("旗") + "旗".length());
                    regAddr = regAddr.substring(regAddr.indexOf("旗") + "旗".length());
                } else if(regAddr.contains("县")) {
                    regAreaTemp = regAddr.substring(0, regAddr.indexOf("县") + "县".length());
                    regAddr = regAddr.substring(regAddr.indexOf("县") + "县".length());
                } else if(regAddr.contains("区")) {
                    regAreaTemp = regAddr.substring(0, regAddr.indexOf("区") + "区".length());
                    regAddr = regAddr.substring(regAddr.indexOf("区") + "区".length());
                } else if(regAddr.contains("市")) {
                    regAreaTemp = regAddr.substring(0, regAddr.indexOf("市") + "市".length());
                    regAddr = regAddr.substring(regAddr.indexOf("市") + "市".length());
                } else {
                    //没有获取到城市名称，直接截取前两位
                    regAreaTemp = regAddr.substring(0, 2);
                    regAreaSub = true;
                }
                //请求参数集合
                sAreaParam.clear();
                sAreaParam.setAreaName(regAreaTemp + "%");  //户籍区
                sAreaParam.setAreaType(PublicConstance.AREA_TYPE_AREA);  //区域类型：区
                sAreaParam.setAreaSts(PublicConstance.STATUS_VALID);  //生效
                //户籍市不为空
                if(StringUtils.hasText(regCity)) {
                    sAreaParam.setAreaParentCode(regCity);
                }
                //查询户籍区
                sAreaResult = sAreaMapper.selectBySArea(sAreaParam);
                if(sAreaResult != null) {
                    regArea = sAreaResult.getAreaCode();  //县区代码
                    //直接截取的前两位
                    if(regAreaSub) {
                        //若户籍地址是以查询出的县区名称为开头，则截取该县区名称（不包含）后面的户籍地址
                        if(regAddr.startsWith(sAreaResult.getAreaName())) {
                            regAddr = regAddr.substring(regAddr.indexOf(sAreaResult.getAreaName()) + sAreaResult.getAreaName().length());
                        }
                    }
                    if(StringUtils.hasText(regCity)) {
                        regCity = sAreaResult.getAreaParentCode();  //上级区域代码（城市）
                    }
                }
            }

            regAddrMap.put("regProvince", regProvince);  //户籍省
            regAddrMap.put("regCity", regCity);  //户籍市
            regAddrMap.put("regArea", regArea);  //户籍区
            regAddrMap.put("regAddr", regAddr);  //户籍详细地址
            errorCode = PublicConstance.RESPONSE_CODE_SUCCESS;
            errorMessage = "身份证解析成功";
            LOGGER_LOGBACK.info("[ParseIdCardComponent解析OCR身份证信息]申请编号【" + applCode + "】执行结束>>>>>>>>>>>>>>>>>");
        } catch (Exception e) {
            LOGGER_LOGBACK.error("[ParseIdCardComponent解析OCR身份证信息]申请编号【" + applCode + "】执行异常：" + e.getMessage(), e);
        } finally {
            regAddrMap.put(PublicConstance.ERROR_CODE, errorCode);  //返回结果码
            regAddrMap.put(PublicConstance.ERROR_MESSAGE, errorMessage);  //错误描述
        }

        return regAddrMap;
    }
}
