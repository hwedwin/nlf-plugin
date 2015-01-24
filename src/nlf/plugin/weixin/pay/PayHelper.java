package nlf.plugin.weixin.pay;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import nc.liat6.frame.xml.XML;
import nlf.plugin.weixin.exception.WeixinException;
import nlf.plugin.weixin.pay.bean.PrePayResult;
import nlf.plugin.weixin.pay.bean.UnifiedOrder;
import nlf.plugin.weixin.util.HttpsClient;

/**
 * 支付工具类
 * 
 * @author 6tail
 *
 */
public class PayHelper{
  public static String URL_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";

  protected PayHelper(){}

  /**
   * 签名
   * 
   * @param order 未签名的预支付单
   * @param secret 密钥
   * @return MD5签名
   * @throws NoSuchAlgorithmException
   */
  public static String sign(UnifiedOrder order,String secret) throws NoSuchAlgorithmException{
    Bean o = new Bean();
    o.fromObject(order);
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
    args.add("key="+secret);
    String s = Stringer.join(args,"&");
    Logger.getLog().debug(L.get("nlf.plugin.weixin.sign_data")+s);
    s = Stringer.md5(s);
    Logger.getLog().debug(L.get("nlf.plugin.weixin.sign_result")+s);
    return s;
  }

  /**
   * 预支付
   * 
   * @param signedOrder 已前面的预支付单
   * @return 预支付结果
   * @throws WeixinException
   */
  public static PrePayResult prePay(UnifiedOrder signedOrder) throws WeixinException{
    try{
      Bean bean = new Bean();
      bean.fromObject(signedOrder);
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
      bean.remove("key");
      String data = XML.toXML(bean,true,"xml",false);
      data = Stringer.cut(data,">");
      Logger.getLog().debug(L.get("nlf.plugin.weixin.send")+data);
      String result = HttpsClient.post(URL_ORDER,data);
      Logger.getLog().debug(L.get("nlf.plugin.weixin.recv")+result);
      Bean o = XML.toBean(result);
      PrePayResult r = new PrePayResult();
      r.setReturn_code(o.getString("return_code"));
      r.setReturn_msg(o.getString("return_msg"));
      if("SUCCESS".equals(r.getReturn_code())){
        r.setAppid(o.getString("appid"));
        r.setMch_id(o.getString("mch_id"));
        r.setDevice_info(o.getString("device_info"));
        r.setNonce_str(o.getString("nonce_str"));
        r.setSign(o.getString("sign"));
        r.setResult_code(o.getString("result_code"));
        r.setErr_code(o.getString("err_code"));
        r.setErr_code_des(o.getString("err_code_des"));
        if("SUCCESS".equals(r.getResult_code())){
          r.setTrade_type(o.getString("trade_type"));
          r.setPrepay_id(o.getString("prepay_id"));
          r.setCode_url(o.getString("code_url"));
        }
      }
      return r;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}