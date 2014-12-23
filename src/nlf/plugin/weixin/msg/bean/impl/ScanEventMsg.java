package nlf.plugin.weixin.msg.bean.impl;

import nlf.plugin.weixin.msg.bean.AbstractEventMsg;
import nlf.plugin.weixin.msg.type.EventType;

/**
 * 二维码扫描事件
 * 
 * @author 6tail
 *
 */
public class ScanEventMsg extends AbstractEventMsg{
  private String eventKey;
  private String ticket;

  public ScanEventMsg(){
    setEventType(EventType.scan);
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
