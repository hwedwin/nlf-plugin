package nlf.plugin.weixin.pay.core.impl;

import nlf.plugin.weixin.pay.bean.PayNotifyRequest;
import nlf.plugin.weixin.pay.bean.PayNotifyResponse;
import nlf.plugin.weixin.pay.core.IPayNotifyHandler;

/**
 * 抽象微信支付通知处理者
 * 
 * @author 6tail
 *
 */
public abstract class DefaulPayNotifyHandler implements IPayNotifyHandler{
  public PayNotifyResponse onHandle(PayNotifyRequest request){
    return null;
  }
  
}