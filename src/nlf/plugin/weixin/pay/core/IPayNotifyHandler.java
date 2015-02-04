package nlf.plugin.weixin.pay.core;

import nlf.plugin.weixin.pay.bean.PayNotifyRequest;
import nlf.plugin.weixin.pay.bean.PayNotifyResponse;

/**
 * 微信支付通知处理接口
 * 
 * @author 6tail
 *
 */
public interface IPayNotifyHandler{
  PayNotifyResponse onHandle(PayNotifyRequest request);
}