package nlf.plugin.weixin.pay.core.impl;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.util.Stringer;
import nc.liat6.frame.xml.XML;
import nlf.plugin.weixin.pay.bean.PayNotifyRequest;
import nlf.plugin.weixin.pay.bean.PayNotifyResponse;
import nlf.plugin.weixin.pay.core.IPayNotifyResolver;

/**
 * 默认微信支付通知解析器
 * 
 * @author 6tail
 *
 */
public class DefaultPayNotifyResolver implements IPayNotifyResolver{
  
  public PayNotifyRequest decode(String str){
    Bean o = XML.toBean(str);
    PayNotifyRequest req = o.toObject(PayNotifyRequest.class);
    return req;
  }


  public String encode(PayNotifyResponse msg){
    if(null==msg){
      return "";
    }
    String data = XML.toXML(msg,true,"xml",false);
    data = Stringer.cut(data,">");
    return data;
  }
}