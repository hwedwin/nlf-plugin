package nlf.plugin.weixin.msg.bean.impl;

import nlf.plugin.weixin.msg.bean.AbstractEventMsg;
import nlf.plugin.weixin.msg.type.EventType;

/**
 * 用户取消关注事件
 * 
 * @author 6tail
 *
 */
public class UnSubscribeEventMsg extends AbstractEventMsg{
  public UnSubscribeEventMsg(){
    setEventType(EventType.unsubscribe);
  }
}
