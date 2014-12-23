package nlf.plugin.weixin.msg.bean.impl;

import nlf.plugin.weixin.msg.bean.AbstractEventMsg;
import nlf.plugin.weixin.msg.type.EventType;

/**
 * 菜单点击跳转链接事件
 * 
 * @author 6tail
 * 
 */
public class ViewEventMsg extends AbstractEventMsg{
  /** 事件KEY值，即设置的跳转URL */
  private String eventKey;

  public ViewEventMsg(){
    setEventType(EventType.VIEW);
  }

  /**
   * 获取事件KEY值，即设置的跳转URL
   * 
   * @return 事件KEY值，即设置的跳转URL
   */
  public String getEventKey(){
    return eventKey;
  }

  /**
   * 设置事件KEY值，即设置的跳转URL
   * 
   * @param eventKey 事件KEY值，即设置的跳转URL
   */
  public void setEventKey(String eventKey){
    this.eventKey = eventKey;
  }
}