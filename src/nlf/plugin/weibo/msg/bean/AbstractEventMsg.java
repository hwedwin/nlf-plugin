package nlf.plugin.weibo.msg.bean;

import nlf.plugin.weibo.msg.type.EventType;
import nlf.plugin.weibo.msg.type.MsgType;

/**
 * 微博抽象事件消息
 * 
 * @author 6tail
 * 
 */
public abstract class AbstractEventMsg extends AbstractMsg implements IEventMsg{
  /** 事件类型 */
  private EventType eventType;

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