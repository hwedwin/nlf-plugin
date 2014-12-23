package sample;

import nlf.plugin.weixin.msg.bean.IResponseMsg;
import nlf.plugin.weixin.msg.bean.impl.SubscribeEventMsg;
import nlf.plugin.weixin.msg.bean.impl.TextMsg;
import nlf.plugin.weixin.msg.core.impl.DefaultMsgHandler;
import nlf.plugin.weixin.msg.core.impl.DefaultMsgController;

/**
 * 接收公众号请求的控制器
 * @author 6tail
 *
 */
public class WeixinApi{
  /** 微信公众号中设置的令牌 */
  public static final String TOKEN = "";

  /**
   * 处理微信公众号服务器发来的请求，对应公众号开发者中心中设置的url
   * 
   * @return 响应消息内容
   */
  public String doRequest(){
    return new DefaultMsgController(TOKEN).handle(new DefaultMsgHandler(){
      @Override
      public IResponseMsg onText(TextMsg msg){
        TextMsg tm = new TextMsg();
        tm.setToUser(msg.getFromUser());
        tm.setFromUser(msg.getToUser());
        tm.setContent("Hello world!");
        return tm;
      }
      
      @Override
      public IResponseMsg onSubscribe(SubscribeEventMsg event){
        TextMsg tm = new TextMsg();
        tm.setToUser(event.getFromUser());
        tm.setFromUser(event.getToUser());
        tm.setContent("你卖啥萌？");
        return tm;
      }
    });
  }
}