package nlf.plugin.weibo.msg.bean.impl;

import nlf.plugin.weibo.msg.bean.AbstractEventMsg;
import nlf.plugin.weibo.msg.type.EventType;

/**
 * 微博点击菜单事件
 * 
 * @author 6tail
 *
 */
public class ClickEventMsg extends AbstractEventMsg{
  protected String key;

  public String getKey(){
    return key;
  }

  public void setKey(String key){
    this.key = key;
  }

  public ClickEventMsg(){
    setEventType(EventType.click);
  }
}