package nlf.plugin.weixin.js;

import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;
import java.util.TreeMap;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import nlf.plugin.weixin.base.BaseHelper;
import nlf.plugin.weixin.js.bean.JsConfig;
import nlf.plugin.weixin.js.bean.JsPayConfig;

/**
 * JSAPI工具类
 * @author 6tail
 *
 */
public class JsHelper{
  protected JsHelper(){}

  /**
   * 签名设置
   * @param cfg
   * @return
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
    return BaseHelper.sha1(s.toString());
  }

  /**
   * 支付签名
   * @param payConfig 支付设置
   * @param partnerKey 商户密钥
   * @return
   * @throws NoSuchAlgorithmException
   */
  public static String paySign(JsPayConfig payConfig,String partnerKey) throws NoSuchAlgorithmException{
    SortedMap<String,String> map = new TreeMap<String,String>();
    Bean o = new Bean();
    //将payConfig对象转换为key-value键值对封装
    o.fromObject(payConfig);
    //将pkg值转到package
    o.set("package",o.get("pkg"));
    //移除pkg键
    o.remove("pkg");
    //移除paySign键
    o.remove("paySign");
    //检查为空的不参与签名
    for(String key:o.keySet()){
      String value = o.getString(key,"");
      if(value.length()>0){
        map.put(key,o.getString(key));
      }
    }
    //拼接待签名串
    StringBuffer sb = new StringBuffer();
    for(String key:map.keySet()){
      sb.append(key);
      sb.append("=");
      sb.append(map.get(key));
      sb.append("&");
    }
    //最后拼接key
    sb.append("key=");
    sb.append(partnerKey);
    String s = sb.toString();
    Logger.getLog().debug(L.get("nlf.plugin.weixin.sign_data")+s);
    //MD5并大写
    s = Stringer.md5(s);
    Logger.getLog().debug(L.get("nlf.plugin.weixin.sign_result")+s);
    return s;
  }
}