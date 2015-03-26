package nlf.plugin.weixin.pay;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import nc.liat6.frame.xml.XML;
import nlf.plugin.weixin.exception.WeixinException;
import nlf.plugin.weixin.pay.bean.RefundRequest;
import nlf.plugin.weixin.pay.bean.RefundResponse;
import nlf.plugin.weixin.util.HttpsClient;

/**
 * 退款工具类
 * 
 * @author 6tail
 *
 */
public class RefundHelper{
  /** 退款申请URL */
  public static String URL_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";
  
  protected RefundHelper(){}

  /**
   * 签名
   * 
   * @param refundRequest 退款申请
   * @param paySecret 密钥
   * @return MD5签名
   * @throws NoSuchAlgorithmException
   */
  public static String sign(RefundRequest refundRequest,String paySecret) throws NoSuchAlgorithmException{
    Bean o = new Bean();
    o.fromObject(refundRequest);
    o.remove("sign");
    SortedMap<String,String> map = new TreeMap<String,String>();
    for(String key:o.keySet()){
      String value = o.getString(key,"");
      if(value.length()>0){
        map.put(key,o.getString(key));
      }
    }
    StringBuffer sb = new StringBuffer();
    for(String key:map.keySet()){
      sb.append(key);
      sb.append("=");
      sb.append(map.get(key));
      sb.append("&");
    }
    sb.append("key=");
    sb.append(paySecret);
    String s = sb.toString();
    Logger.getLog().debug(L.get("nlf.plugin.weixin.sign_data")+s);
    s = Stringer.md5(s);
    Logger.getLog().debug(L.get("nlf.plugin.weixin.sign_result")+s);
    return s;
  }

  /**
   * 退款申请
   * 
   * @param refundRequest 已签名的退款申请
   * @return 退款申请反馈
   * @throws WeixinException
   */
  public static RefundResponse refund(RefundRequest refundRequest,File certFile) throws WeixinException{
    try{
      Bean bean = new Bean();
      bean.fromObject(refundRequest);
      List<String> args = new ArrayList<String>();
      Iterator<String> it = bean.keySet().iterator();
      String key;
      while(it.hasNext()){
        key = it.next();
        if(bean.getString(key,"").length()<1){
          args.add(key);
        }
      }
      for(String k:args){
        bean.remove(k);
      }
      String data = XML.toXML(bean,true,"xml",false);
      data = Stringer.cut(data,">");
      Logger.getLog().debug(L.get("nlf.plugin.weixin.send")+data);
      String result = HttpsClient.post(certFile,refundRequest.getMch_id(),URL_REFUND,data);
      Logger.getLog().debug(L.get("nlf.plugin.weixin.recv")+result);
      Bean o = XML.toBean(result);
      RefundResponse r = o.toObject(RefundResponse.class);
      return r;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}