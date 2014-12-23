package nlf.plugin.weixin.msg.bean;

import nlf.plugin.weixin.msg.type.EventType;
import nlf.plugin.weixin.msg.type.MsgType;

/**
 * 抽象事件消息
 * 
 * @author 6tail
 * 
 */
public abstract class AbstractEventMsg extends AbstractMsg implements IEventMsg{
  /** 事件类型 */
  protected EventType eventType;

  public AbstractEventMsg(){
    setMsgType(MsgType.event);
  }

  public EventType getEventType(){
    return eventType;
  }

  public void setEventType(EventType eventType){
    this.eventType = eventType;
  }
}