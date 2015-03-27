package nlf.plugin.weibo.msg.bean;

import nlf.plugin.weibo.msg.type.EventType;

/**
 * 微博事件消息接口
 * 
 * @author 6tail
 *
 */
public interface IEventMsg extends IRequestMsg{
  /**
   * 设置事件类型
   * 
   * @param msgType 事件类型
   */
  public void setEventType(EventType msgType);

  /**
   * 获取事件类型
   * 
   * @return 事件类型
   */
  public EventType getEventType();
}