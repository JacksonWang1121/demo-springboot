package com.example.demo.master.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.jwt.JWTUtil;
import com.example.demo.master.util.DruidUtil;
import org.springframework.util.StopWatch;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class TestHttp {
	
	public static void main(String[] args) throws Exception {
//		Hutool.printAllUtils();

		System.out.println(SecureUtil.md5("zhangsan123456"));
		System.out.println(new MD5().digestHex("zhangsan123456"));
		System.out.println("9bad41710724cf6511abde2a52416881".length());

		System.out.println(DateUtil.date().toStringDefaultTimeZone());

		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6ImNlNDVjNjc0MDY4MmNiZDQzZWI4ZWUwMzYzNmRkMTJiIiwiYXV0aFRva2VuIjoiODQ1Yjg4ZjFmZDFjZWQzNTE0MmZmMzM0YTY5NTA4NzgiLCJ1c2VybmFtZSI6Indhbmd3dSJ9.m87rFYyRq68boXZzMjY_WDYve7lG6Cr6yE1ULuCwTcw";
		System.out.println(JWTUtil.parseToken(token).getPayload("username"));

	}

	public static void testRedis() {
		//计时器
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("testRedis");
		System.out.println(">>>>>>>>>>Test Redis Start<<<<<<<<<<");

		//连接本地的 Redis 服务
		//mymaster@192.168.7.41:26379,192.168.7.41:26389,192.168.7.41:26399
		Jedis jedis = new Jedis("192.168.7.41", 26379);
		// 如果 Redis 服务设置了密码，需要下面这行，没有就不需要
		// jedis.auth("123456");
		//查看服务是否运行
		System.out.println("[testRedis]Redis连接成功，服务运行状态：" + jedis.ping());

		//获取申请编号
//        long applCde = jedis.incr("applCde_applysubmit");

		//关闭Redis连接
		jedis.close();

		stopWatch.stop();
		System.out.println(">>>>>>>>>>Test Redis End, Take " + stopWatch.getTotalTimeSeconds() + "s<<<<<<<<<<");
	}

	public static void batchInsertData() {
		try{
			System.err.println("请输入要导入数据的数量（条）：");
			Scanner scanner = new Scanner(System.in);
			int inputIndex = Integer.parseInt(scanner.nextLine());
			scanner.close();
			if(inputIndex == 0) {
				return;
			}
			System.out.println("-----------------------------------批量导入数据开始，预计导入" + inputIndex + "条数据-------------------------------------");
			long startTime = System.currentTimeMillis();
			int index = 1;
			int errIndex = 0;

//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			Connection connection  = DriverManager.getConnection("jdbc:oracle:thin:@192.168.7.156:1521:rtlfo30", "cmis", "cmis");
//			Connection connection  = DriverManager.getConnection("jdbc:oracle:thin:@192.168.7.35:1521:rtlfo20", "cmis", "cmis");
//			Connection connection  = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.135:1521:mgt00", "cmis", "Dpcafc123");
//			connection.setAutoCommit(false);

			Connection connection  = null;
			PreparedStatement pstmt = null;
			do {
				String applSeq = null;
				try {
					//数据报文
//					String lcApplStr = "{\"INSTU_CDE\":\"900000000\",\"APPL_SEQ\":\"" + applSeq + "\",\"APPL_CDE\":\"\",\"APPLY_DT\":\"2022-06-27\",\"FORM\":\"\",\"CUST_ID\":\"20220627783327\",\"ID_TYP\":\"20\",\"ID_NO\":\"420821199005151030\",\"CUST_NAME\":\"陈涛\",\"LOAN_TYP\":\"SRS118A3\",\"TYP_SEQ\":\"265387\",\"TYP_VER\":\"3\",\"PRO_PUR_AMT\":\"20152\",\"FST_PCT\":\".3\",\"FST_PAY\":\"6045.6\",\"PURPOSE\":\"\",\"APPLY_AMT\":\"14106.4\",\"APPLY_TNR\":\"36\",\"LOAN_FREQ\":\"1M\",\"WF_APPR_STS\":\"990\",\"APP_ORIGIN\":\"22\",\"MTD_CDE\":\"ECI\",\"RATE_TYP\":\"LPR 12M\",\"MTD_MODE\":\"FX\",\"REPC_OPT\":\"\",\"BASIC_INT_RAT\":\".0475\",\"PRICE_INT_RAT\":\".1388\",\"COOPR_CDE\":\"81012A\",\"COOPR_NAME\":\"上海开州实业有限公司\",\"COOPR_ZONE\":\"\",\"COOPR_TEL\":\"15821093828\",\"COOPR_SUB\":\"\",\"CRT_USR\":\"admin1\",\"CRT_DT\":\"2022-06-27 15:05:30\",\"LAST_CHG_DT\":\"2022-06-27 16:57:48\",\"LAST_CHG_USR\":\"ludi\",\"IN_STS\":\"20\",\"OUT_STS\":\"14\",\"APPLY_TNR_TYP\":\"M\",\"DUE_DAY_OPT\":\"DD\",\"DUE_DAY\":\"\",\"ASSURE\":\"\",\"CUS_KIND\":\"01\",\"CAR_TYPE\":\"03\",\"IS_SALES_PROMOTION\":\"\",\"IS_SUPER_FINANCIAL\":\"Y\",\"PURCHASE_TAX_AMT\":\"5752\",\"INSURANCE_AMT\":\"3400\",\"OTHER_AMT\":\"0\",\"SUB_REGI\":\"1002E\",\"REG_MANA\":\"pengzhiming\",\"SUB_AREA\":\"1002SFJ\",\"AREA_MANA\":\"laiwei\",\"SAL_USR\":\"10022755\",\"SAL_USR_NAME\":\"刘鹏\",\"SAL_USR_TEL\":\"13291999347\",\"CREDIT_OFFICER\":\"10022035\",\"CREDIT_OFFICER_NAME\":\"王思敏\",\"CREDIT_OFFICER_TEL\":\"18916073390\",\"SALES_PROMOTION_ID\":\"\",\"CRT_BCH\":\"900000000\",\"LOAN_CCY\":\"CNY\",\"TM_APPL_CDE\":\"\",\"CF_APPL_CDE\":\"2222062300085\",\"CAL_MODE\":\"01\",\"MAG_ID\":\"\",\"MSG_INFO\":\"\",\"GRP_CDE\":\"66204\",\"GRP_NAME\":\"它品牌新车LCV\",\"GRP_TYP\":\"01\",\"WHOLE_APPLY_AMT\":\"14106.4\",\"WHOLE_FST_PCT\":\".3\",\"WHOLE_FST_PAY\":\"6045.6\",\"SUPER_MEL_RATIO\":\"1.3599\",\"ADD_FINC_TYP\":\"01\",\"SUPER_MEL_CHOOSE\":\"\",\"JRCP_INSTM_AMT\":\"2081.5\",\"ZRCP_GZS_INSTM_AMT\":\"\",\"ZRCP_BX_INSTM_AMT\":\"\",\"ZRCP_QT_INSTM_AMT\":\"\",\"MONTHLY_MORTGAGE_PAYMENT\":\"2081.5\",\"LOAN_TYP_DESC\":\"SRS118A3 新易融LCV 36期 利率 13.88%\",\"SUPER_MEL_FINCIND\":\"Y\",\"GPS_IND\":\"N\",\"DISCONT_AMT\":\"\",\"DISCONT_INSTL\":\"\",\"LOAN_TYP_GL_CDE\":\"E_GL001\",\"WV_FEE_SEL\":\"\",\"HANDLING_CHANNEL\":\"01\",\"RECEIVING_WAY\":\"01\",\"EFFECT_DATE\":\"2022-06-20\",\"DIFFER_RATE\":\".1018\",\"GPS_AMT\":\"0\",\"DECORATE_AMT\":\"11000\",\"OTHER_AMT_UPPER_TYPE\":\"0\",\"OTHER_AMT_UPPER\":\"0\",\"OTHER_RATIO_UPPER\":\"0\",\"SERVICE_LINE\":\"lcvqpp\",\"FREIGHT_TIME\":\"01\",\"FREIGHT_RANGE\":\"01\",\"EXIST_CAR_NUM\":\"00\",\"PAY_CAR_REASON\":\"01\",\"CAR_USE_SCENE\":\"04\",\"TRANSPORT_TYPE\":\"01\",\"LCV_INDUSTRY\":\"03\",\"LCV_POSITION\":\"01\",\"LCV_MONTHLY_SALARY\":\"02\",\"GROUP_CODE\":\"86172\",\"GROUP_DESC\":\"上海开州shanghai Kaizhou\",\"SETTMENT_TYP\":\"01\",\"COMPANY_NAME\":\"\",\"UNIFIED_SOCIAL_CREDIT_CODE\":\"\",\"BUSINESS_DATE\":\"\",\"CSTNO\":\"10022755\",\"AFFILIATED_COMPANY\":\"\",\"AFFILIATED_COMPANY_CDE\":\"\",\"INVOICING_COMPANY\":\"上海开州实业有限公司\",\"INVOICING_COMPANY_CDE\":\"81012A\",\"DUE_DAY_CFG\":\"15\",\"CUST_NUMBER\":\"\"}";
//					String lcApplExtStr = "{\"APPL_SEQ\":\"" + applSeq + "\",\"CUS_TYPE\":\"09\",\"COMPL_RESULT\":\"\",\"SCORE\":\"\",\"SCORE_GRADE\":\"\",\"INSERTTYPE\":\"\",\"CHANNEL_TYP\":\"15\",\"CBC_NUM\":\"\",\"CRT_USR\":\"admin1\",\"CRT_DT\":\"2022-06-27 15:05:30\",\"LAST_CHG_DT\":\"2022-06-27 16:54:48\",\"LAST_CHG_USR\":\"ludi\",\"FST_APPROVE_DT\":\"\",\"GUA_TYPE\":\"N\",\"IS_SPECIAL\":\"N\",\"SPECIAL_USR\":\"\",\"FALSE_FLAG\":\"\",\"SOS_APPLY_AMOUN\":\"\",\"SOS_DOWN_PAYMENT\":\"\",\"SPECIAL_MATTER\":\"\",\"SPECIAL_MATTER_REMARK\":\"\",\"RISK_GRADE\":\"\",\"APP_AUTH\":\"\",\"GPS_TYPE\":\"\",\"INSURANCE_TYP\":\"01\",\"CUS_TYPE_AUTO\":\"09\",\"AUTO_REFUSAL_RESULT\":\"\",\"ORGIN_TYP_SEQ\":\"265387\",\"LOAN_CHANGE_TYPE\":\"00\",\"LOAN_AMT_CHANGE_RANGE\":\"0\",\"IS_ASSURE\":\"N\",\"GUA_CUS_TYPE\":\"\",\"RISK_FLAG\":\"A\",\"MOD_MARI_FLAG\":\"\",\"CUSTOMER_SOURCE\":\"02\",\"ARTIFICIAL_EXE\":\"N\",\"ARTIFICIAL_EXE_TYPE\":\"\",\"LOYAL_CUST\":\"N\",\"BAIRONG_CREDIT_SCORE\":\"\",\"EDITION\":\"1\",\"EXCHANGE_BOR_FLAG\":\"N\",\"IS_DOUBLE_LIMIT\":\"\",\"EXCHANGE_APPT\":\"\",\"FICO_CREDIT_SCORE\":\"\",\"LENGTH_QUALITY_UPDATE\":\"N\",\"APP_IN_ADVICE\":\"\",\"CALLBACK_MESSAGE\":\"\",\"LAST_AUTO_SEQ\":\"102022062714407\",\"BR_STRATEGY_RESULT\":\"\",\"MONTHLY_HOUSEHOLD_INCOME\":\"10000\",\"INCOME_LIABILITY_RATIO\":\".8966\",\"APPL_SIGN_DOC_TYPE\":\"01\",\"FK_SETTMENT_TYP\":\"\",\"FK_COMPANY_NAME\":\"\",\"FK_UNIFIED_SOCIAL_CREDIT_CODE\":\"\",\"FK_BUSINESS_DATE\":\"\",\"FICO_RISLEVEL\":\"C7\",\"FICO_RISLEVEL_AMT\":\"210000\",\"VIDEO_RECORDING_IND\":\"\",\"VIDEO_RECORDING_RESULT\":\"\",\"LOAN_APPROVAL_PROMPT_MSG\":\"\",\"IS_VERIFY\":\"Y\",\"\"}";

					//转为cmis实体对象
//					LcAppl lcAppl = new LcAppl(JSONObject.parseObject(lcApplStr, Map.class));
//					LcApplExt lcApplExt = new LcApplExt(JSONObject.parseObject(lcApplExtStr, Map.class));

					//插入数据库
//					SqlClient.insertAuto(lcAppl, connection);

					//获取数据库连接
					connection = DruidUtil.getConnection();

					//" + applSeq + "
					applSeq = String.valueOf(System.currentTimeMillis());
					String sql = "insert into lc_appl (INSTU_CDE, APPL_SEQ, APPL_CDE, APPLY_DT, FORM, CUST_ID, ID_TYP, ID_NO, CUST_NAME, LOAN_TYP, TYP_SEQ, TYP_VER, PRO_PUR_AMT, FST_PCT, FST_PAY, PURPOSE, APPLY_AMT, APPLY_TNR, LOAN_FREQ, WF_APPR_STS, APP_ORIGIN, MTD_CDE, RATE_TYP, MTD_MODE, REPC_OPT, BASIC_INT_RAT, PRICE_INT_RAT, COOPR_CDE, COOPR_NAME, COOPR_ZONE, COOPR_TEL, COOPR_SUB, CRT_USR, CRT_DT, LAST_CHG_DT, LAST_CHG_USR, IN_STS, OUT_STS, APPLY_TNR_TYP, DUE_DAY_OPT, DUE_DAY, ASSURE, CUS_KIND, CAR_TYPE, IS_SALES_PROMOTION, IS_SUPER_FINANCIAL, PURCHASE_TAX_AMT, INSURANCE_AMT, OTHER_AMT, SUB_REGI, REG_MANA, SUB_AREA, AREA_MANA, SAL_USR, SAL_USR_NAME, SAL_USR_TEL, CREDIT_OFFICER, CREDIT_OFFICER_NAME, CREDIT_OFFICER_TEL, SALES_PROMOTION_ID, CRT_BCH, LOAN_CCY, TM_APPL_CDE, CF_APPL_CDE, CAL_MODE, MAG_ID, MSG_INFO, GRP_CDE, GRP_NAME, GRP_TYP, WHOLE_APPLY_AMT, WHOLE_FST_PCT, WHOLE_FST_PAY, SUPER_MEL_RATIO, ADD_FINC_TYP, SUPER_MEL_CHOOSE, JRCP_INSTM_AMT, ZRCP_GZS_INSTM_AMT, ZRCP_BX_INSTM_AMT, ZRCP_QT_INSTM_AMT, MONTHLY_MORTGAGE_PAYMENT, LOAN_TYP_DESC, SUPER_MEL_FINCIND, GPS_IND, DISCONT_AMT, DISCONT_INSTL, LOAN_TYP_GL_CDE, WV_FEE_SEL, HANDLING_CHANNEL, RECEIVING_WAY, EFFECT_DATE, DIFFER_RATE, GPS_AMT, DECORATE_AMT, OTHER_AMT_UPPER_TYPE, OTHER_AMT_UPPER, OTHER_RATIO_UPPER, SERVICE_LINE, FREIGHT_TIME, FREIGHT_RANGE, EXIST_CAR_NUM, PAY_CAR_REASON, CAR_USE_SCENE, TRANSPORT_TYPE, LCV_INDUSTRY, LCV_POSITION, LCV_MONTHLY_SALARY, GROUP_CODE, GROUP_DESC, SETTMENT_TYP, COMPANY_NAME, UNIFIED_SOCIAL_CREDIT_CODE, BUSINESS_DATE, CSTNO, AFFILIATED_COMPANY, AFFILIATED_COMPANY_CDE, INVOICING_COMPANY, INVOICING_COMPANY_CDE, DUE_DAY_CFG, CUST_NUMBER) values ('900000000', '" + applSeq + "', null, '2022-08-07', null, '20220729607731', '20', '654121199306020665', '马梅', 'SRS84A2', '265383', '4', '221400', '.3', '66420', null, '154980', '24', '1M', '111', '23', 'ECI', 'LPR 12M', 'FX', null, '.037', '.0999', '70764S', '河南瑞铭汽车销售服务有限公司', null, '18538320986', null, 'admin1', '2022-08-07 14:44:56', '2022-08-07 15:14:15', 'robot', '16', '09', 'M', 'DD', null, null, '01', '03', null, 'Y', '200', '200', '0', '1002C', 'zhangjian', '1002CHen', 'jinyao', '10009091', '李晖', '18210010232', '10009091', '李晖', '18210010232', null, '900000000', 'CNY', null, null, '01', null, null, '66204', '它品牌新车LCV', '01', '154980', '.3', '66420', '1.107', '01', null, '7150.83', null, null, null, '7150.83', 'SRS84A2 新易融LCV 24期 9.99%', 'Y', 'N', null, null, 'E_GL001', null, '02', '02', '2022-06-20', '.0629', '0', '21000', '0', '0', '0', 'lcvqpp', '01', '01', '01', '01', '10', '01', '01', '01', '04', '66067', '河南瑞铭henan ruiming', '01', null, null, null, '10009091', null, null, null, '70764S', '17', null)";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();//commit,jdbc默认就是自动提交事务
					//给sql中的参数赋值
//					pstmt.setInt(1, 7);
//					pstmt.setString(2, "李忠");
//					pstmt.setInt(3, 22);

					sql = "insert into lc_appl_ext (APPL_SEQ, CUS_TYPE, COMPL_RESULT, SCORE, SCORE_GRADE, INSERTTYPE, CHANNEL_TYP, CBC_NUM, CRT_USR, CRT_DT, LAST_CHG_DT, LAST_CHG_USR, FST_APPROVE_DT, GUA_TYPE, IS_SPECIAL, SPECIAL_USR, FALSE_FLAG, SOS_APPLY_AMOUN, SOS_DOWN_PAYMENT, SPECIAL_MATTER, SPECIAL_MATTER_REMARK, RISK_GRADE, APP_AUTH, GPS_TYPE, INSURANCE_TYP, CUS_TYPE_AUTO, AUTO_REFUSAL_RESULT, ORGIN_TYP_SEQ, LOAN_CHANGE_TYPE, LOAN_AMT_CHANGE_RANGE, IS_ASSURE, GUA_CUS_TYPE, RISK_FLAG, MOD_MARI_FLAG, CUSTOMER_SOURCE, ARTIFICIAL_EXE, ARTIFICIAL_EXE_TYPE, LOYAL_CUST, BAIRONG_CREDIT_SCORE, EDITION, EXCHANGE_BOR_FLAG, IS_DOUBLE_LIMIT, EXCHANGE_APPT, FICO_CREDIT_SCORE, LENGTH_QUALITY_UPDATE, APP_IN_ADVICE, CALLBACK_MESSAGE, LAST_AUTO_SEQ, BR_STRATEGY_RESULT, MONTHLY_HOUSEHOLD_INCOME, INCOME_LIABILITY_RATIO, APPL_SIGN_DOC_TYPE, FK_SETTMENT_TYP, FK_COMPANY_NAME, FK_UNIFIED_SOCIAL_CREDIT_CODE, FK_BUSINESS_DATE, FICO_RISLEVEL, FICO_RISLEVEL_AMT, VIDEO_RECORDING_IND, VIDEO_RECORDING_RESULT, LOAN_APPROVAL_PROMPT_MSG, IS_VERIFY) values ('" + applSeq + "', null, '01', null, null, null, null, null, 'admin1', '2022-08-07 14:44:56', '2022-08-07 15:08:00', 'liuhong', null, 'N', 'N', null, null, null, null, null, null, 'H', 'A', null, '01', null, null, '265383', '00', '0', 'N', null, 'A', null, '01', 'N', null, 'N', '454', '1', 'N', null, 'N', null, 'N', null, null, '102022080744322', '0', '40000', '.1788', '01', null, null, null, null, 'C9', '360000', 'N', null, null, 'Y')";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//" + goodsSeq + "
					String goodsSeq = "2322080" + String.valueOf(System.currentTimeMillis());
					sql = "insert into lc_appl_goods (APPL_SEQ, GOODS_SEQ, GOODS_KIND, GOODS_CLASS, GOODS_MODEL, GOODS_PRICE, GOODS_NUM, GOODS_SING_PRICE, LAST_CHG_DT, LAST_CHG_USR, GOODS_NAME, AVAIL_NUM, GOODS_KIND_NAME, GOODS_CLASS_NAME, CAR_MFRS_ID, CAR_MFRS_NAME, YEAR_OF_PRODUCTION_ID, YEAR_OF_PRODUCTION_NAME, YEAR_MODE_CDE, YEAR_MODE_NAME, CRT_USR, CRT_DT) values ('" + applSeq + "', '" + goodsSeq + "', '8026', '80174', '853371', '221400', '1', '200000', '2022-08-07 15:12:22', 'admin1', '2017款GMC-Savana-6.0-A/MT后驱公务版3500', '0', 'GMC（商）', 'GMCSavana[进口]', '8073', 'GMC', '2018', '2018年', null, null, 'admin1', '2022-08-07 14:44:56')";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					sql = "insert into lc_goods_veh (GOODS_SEQ, VEH_CAR_TYPE, VEH_GEARBOX_TYP, VEH_EXHST_VAL, VEH_DIRECT_PRICE, VEH_CITY_CARD, VEH_PROVINCE, VEH_CHASSIS, VEH_TYPE, VEH_CONDITION, VEH_IS_LUXURY_MODELS, VEH_FRIST_DT, VEH_RANGE, VEH_TRIMID, VEH_TRIMNAME, VEH_MARKET_PRICE, LAST_CHG_DT, LAST_CHG_USR, VHE_USE_CHAR, VEH_PRICE_PRO, VEH_ELE_CAR, VEH_CAR_MODEL, VEH_CITY, VEH_TRIM_PRICE, TRANSMISSION_TYPE, AVERAGE_SALE_PRICE, CRT_USR, CRT_DT, IS_OTHER_CAR, OTHER_CAR_NAME, IS_MANUAL_INPUT, CAR_AGE, CAR_LENGTH, TOTAL_QUALITY, CAR_TYPE_GRADE, CAR_SERIES_TYPE, FUEL_TYPE, DRIVING_CERTIFICATE, VEH_TYPE_NUMBER, OCR_FIRST_REG_DATE, OCR_VIN_NO, OCR_VEHICLE_OWNER, OCR_ENGINE_NUMBER, VEHICLE_OWNER, ENGINE_NUMBER, VIN_INPUT_MODE, GOODS_VEH_CHANGE, MAINTENANCE_RECORDS, FORMER_OWNER_RELATION, VHE_USE_CHAR_NEW, CAR_PURPOSES, POWER_TYPE, FIRST_DRIVING_LIC_DT, PURCHASE_TAX_AMT, INSURANCE_AMT, GPS_AMT, DECORATE_AMT, OTHER_AMT) values ('" + goodsSeq + "', '03', null, null, '688000', '110100', '110000', null, null, null, null, null, null, null, null, null, '2022-08-07 15:08:00', 'liuhong', null, '.2907', null, null, '110100', null, '01', '688000', 'admin1', '2022-08-07 14:44:56', null, null, 'N', null, '6200', '0', 'MPV', 'MPV', '无铅汽油95#', '01', null, null, null, null, null, null, null, null, null, null, null, '1', '02', '01', '2007-09-24', null, null, null, null, null)";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//" + apptSeq + "
					String apptSeq = "2322080" + String.valueOf(System.currentTimeMillis());
					sql = "insert into lc_appl_appt (INSTU_CDE, APPT_SEQ, APPL_SEQ, APPT_TYP, CUST_ID, CUST_NAME, ID_TYP, ID_NO, APPT_RELATION, CUS_KIND, CRT_USR, CRT_BCH, CRT_DT, LAST_CHG_USR, LAST_CHG_DT, STS, ID_IS_PERMANENT, ID_END_DT, SOS_SCORE, ID_IS_TEMPORARY, BAIRONG_SCORE, ID_START_DT, AUTHORIZATION_TYPE, FIRST_LOAN_IND) values ('900000000', '" + apptSeq + "', '" + applSeq + "', '01', '20220729607731', '马梅', '20', '654121199306020665', null, '01', 'admin1', '900000000', '2022-08-03 19:29:35', 'admin1', '2022-08-03 19:29:35', 'A', 'N', '2022-12-05', '552', 'N', null, '2012-12-05', '01', null)";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//" + apptSeq2 + "
					String apptSeq2 = "2322080" + String.valueOf(System.currentTimeMillis());
					sql = "insert into lc_appl_appt (INSTU_CDE, APPT_SEQ, APPL_SEQ, APPT_TYP, CUST_ID, CUST_NAME, ID_TYP, ID_NO, APPT_RELATION, CUS_KIND, CRT_USR, CRT_BCH, CRT_DT, LAST_CHG_USR, LAST_CHG_DT, STS, ID_IS_PERMANENT, ID_END_DT, SOS_SCORE, ID_IS_TEMPORARY, BAIRONG_SCORE, ID_START_DT, AUTHORIZATION_TYPE, FIRST_LOAN_IND) values ('900000000', '" + apptSeq2 + "', '" + applSeq + "', '02', '20210825606755', '徐俊伟', '20', '532526199504190459', '01', '01', 'admin1', '900000000', '2022-08-03 19:31:14', 'admin1', '2022-08-03 19:31:14', 'A', 'N', '2038-07-03', null, 'N', null, '2018-07-03', '01', null)";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//" + apptSeq3 + "
					String apptSeq3 = "2322080" + String.valueOf(System.currentTimeMillis());
					sql = "insert into lc_appl_appt (INSTU_CDE, APPT_SEQ, APPL_SEQ, APPT_TYP, CUST_ID, CUST_NAME, ID_TYP, ID_NO, APPT_RELATION, CUS_KIND, CRT_USR, CRT_BCH, CRT_DT, LAST_CHG_USR, LAST_CHG_DT, STS, ID_IS_PERMANENT, ID_END_DT, SOS_SCORE, ID_IS_TEMPORARY, BAIRONG_SCORE, ID_START_DT, AUTHORIZATION_TYPE, FIRST_LOAN_IND) values ('900000000', '" + apptSeq3 + "', '" + applSeq + "', '03', '20220113607201', '王妍芳', '20', '372321197712218960', '03', '01', 'admin1', '900000000', '2022-08-03 19:33:00', 'admin1', '2022-08-03 19:33:00', 'A', 'N', '2038-07-03', null, 'N', null, '2018-07-03', '01', null)";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					sql = "insert into lc_appt_indiv (APPT_SEQ, APPL_SEQ, INDIV_BIRTHDAY, INDIV_AGE, INDIV_SEX, INDIV_MARITAL, INDIV_EDU, INDIV_DEGREE, REG_PROVINCE, REG_CITY, LIVE_PROVINCE, LIVE_CITY, LIVE_AREA, LIVE_ADDR, LIVE_ADDR_NO, LIVE_ZIP, LIVE_INFO, INDIV_MOBILE, INDIV_MOBILE1, INDIV_MOBILE2, INDIV_MOBILE3, INDIV_MOBILE4, INDIV_MOBILE5, INDIV_FMLY_ZONE, INDIV_FMLY_TEL, POSITION_OPT, INDIV_EMP_NAME, INDIV_EMP_TYP, INDIV_EMP_INDUSTRY, INDIV_POSITION, INDIV_EMP_PROVINCE, INDIV_EMP_CITY, INDIV_EMP_AREA, INDIV_EMP_ADDR, INDIV_EMP_YRS, INDIV_EMP_ZONE, INDIV_EMP_TEL, INDIV_EMP_TEL_SUB, LAST_CHG_USR, LAST_CHG_DT, IF_LOCAL_REG, MONTHLY_GROSS_SALARY, REG_ADDR, LIVE_IS_REG, INDIV_PROVINCE, INDIV_CITY, INDIV_AREA, INDIV_ADDR, LIVE_IS_INDIV, RATIO, INDIV_BRANCH, REG_AREA, COUNTRY_CODE, COUNTRY_NAME, NATION) values ('" + apptSeq + "', '" + applSeq + "', '1993-06-02', '29', '20', '10', '10', null, '110000', '110100', '110000', '110100', '110101', '西单', '1', null, '04', '18210010232', null, null, null, null, null, null, null, '10', '宇信科技', '01', 'I', '01', '110000', '110100', '110101', '乐成', '10', null, null, null, 'admin1', '2022-08-03 19:29:35', null, '20000', '东单', 'N', '110000', '110100', '110102', '西单', 'N', null, '挖矿', '110101', 'CHN', '中华人民共和国', '04')";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					sql = "insert into lc_appt_indiv (APPT_SEQ, APPL_SEQ, INDIV_BIRTHDAY, INDIV_AGE, INDIV_SEX, INDIV_MARITAL, INDIV_EDU, INDIV_DEGREE, REG_PROVINCE, REG_CITY, LIVE_PROVINCE, LIVE_CITY, LIVE_AREA, LIVE_ADDR, LIVE_ADDR_NO, LIVE_ZIP, LIVE_INFO, INDIV_MOBILE, INDIV_MOBILE1, INDIV_MOBILE2, INDIV_MOBILE3, INDIV_MOBILE4, INDIV_MOBILE5, INDIV_FMLY_ZONE, INDIV_FMLY_TEL, POSITION_OPT, INDIV_EMP_NAME, INDIV_EMP_TYP, INDIV_EMP_INDUSTRY, INDIV_POSITION, INDIV_EMP_PROVINCE, INDIV_EMP_CITY, INDIV_EMP_AREA, INDIV_EMP_ADDR, INDIV_EMP_YRS, INDIV_EMP_ZONE, INDIV_EMP_TEL, INDIV_EMP_TEL_SUB, LAST_CHG_USR, LAST_CHG_DT, IF_LOCAL_REG, MONTHLY_GROSS_SALARY, REG_ADDR, LIVE_IS_REG, INDIV_PROVINCE, INDIV_CITY, INDIV_AREA, INDIV_ADDR, LIVE_IS_INDIV, RATIO, INDIV_BRANCH, REG_AREA, COUNTRY_CODE, COUNTRY_NAME, NATION) values ('" + apptSeq2 + "', '" + applSeq + "', '1995-04-19', '27', '10', '20', '10', null, '110000', '110100', '110000', '110100', '110101', '西单', '1', null, '04', '18899999898', null, null, null, null, null, null, null, '10', '宇信科技', '04', 'I', '05', '110000', '110100', '110101', '乐成', '10', null, null, null, 'admin1', '2022-08-03 19:31:14', null, '20000', '西单', 'N', '110000', '110100', '110101', '东西', 'N', null, '测试', '110105', 'CHN', '中华人民共和国', '01')";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					sql = "insert into lc_appt_indiv (APPT_SEQ, APPL_SEQ, INDIV_BIRTHDAY, INDIV_AGE, INDIV_SEX, INDIV_MARITAL, INDIV_EDU, INDIV_DEGREE, REG_PROVINCE, REG_CITY, LIVE_PROVINCE, LIVE_CITY, LIVE_AREA, LIVE_ADDR, LIVE_ADDR_NO, LIVE_ZIP, LIVE_INFO, INDIV_MOBILE, INDIV_MOBILE1, INDIV_MOBILE2, INDIV_MOBILE3, INDIV_MOBILE4, INDIV_MOBILE5, INDIV_FMLY_ZONE, INDIV_FMLY_TEL, POSITION_OPT, INDIV_EMP_NAME, INDIV_EMP_TYP, INDIV_EMP_INDUSTRY, INDIV_POSITION, INDIV_EMP_PROVINCE, INDIV_EMP_CITY, INDIV_EMP_AREA, INDIV_EMP_ADDR, INDIV_EMP_YRS, INDIV_EMP_ZONE, INDIV_EMP_TEL, INDIV_EMP_TEL_SUB, LAST_CHG_USR, LAST_CHG_DT, IF_LOCAL_REG, MONTHLY_GROSS_SALARY, REG_ADDR, LIVE_IS_REG, INDIV_PROVINCE, INDIV_CITY, INDIV_AREA, INDIV_ADDR, LIVE_IS_INDIV, RATIO, INDIV_BRANCH, REG_AREA, COUNTRY_CODE, COUNTRY_NAME, NATION) values ('" + apptSeq3 + "', '" + applSeq + "', '1977-12-21', '44', '20', '10', '10', null, '110000', '110100', '110000', '110100', '110101', '呼呼', '1', null, '04', '17788877777', null, null, null, null, null, null, null, '10', '宇信科技', '04', 'I', '01', '110000', '110100', '110101', '乐成', '14', null, null, null, 'admin1', '2022-08-03 19:33:00', null, '20000', '西单', 'N', '110000', '110100', '110101', '哈喽', 'N', null, '测试', '110106', 'CHN', '中华人民共和国', '01')";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//" + relSeq + "
					String relSeq = "2322080" + String.valueOf(System.currentTimeMillis());
					sql = "insert into lc_appt_rel (REL_SEQ, APPT_SEQ, APPL_SEQ, REL_NAME, REL_MOBILE, REL_RELATION, REL_ADDR, LAST_CHG_DT, LAST_CHG_USR, STS, ID_TYP, ID_NO, REL_PROVINCE, REL_CITY, REL_AREA, HOME_REL, CRT_USR, CRT_DT) values ('" + relSeq + "', '" + apptSeq + "', '" + applSeq + "', '测试员', '18877722222', '05', '西单', '2022-08-04 14:02:09', 'admin1', 'A', null, null, '110000', '110100', '110101', null, 'admin1', '2022-08-07 14:54:48')";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//" + relSeq2 + "
					String relSeq2 = "2322080" + String.valueOf(System.currentTimeMillis());
					sql = "insert into lc_appt_rel (REL_SEQ, APPT_SEQ, APPL_SEQ, REL_NAME, REL_MOBILE, REL_RELATION, REL_ADDR, LAST_CHG_DT, LAST_CHG_USR, STS, ID_TYP, ID_NO, REL_PROVINCE, REL_CITY, REL_AREA, HOME_REL, CRT_USR, CRT_DT) values ('" + relSeq2 + "', '" + apptSeq + "', '" + applSeq + "', '测试工', '12233355654', '05', '西单', '2022-08-04 14:02:09', 'admin1', 'A', null, null, '110000', '110100', '110101', null, 'admin1', '2022-08-07 14:54:48')";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//" + contNo + "
					String contNo = applSeq + "A";
					sql = "insert into lc_appl_cont (CONT_NO, APPL_SEQ, CUS_KIND, CAR_TYPE, CONT_AMT, AVAIL_AMT, STR_DT, END_DT, CONT_STS, SIGN_ORGAN, SIGN_USR, SIGN_DT, LAST_CHG_USR, LAST_CHG_DT, GUARANTEE_TYP, CRT_USR, CRT_BCH, CRT_DT, LENDING_MODE, LENDING_AUTHORITY, CAL_MODE, MAG_ID, MSG_INFO, MORT_REG_STATUS, APPL_DT, BCH_CDE, CUST_ID, ID_TYPE, ID_NO, ISS_CTRY, CUST_NAME, GUA_TYPE, IS_ASSURE, COOPR_CDE, CONT_MONTHLY_PAYMENT, CONT_MONTHLY_PAYMENT_TWO, CONT_MONTHLY_PAYMENT_THREE, PRELIMINARY_HEARING_SEQ, MANUAL_AUDIT, MANUAL_AUDIT_JXS, IS_AUTO, AVOID_UNITE, AVOID_FLAG, EDITION, REQUIRE_TYPE, PRINT_LOAN_FREE_CONT, BASIC_DEPOSIT_ACCOUNT, BASIC_ACCOUNT_BANK, BASIC_ACCOUNT_BANK_NAME, BASIC_ACCOUNT_BRANCH, CUST_NUMBER, CUST_NUMBER_IND, HANDLING_CHANNEL) values ('" + contNo + "', '" + applSeq + "', '01', '03', '154980', null, '2022-08-07', '2024-08-07', '200', '70764S', '10009091', '2022-08-07', 'robot', '2022-08-07 15:14:15', null, 'liuhong', '1004', '2022-08-07 15:08:05', '00', '01', '01', null, null, 'N', '2022-08-07', null, '20220729607731', '20', '654121199306020665', null, '马梅', 'N', 'N', '70764S', '7150.83', null, null, '102022080744325', 'N', 'N', 'N', 'N', 'A', '1', '01', 'Y', null, null, null, null, null, 'N', null)";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//" + applAcSeq + "
					String applAcSeq = "2322080" + String.valueOf(System.currentTimeMillis());
					sql = "insert into lc_appl_acc_info (APPL_AC_SEQ, APPL_SEQ, APPL_AC_KIND, APPL_AC_TYP, APPL_AC_BANK, APPL_AC_BCH, APPL_AC_NAM, APPL_AC_NO, APPL_ID_TYP, APPL_ID_NO, LAST_CHG_DT, LAST_CHG_USR, APPL_AC_ALIPAY_NO, APPL_AC_PROVINCE, APPL_AC_CITY, APPL_AC_AREA, APPL_AC_ADDR, CONT_NO, APPL_AC_BCH_DESC, APPL_AC_TYP_DETAIL, APPL_AC_BANK_DESC, ELEMENT_RESULT, BANKCARD_CHECK_MESSAGE) values ('" + applAcSeq + "', '" + applSeq + "', '02', '01', '103', null, '马梅', '6228480838282959678', '20', '654121199306020665', '2022-08-07 15:10:26', 'admin1', null, null, null, null, null, '" + contNo + "', null, '06', '中国农业银行', 'N', '认证信息不匹配，银行卡无效。')";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//" + applAcSeq2 + "
					String applAcSeq2 = "2322080" + String.valueOf(System.currentTimeMillis());
					sql = "insert into lc_appl_acc_info (APPL_AC_SEQ, APPL_SEQ, APPL_AC_KIND, APPL_AC_TYP, APPL_AC_BANK, APPL_AC_BCH, APPL_AC_NAM, APPL_AC_NO, APPL_ID_TYP, APPL_ID_NO, LAST_CHG_DT, LAST_CHG_USR, APPL_AC_ALIPAY_NO, APPL_AC_PROVINCE, APPL_AC_CITY, APPL_AC_AREA, APPL_AC_ADDR, CONT_NO, APPL_AC_BCH_DESC, APPL_AC_TYP_DETAIL, APPL_AC_BANK_DESC, ELEMENT_RESULT, BANKCARD_CHECK_MESSAGE) values ('" + applAcSeq2 + "', '" + applSeq + "', '01', '02', '104', '104491060125', '河南瑞铭汽车销售服务有限公司', '252018684271', null, null, '2022-08-07 15:10:26', 'admin1', null, '410000', null, null, null, '" + contNo + "', '中国银行郑州市上街区支行', '01', '中国银行', null, null)";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//" + applChargeSeq + "
					String applChargeSeq = "2322080" + String.valueOf(System.currentTimeMillis());
					sql = "insert into lc_appl_charge (APPL_SEQ, APPL_CHARGE_SEQ, COMM_CDE, COMM_DESC, CAL_MTD, CAL_BASIC, COMM_VAL, COMM_PCT, COMM_CDT, VEH_KIND, VEH_SEL_CDT, COOPR_SEL_CDT, CAL_TYP, STS, ACC_IND, FEE_AMT_TYP, ACC_AMT_TYP, INSTU_CDE, GOODS_SEQ, LAST_CHG_DT, LAST_CHG_USR, CRT_USR, CRT_DT) values ('" + applSeq + "', '" + applChargeSeq + "', 'CF01', '无服务费', null, null, '0', null, 'ALL', null, null, null, '01', 'A', null, null, null, '900000000', '" + goodsSeq + "', '2022-08-07 15:08:00', 'liuhong', 'admin1', '2022-08-07 14:44:56')";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//" + loanMtdSeq + "
					String loanMtdSeq = "2322080" + String.valueOf(System.currentTimeMillis());
					sql = "insert into lc_appl_mtd (LOAN_MTD_SEQ, APPL_SEQ, MTD_CDE, MTD_TYP, MTD_PRCP_FREQ, MTD_PRCP_UNIT, LOAN_INSTAL, MTD_FREQ, MTD_CHG_BASE, PRCP_FREQ, PROP_PCT, PRCP_AMT, APPL_INT_BASE, LAST_CHG_DT, LAST_CHG_USR, MAX_PCT, MIN_PCT, MTD_DESC, DEF_PCT, PHASE_NUM, MTD_CLASS, GL_MTD_TYP, CRT_USR, CRT_DT) values ('" + loanMtdSeq + "', '" + applSeq + "', 'ECI', '01', null, null, '24', null, null, null, null, null, 'M', '2022-08-07 15:08:00', 'liuhong', null, null, '等额本息', null, null, null, '01', null, null)";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//" + applMtdSeq + "
					String applMtdSeq = "2322080" + String.valueOf(System.currentTimeMillis());
					sql = "insert into lc_appl_mtd_dtl (APPL_SEQ, LOAN_MTD_SEQ, APPL_MTD_SEQ, APPL_MTD_NO, APPL_MTD_STR_INSTL, APPL_MTD_END_INSTL, APPL_MTD_PRCP_ADJ, APPL_MTD_CALC_TNR, APPL_MTD_RPYM_OPT, APPL_MTD_SETL_TYP, APPL_RAT_BASE, APPL_MTD_INT_SPRD, APPL_MTD_INT_ADJ, APPL_MTD_INT_RATE, APPL_MTD_OD_ADJ, APPL_MTD_OD_RATE, APPL_MTD_DEF_ADJ, APPL_MTD_DEF_RATE, LAST_CHG_DT, LAST_CHG_USR, APPL_FIXED_OD_IND, APPL_FIXED_OD_TYP, MTH_AMT, PRINCIPAL, DEF_PCT_AMT, DEF_PCT, CRT_USR, CRT_DT, MTD_TYP, MTD_CLASS, SEQ_NO, SPLITAMT, SEQ_NUM) values ('" + applSeq + "', '" + loanMtdSeq + "', '" + applMtdSeq + "', '1', '1', '24', null, null, 'IP', 'T', 'OSP', '.0629', '0', '.0999', '.5', '.19485', '1', '.1998', '2022-08-07 15:08:05', 'liuhong', 'N', 'P R 01', null, '154980', null, null, 'admin1', '2022-08-07 14:44:56', '01', null, '1', '154980', '1')";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//" + dnSeq + "
					String dnSeq = "10" + String.valueOf(System.currentTimeMillis());
					sql = "insert into lpb_appl_dn (DN_SEQ, APPL_SEQ, CONT_NO, DN_AMT, DN_DT, DN_STS, WF_APPR_STS, IN_STS, OUT_STS, CRT_DT, CRT_USR, LAST_CHG_DT, LAST_CHG_USR, CUST_ID, CRT_BCH, INSTU_CDE, DN_USR, CUS_KIND, ID_TYPE, ID_NO, CUST_NAME, APPLY_DT, CAR_TYPE, COOPR_CDE, IS_INTERCEPT, BLACK_REASON, PAY_DT, CUST_NUMBER) values ('" + dnSeq + "', '" + applSeq + "', '" + contNo + "', '154980', null, '100', null, null, null, '2022-08-07 15:08:05', 'liuhong', '2022-08-07 15:08:00', 'admin1', '20220729607731', '1004', '900000000', 'liuhong', '01', '20', '654121199306020665', '马梅', '2022-08-07', '03', '70764S', null, null, null, null)";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//" + goodsCde + "
					String goodsCde = "10" + String.valueOf(System.currentTimeMillis());
					sql = "insert into lm_goods (GOODS_CDE, GOODS_SEQ, CONT_NO, GOODS_KIND, GOODS_CLASS, GOODS_MODEL, GOODS_NAME, GOODS_PRICE, GOODS_FST_PCT, GOODS_FST_PAY, LOAN_AMT, SALVAGE_AMT, RMK, LAST_CHG_USR, LAST_CHG_DT, MORT_REG_STATUS, GOODS_KIND_NAME, GOODS_CLASS_NAME, CAR_MFRS_ID, CAR_MFRS_NAME, YEAR_OF_PRODUCTION_ID, YEAR_OF_PRODUCTION_NAME, CONT_NO_SUB, YEAR_MODE_CDE, YEAR_MODE_NAME) values ('" + goodsCde + "', '" + goodsSeq + "', '" + contNo + "', '8026', '80174', '853371', '2017款GMC-Savana-6.0-A/MT后驱公务版3500', '200000', '.3', '66420', '154980', null, null, 'admin1', '2022-08-07 15:08:00', 'N', 'GMC（商）', 'GMCSavana[进口]', '8073', 'GMC', '2018', '2018年', null, null, null)";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					sql = "insert into lm_goods_veh (GOODS_CDE, VEH_CAR_TYPE, VEH_GEARBOX_TYP, VEH_EXHST_VAL, VEH_DIRECT_PRICE, VEH_CITY_CARD, VEH_PROVINCE, VEH_CHASSIS, VEH_TYPE, VEH_CONDITION, VEH_IS_LUXURY_MODELS, VEH_FRIST_DT, VEH_RANGE, VEH_TRIMID, VEH_TRIMNAME, VEH_MARKET_PRICE, LAST_CHG_DT, LAST_CHG_USR, VHE_USE_CHAR, VEH_PRICE_PRO, VEH_CAR_MODEL, VEH_ELE_CAR, VEH_TRIM_PRICE, VEH_LIC_NO, FLD_FDJXH, VEH_CITY, TRANSMISSION_TYPE, AVERAGE_SALE_PRICE, CONT_NO, CONT_NO_SUB, VIN_STS, CAR_PURPOSES, POWER_TYPE, PURCHASE_TAX_AMT, INSURANCE_AMT, GPS_AMT, DECORATE_AMT, OTHER_AMT) values ('" + goodsCde + "', '03', null, null, '688000', '110100', '110000', 'LZWCBAGA5DA136312', null, null, null, null, null, '853371', '2017款GMC-Savana-6.0-A/MT后驱公务版3500', null, '2022-08-07 15:12:22', 'admin1', null, null, null, null, null, null, null, '110100', '01', '688000', '2322080300040A', null, 'O', '02', '01', null, null, null, null, null)";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//" + serno + "
					String serno = "2322080" + String.valueOf(System.currentTimeMillis());
					sql = "insert into Lpb_Appl_Wait (SERNO, CONT_NO, DN_SEQ, DN_STS, LAST_CHG_DT, LAST_CHG_USR, CRT_USR, CRT_DT) values ('" + serno + "', '" + contNo + "', '" + dnSeq + "', '400', '2019-11-15 09:11:21', 'robot', 'robot', '2019-11-15 09:11:21')";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();

					//手动提交事务
					connection.commit();
					System.out.println("第" + index + "条数据（申请编号【" + applSeq + "】）导入成功");
				} catch (Exception e) {
					errIndex++;
					connection.rollback();
					System.out.println("第" + index + "条数据（申请编号【" + applSeq + "】）导入失败：" + e.getMessage());
					e.printStackTrace();
				} finally {
					//关闭资源，非常重要
					DruidUtil.closeConnection(null, null, connection);
				}

				index++;
			} while(index <= inputIndex);

			long endTime = System.currentTimeMillis();
			System.out.println("-----------------------------------批量导入数据结束，共导入" + (index - 1) + "条数据，其中导入失败" + errIndex + "条，用时" + (endTime - startTime) / 1000 + "秒-------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

