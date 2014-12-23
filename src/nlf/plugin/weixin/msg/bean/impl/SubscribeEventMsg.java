package nlf.plugin.weixin.msg.bean.impl;

import nlf.plugin.weixin.msg.bean.AbstractEventMsg;
import nlf.plugin.weixin.msg.type.EventType;

/**
 * 用户关注事件
 * 
 * @author 6tail
 *
 */
public class SubscribeEventMsg extends AbstractEventMsg{
  private String eventKey;
  private String ticket;

  public SubscribeEventMsg(){
    setEventType(EventType.subscribe);
  }

  public String getEventKey(){
    return eventKey;
  }

  public void setEventKey(String eventKey){
    this.eventKey = eventKey;
  }

  public String getTicket(){
    return ticket;
  }

  public void setTicket(String ticket){
    this.ticket = ticket;
  }
}
