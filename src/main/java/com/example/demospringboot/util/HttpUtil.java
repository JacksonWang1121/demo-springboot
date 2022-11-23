package com.example.demospringboot.util;

import com.alibaba.fastjson.JSONObject;
import com.example.demospringboot.constance.PublicConstance;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import sun.net.www.protocol.http.Handler;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP 请求工具类
 */
public class HttpUtil {
    private static final Logger LOGGER = Logger.getLogger(HttpUtil.class);
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 60000;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);

        requestConfig = configBuilder.build();
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     *
     * @param apiUrl
     * @return
     */
    public static String doPost(String apiUrl) throws IOException {
        return doPost(apiUrl, new HashMap<String, Object>());
    }

    /**
     * 发送 GET 请求（HTTP），JSON形式
     *
     */
    public static String doGet(String apiUrl) throws IOException {
        String httpStr = null;
        CloseableHttpResponse response = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(apiUrl);
            httpGet.setConfig(requestConfig);
            response = httpClient.execute(httpGet);

            HttpEntity httpEntity = response.getEntity();
            LOGGER.info("[doGet]  " + apiUrl + "    Status Code: " + response.getStatusLine().getStatusCode());
            httpStr = EntityUtils.toString(httpEntity, PublicConstance.CHARSET_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
                response.close();
            }
        }
        return httpStr;
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param json   json对象
     */
    public static String doPost(String apiUrl, Object json) throws IOException {
        String httpStr = null;
        CloseableHttpResponse response = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), PublicConstance.CHARSET_DEFAULT);// 解决中文乱码问题
            stringEntity.setContentEncoding(PublicConstance.CHARSET_DEFAULT);
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            LOGGER.info(response.getStatusLine().getStatusCode());
            httpStr = EntityUtils.toString(entity, PublicConstance.CHARSET_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
                response.close();
            }
        }
        return httpStr;
    }

    public static String doPostLogin(String apiUrl, String username, String password) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        try {
            httpPost.setConfig(requestConfig);
            String paramcase = "merchant_name=" + username + "&merchant_pwd=" + password;
            StringEntity stringEntity = new StringEntity(paramcase, PublicConstance.CHARSET_DEFAULT);// 解决中文乱码问题
            stringEntity.setContentEncoding(PublicConstance.CHARSET_DEFAULT);
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            LOGGER.info(response.getStatusLine().getStatusCode());
            httpStr = EntityUtils.toString(entity, PublicConstance.CHARSET_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
                response.close();
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式
     *
     * @param apiUrl API接口URL
     * @param json   JSON对象
     */
    public static String doPostSSL(String apiUrl, Object json) throws IOException {
        CloseableHttpResponse response = null;
        String httpStr = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(createSSLConnSocketFactory()).build();
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), PublicConstance.CHARSET_DEFAULT);// 解决中文乱码问题
            stringEntity.setContentEncoding(PublicConstance.CHARSET_DEFAULT);
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, PublicConstance.CHARSET_DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
                response.close();
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式
     *
     * @param apiUrl API接口URL
     * @param json   JSON对象
     * @return
     */
    public static String doPostSSLSuanhuaBatchData(String apiUrl, Object json) throws IOException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String httpStr = null;
        try {
            httpClient = HttpClients.custom()
                    .setSSLSocketFactory(createSSLConnSocketFactory()).build();
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), PublicConstance.CHARSET_DEFAULT);// 解决中文乱码问题
            stringEntity.setContentEncoding(PublicConstance.CHARSET_DEFAULT);
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, PublicConstance.CHARSET_DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(httpClient != null) {
                httpClient.close();
            }
            if (response != null) {
                EntityUtils.consume(response.getEntity());
                response.close();
            }
        }
        return httpStr;
    }

    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("SSL");
            ctx.init(new KeyManager[0], new TrustManager[]{new X509TrustManager() {

                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

            }}, new SecureRandom());

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx, new HostnameVerifier() {

                @Override
                public boolean verify(String paramString, SSLSession paramSSLSession) {
                    return true;
                }

            });
            return sslsf;
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通用方法。
     *
     * @param reqMessage
     * @return
     * @throws Exception
     */
    public static String doHttpUrl(String apiUrl, String reqMessage) throws Exception {
        String repMessage = "";
        HttpURLConnection httpURLConnection = null;
        OutputStream out = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(null, apiUrl, new Handler());// 兼容tomcat跟weblogic
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(100000);
            // httpURLConnection.setReadTimeout(100000);
            httpURLConnection.setRequestProperty("content-type", "text/xml; charset=UTF-8");
            out = httpURLConnection.getOutputStream();
            out.write(reqMessage.getBytes(PublicConstance.CHARSET_DEFAULT));
            out.flush();
            httpURLConnection.connect();

            StringBuffer sb = new StringBuffer();
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), PublicConstance.CHARSET_DEFAULT));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            repMessage = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(out != null) {
                out.flush();
                out.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return repMessage;
    }

    public static String signature(String ORG_KEY, String timestamp, String nonce) {
        try {
            String[] arr = new String[]{timestamp, nonce, ORG_KEY};
            Arrays.sort(arr);// 参数值做字典排序
            String s = arr[0] + arr[1] + arr[2];
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(s.getBytes(PublicConstance.CHARSET_DEFAULT));
            return byts2hexstr(digest).toUpperCase();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String byts2hexstr(byte[] arrayBytes) {
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (int i = 0; i < arrayBytes.length; i++) {
            tmp = Integer.toHexString(arrayBytes[i] & 0xff);
            sb.append(tmp.length() == 1 ? "0" + tmp : tmp);
        }
        return sb.toString();
    }

    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String orgcode = "30320211201611001";// 机构号
        String orgSecretKey = "pNeDCdxjcZoOIOG";// 机构API私钥

        String url = "" + "application";
        String timestamp = System.currentTimeMillis() + "";
        String nonce = Math.round((1 + Math.random()) * 100000) + "";
        String sign = signature(orgSecretKey, timestamp, nonce);

        Map<String, Object> req = new HashMap<String, Object>();
        Map<String, String> data = new HashMap<String, String>();

        req.put("TIMESTAMP", timestamp);
        req.put("NONCE", nonce);
        req.put("SIGN", sign);
        req.put("ORG_NUM", orgcode);
        req.put("DATA", data);

        data.put("FRD_DATA_NUM", "1");
        data.put("APP_NUM", "201603011718130210");
        data.put("APP_DATE", "20160314164520");
        data.put("APP_LOAN_TYPE", "1");
        data.put("ID_TYPE", "0");
        data.put("ID_NUM", "522635198708184662");
        data.put("CUST_NAME", "张三");
        data.put("CELL_PHONE", "13363625452");

        // data.put("ID_HASH", md5("522635198708184662",charset));
        // data.put("CELL_PHONE_HASH", "1");

        // obj to json
        String reqStr = JSONObject.toJSONString(req);
        System.out.println("req:" + reqStr);
        String result = doPostSSL(url, reqStr);
        System.out.println("resp:" + result);

    }
}