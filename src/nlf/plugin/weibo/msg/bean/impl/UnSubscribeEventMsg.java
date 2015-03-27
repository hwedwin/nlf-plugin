package nlf.plugin.weibo.msg.bean.impl;

import nlf.plugin.weibo.msg.bean.AbstractEventMsg;
import nlf.plugin.weibo.msg.type.EventType;

/**
 * 微博用户取消订阅事件
 * 
 * @author 6tail
 *
 */
public class UnSubscribeEventMsg extends AbstractEventMsg{
  public UnSubscribeEventMsg(){
    setEventType(EventType.unsubscribe);
  }
}
