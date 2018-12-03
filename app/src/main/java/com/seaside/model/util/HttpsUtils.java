package com.seaside.model.util;

import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import com.seaside.utils.DUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.apache.http.NameValuePair;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * Createed by Deven
 * on 2018/11/29.
 * Descibe: TODO:
 */
public class HttpsUtils {
    private static final String MAC_NAME = "HMACSHA1";
    private static final String ENCODING = "UTF-8";

    public static String getToken(Map<String, String> paramMap, String HTTPMethod, String AccessSecret) {
        return getToken(paramMap, HTTPMethod, AccessSecret, false);
    }

    /**
     * 获取加密token
     * params传入参数对，包括公共参数
     * HTTPMethod为"GET"或"POST"
     * AccessSecret接口定义
     *
     * @return
     */
    public static String getToken(Map<String, String> paramMap, String HTTPMethod, String AccessSecret, boolean isSreach) {
        return createToken(HTTPMethod, AccessSecret, paramMap, isSreach);
    }

    @Nullable
    private static String createToken(String HTTPMethod, String AccessSecret, Map<String, String> paramMap, boolean isSreach) {
        // 1.1 对参数名进行字典排序
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        // 1.2 url编码后，拼接有序的参数名-值串
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            try {
                if (isSreach) {
                    sb.append(URLEncoder.encode(keyArray[i], "UTF-8")).append("=").append(URLEncoder.encode(Objects.requireNonNull(paramMap.get(keyArray[i])).replace("%5C", "\\").replace("%25", "%"), "UTF-8"));
                } else {
                    sb.append(URLEncoder.encode(keyArray[i], "UTF-8")).append("=").append(URLEncoder.encode(paramMap.get(keyArray[i]), "UTF-8"));
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (i < keyArray.length - 1) {
                sb.append("&");
            }
        }

        String codes = sb.toString();

        codes = codes.replaceAll("\\+", "%20");

        Log.i("test", "codes=" + codes);

        // 2.0 authString= HTTPMethod
        // +”&”+UrlEncode(“/”)+”&”+UrlEncode(queryString)
        String authString = getAuthString(HTTPMethod, codes);
        Log.i("test", "codes  authString=" + authString);

        // 3.0 ：使用第2步中构造的字符串计算签名认证的HMAC值
        String HMAC = HmacSHA1Encrypt(authString, AccessSecret);

        Log.i("test", "codes  HMAC=" + HMAC);
        //String token = OkHttpUtils.encode(HMAC); jerry edit
        String token = null;
        try {
            token = URLEncoder.encode(HMAC, "UTF-8");
            token = token.replaceAll("\\+", "%20");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i("test", "codes  token=" + token);
        return token;
    }

    /**
     *  1. 对参数名进行字典排序
     * @param paramMap
     * @return
     */
    public static String[] sortParam(Map<String, String> paramMap) {
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        return keyArray;
    }

    /**
     * 2.对每个请求参数的名称和值进行编码
     * @param params
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String encodeString(List<NameValuePair> params) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < params.size(); i++) {
            try {
                sb.append(URLEncoder.encode(params.get(i).getName(), "UTF-8")).append("=").append(URLEncoder.encode(params.get(i).getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (i < params.size() - 1) {
                sb.append("&");
            }
        }
        String queryString = "?" + sb.toString();
        queryString = queryString.replaceAll("\\+", "%20");
        queryString = queryString.replaceAll("\\*", "%2A");
        queryString = queryString.replaceAll("%7E", "~");
        return queryString;
    }

    /**
     * 3. 对每个请求参数的名称和值进行编码
     * @param HTTPMethod
     * @param queryString
     * @return
     */
    private static String getAuthString(String HTTPMethod, String queryString) {

        //String authString= HTTPMethod +"&"+ java.net.URLEncoder.encode("/")+"&"+java.net.URLEncoder.encode(queryString);
        return HTTPMethod + "&" + encode("/") + "&" + encode(queryString);

    }

    /**
     * 4. 获取token
     * @param authString
     * @return
     */
    public static String getToken(String authString) {

        String hamc = SHA(authString);

        return Base64.encodeToString(hamc.getBytes(), Base64.DEFAULT);

    }

    private static String SHA(String decript) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("HMACSHA1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            // 字节数组转换为 十六进制 数
            for (byte aMessageDigest : messageDigest) {
                String shaHex = Integer.toHexString(aMessageDigest & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /// <summary>
    /// 按照文档中说明的规则对字符串进行编码并返回编码后的字符串值。
    /// </summary>
    /// <param name="value">需要被编码的字符内容</param>
    /// <returns></returns>
    private static String encode(String value) {
        StringBuilder stringBuilder = new StringBuilder();
        ///byte[] bytes = Encoding.GetEncoding(ENCODING_UTF8).GetBytes(value);
        char[] cArray = value.toCharArray();
        String text = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~";

        for (char c : cArray) {
            if (text.indexOf(c) != -1) {
                stringBuilder.append(c);
            } else {
                stringBuilder.append("%").append(Integer.toHexString(0xff & c).toUpperCase());
            }
        }


        return stringBuilder.toString();
    }


    /**
     * HMAC-SHA1
     *
     * @param encryptText
     * @param encryptKey
     * @return
     * @throws Exception
     */
    private static String HmacSHA1Encrypt(String encryptText, String encryptKey) {
        byte[] data;
        byte[] str = null;
        try {
            data = encryptKey.getBytes(ENCODING);
            SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
            Mac mac = Mac.getInstance(MAC_NAME);
            mac.init(secretKey);

            byte[] text = encryptText.getBytes(ENCODING);
            str = mac.doFinal(text);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	/*String string = null;
	for (int i = 0; i < str.length; i++) {
		string+=str[i]+"-";

	}
	Log.i("test","code str="+string);
	// Create Hex String
	StringBuffer hexString = new StringBuffer();
	// 字节数组转换为 十六进制 数
	for (int i = 0; i < str.length; i++) {
	String shaHex = Integer.toHexString(str[i] & 0xFF);
	if (shaHex.length() < 2) {
	hexString.append(0);
	}
	hexString.append(shaHex);
	}*/
        //return hexString.toString();

        return Base64.encodeToString(str, Base64.NO_WRAP);

    }


    /**
     * 获取随机字符串
     *
     * @return
     */
    public static String getRandomString() {
        Date date = new Date();
        return date.getTime() + "";
    }


    public static RequestBody buildPostRequestBody(HashMap<String, String> map) {
        String json = new JSONObject(map).toString();
        DUtils.dLog("HTTP_POST ===>>> : " + json);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        DUtils.dLog("HTTP_POST ===>>> : " + requestBody.toString());
        return requestBody;
    }


    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ssfFactory;
    }

    public static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
