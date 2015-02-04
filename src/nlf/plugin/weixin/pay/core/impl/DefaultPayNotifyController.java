package nlf.plugin.weixin.pay.core.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nlf.plugin.weixin.pay.bean.PayNotifyRequest;
import nlf.plugin.weixin.pay.core.IPayNotifyController;
import nlf.plugin.weixin.pay.core.IPayNotifyHandler;
import nlf.plugin.weixin.pay.core.IPayNotifyResolver;

/**
 * 微信支付通知控制器
 * 
 * @author 6tail
 *
 */
public class DefaultPayNotifyController implements IPayNotifyController{
  /** 通知解析接口 */
  protected IPayNotifyResolver resolver;

  /**
   * 构造控制器
   * 
   */
  public DefaultPayNotifyController(){
    this(new DefaultPayNotifyResolver());
  }

  /**
   * 构造控制器
   * 
   * @param resolver 通知解析器
   */
  public DefaultPayNotifyController(IPayNotifyResolver resolver){
    this.resolver = resolver;
  }

  public String handle(IPayNotifyHandler handler){
    try{
      Request r = Context.get(Statics.REQUEST);
      HttpServletRequest or = r.find(Statics.FIND_REQUEST);
      BufferedReader br = new BufferedReader(new InputStreamReader(or.getInputStream(),"utf-8"));
      StringBuilder s = new StringBuilder();
      String line = "";
      while(null!=(line = br.readLine())){
        s.append(line);
      }
      Logger.getLog().debug(L.get("nlf.plugin.weixin.request")+s);
      PayNotifyRequest req = resolver.decode(s.toString());
      String responseStr = resolver.encode(handler.onHandle(req));
      Logger.getLog().debug(L.get("nlf.plugin.weixin.response")+responseStr);
      return responseStr;
    }catch(Throwable e){
      Logger.getLog().error(L.get("nlf.plugin.weixin.error"),e);
    }
    return "";
  }
}