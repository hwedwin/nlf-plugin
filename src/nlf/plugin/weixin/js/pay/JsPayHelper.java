package nlf.plugin.weixin.js.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.entity.IBeanRule;
import nc.liat6.frame.util.Stringer;
import nlf.plugin.weixin.js.bean.JsConfig;
import nlf.plugin.weixin.js.bean.PayPackage;
import nlf.plugin.weixin.js.bean.adapter.PayPackageAdapter;

/**
 * 微信公众号中网页JS调用支付API的辅助工具
 * 
 * @author 6tail
 *
 */
public class JsPayHelper{
  protected JsPayHelper(){}

  protected static IBeanRule rulePayPackage = new PayPackageAdapter();

  /**
   * 签名jsapi配置
   * @param cfg
   * @return 签名signature
   * @throws NoSuchAlgorithmException
   */
  public static String signConfig(JsConfig cfg) throws NoSuchAlgorithmException{
    StringBuffer s = new StringBuffer();
    s.append("jsapi_ticket=");
    s.append(cfg.getTicket());
    s.append("&noncestr=");
    s.append(cfg.getNoncestr());
    s.append("&timestamp=");
    s.append(cfg.getTimestamp());
    s.append("&url=");
    s.append(cfg.getUrl());
    return sha1(s.toString());
  }

  /**
   * 签名package数据段
   * 
   * @param pkg
   * @param paternerKey
   * @return 签名
   * @throws NoSuchAlgorithmException
   */
  public static String signPayPackage(PayPackage pkg,String paternerKey) throws NoSuchAlgorithmException{
    Bean o = new Bean();
    o.fromObject(pkg,rulePayPackage);
    List<String> args = new ArrayList<String>();
    Iterator<String> it = o.keySet().iterator();
    String key;
    while(it.hasNext()){
      key = it.next();
      if(o.getString(key,"").length()>0){
        args.add(key);
      }
    }
    Collections.sort(args);
    for(int i = 0,j = args.size();i<j;i++){
      key = args.get(i);
      args.set(i,key+"="+o.getString(key));
    }
    args.add("key="+paternerKey);
    return Stringer.md5(Stringer.join(args,"&"));
  }

  /**
   * 生成package数据段
   * 
   * @param pkg package封装
   * @return package数据段
   * @throws UnsupportedEncodingException
   */
  public static String generatePayPackageData(PayPackage pkg) throws UnsupportedEncodingException{
    Bean o = new Bean();
    o.fromObject(pkg,rulePayPackage);
    List<String> args = new ArrayList<String>();
    Iterator<String> it = o.keySet().iterator();
    String key;
    while(it.hasNext()){
      key = it.next();
      if(o.getString(key,"").length()>0){
        args.add(key);
      }
    }
    Collections.sort(args);
    for(int i = 0,j = args.size();i<j;i++){
      key = args.get(i);
      args.set(i,key+"="+URLEncoder.encode(o.getString(key),pkg.getInputCharset()));
    }
    return Stringer.join(args,"&");
  }

  /**
   * 生成带签名的完整package
   * 
   * @param pkg package封装
   * @param paternerKey 商户密钥
   * @return 已签名的package字符串
   * @throws NoSuchAlgorithmException
   * @throws UnsupportedEncodingException
   */
  public static String generatePayPackage(PayPackage pkg,String paternerKey) throws NoSuchAlgorithmException,UnsupportedEncodingException{
    String data = generatePayPackageData(pkg);
    String sign = signPayPackage(pkg,paternerKey);
    return data+"&sign="+sign;
  }

  /**
   * sha1
   * 
   * @param s 原字符串
   * @return 结果字符串
   * @throws NoSuchAlgorithmException
   */
  private static String sha1(String s) throws NoSuchAlgorithmException{
    MessageDigest md = MessageDigest.getInstance("SHA-1");
    md.update(s.getBytes());
    byte[] b = md.digest();
    StringBuilder sb = new StringBuilder();
    for(int i = 0;i<b.length;i++){
      String hex = Integer.toHexString(b[i]&0xFF);
      hex = (hex.length()==1?"0":"")+hex;
      sb.append(hex);
    }
    return sb.toString();
  }
}