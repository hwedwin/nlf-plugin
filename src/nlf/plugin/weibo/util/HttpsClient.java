package nlf.plugin.weibo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * HTTPS客户端
 * 
 * @author 6tail
 *
 */
public class HttpsClient{
  private HttpsClient(){}

  private static TrustManager trustManager = new X509TrustManager(){
    public void checkClientTrusted(X509Certificate[] chain,String authType) throws CertificateException{}

    public void checkServerTrusted(X509Certificate[] chain,String authType) throws CertificateException{}

    public X509Certificate[] getAcceptedIssuers(){
      return null;
    }
  };

  /**
   * GET请求
   * 
   * @param url URL
   * @return 返回结果
   * @throws IOException
   * @throws NoSuchAlgorithmException
   * @throws KeyManagementException
   */
  public static String get(String url) throws IOException,NoSuchAlgorithmException,KeyManagementException{
    SSLContext ssl = SSLContext.getInstance("TLS");
    ssl.init(null,new TrustManager[]{trustManager},null);
    HttpsURLConnection conn = (HttpsURLConnection)new URL(url).openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("GET");
    conn.setSSLSocketFactory(ssl.getSocketFactory());
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
    String line = null;
    StringBuilder sb = new StringBuilder();
    while((line = in.readLine())!=null){
      sb.append(line);
    }
    conn.disconnect();
    return sb.toString();
  }

  /**
   * POST请求
   * 
   * @param url URL
   * @param data 数据内容
   * @return 返回结果
   * @throws IOException
   * @throws NoSuchAlgorithmException
   * @throws KeyManagementException
   */
  public static String post(String url,String data) throws IOException,NoSuchAlgorithmException,KeyManagementException{
    SSLContext ssl = SSLContext.getInstance("TLS");
    ssl.init(null,new TrustManager[]{trustManager},null);
    HttpsURLConnection conn = (HttpsURLConnection)new URL(url).openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setSSLSocketFactory(ssl.getSocketFactory());
    OutputStream out = conn.getOutputStream();
    out.write(data.getBytes("utf-8"));
    out.flush();
    out.close();
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
    String line = null;
    StringBuilder sb = new StringBuilder();
    while((line = in.readLine())!=null){
      sb.append(line);
    }
    conn.disconnect();
    return sb.toString();
  }
}