package nlf.plugin.weibo.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import nc.liat6.frame.util.ContentTypes;
import nc.liat6.frame.util.IOHelper;

/**
 * HTTP客户端
 * 
 * @author 6tail
 *
 */
public class HttpClient{
  public static final String BOUNDARY = "*****";

  private HttpClient(){}

  /**
   * POST请求
   * 
   * @param url URL
   * @param data 数据内容
   * @return 返回结果
   * @throws IOException
   */
  public static String post(String url,String data) throws IOException{
    HttpURLConnection conn = null;
    OutputStream out = null;
    BufferedReader in = null;
    try{
      conn = (HttpURLConnection)new URL(url).openConnection();
      conn.setDoInput(true);
      conn.setDoOutput(true);
      conn.setUseCaches(false);
      conn.setRequestMethod("POST");
      out = conn.getOutputStream();
      out.write(data.getBytes("utf-8"));
      out.flush();
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
      String line = null;
      StringBuilder sb = new StringBuilder();
      while((line = in.readLine())!=null){
        sb.append(line);
      }
      return sb.toString();
    }finally{
      IOHelper.closeQuietly(out);
      IOHelper.closeQuietly(in);
      if(null!=conn){
        conn.disconnect();
      }
    }
  }

  /**
   * 文件上传
   * 
   * @param url URL
   * @param file 文件
   * @return 返回结果
   * @throws IOException
   */
  public static String upload(String url,File file) throws IOException{
    HttpURLConnection conn = null;
    DataOutputStream out = null;
    BufferedReader in = null;
    FileInputStream fin = null;
    try{
      String fileName = file.getName();
      String suffix = fileName.substring(0,fileName.lastIndexOf(".")+1);
      conn = (HttpURLConnection)new URL(url).openConnection();
      conn.setDoInput(true);
      conn.setDoOutput(true);
      conn.setUseCaches(false);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Connection","Keep-Alive");
      conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+BOUNDARY);
      out = new DataOutputStream(conn.getOutputStream());
      out.writeBytes("--"+BOUNDARY+"\r\n");
      out.writeBytes("Content-Disposition:form-data;name=\"upload\";filename=\""+fileName+";filelength=\""+file.length()+";Content-Type=\""+ContentTypes.getContenType(suffix)+"\r\n");
      out.writeBytes("\r\n");
      fin = new FileInputStream(file);
      byte[] buffer = new byte[8192];
      int count = 0;
      while(-1!=(count = fin.read(buffer))){
        out.write(buffer,0,count);
      }
      out.writeBytes("\r\n");
      out.writeBytes("--"+BOUNDARY+"--\r\n");
      out.flush();
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
      String line = null;
      StringBuilder sb = new StringBuilder();
      while((line = in.readLine())!=null){
        sb.append(line);
      }
      return sb.toString();
    }finally{
      IOHelper.closeQuietly(fin);
      IOHelper.closeQuietly(out);
      IOHelper.closeQuietly(in);
      if(null!=conn){
        conn.disconnect();
      }
    }
  }
  
  /**
   * 文件下载
   * @param url URL
   * @param file 保存文件
   * @throws IOException
   */
  public static void download(String url,File file) throws IOException{
    URLConnection conn = null;
    InputStream in = null;
    FileOutputStream out = null;
    try{
      conn = (HttpURLConnection)new URL(url).openConnection();
      in = conn.getInputStream();
      out = new FileOutputStream(file);
      byte[] buffer = new byte[8192];
      int count = 0;
      while(-1!=(count = in.read(buffer))){
        out.write(buffer,0,count);
      }
      out.flush();
    }finally{
      IOHelper.closeQuietly(out);
      IOHelper.closeQuietly(in);
    }
  }
}