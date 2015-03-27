package nlf.plugin.weibo.msg.bean.impl;

import nlf.plugin.weibo.msg.bean.AbstractMsg;
import nlf.plugin.weibo.msg.bean.IRequestMsg;
import nlf.plugin.weibo.msg.bean.IResponseMsg;
import nlf.plugin.weibo.msg.type.MsgType;

/**
 * 微博文本消息
 * 
 * @author 6tail
 * 
 */
public class TextMsg extends AbstractMsg implements IRequestMsg,IResponseMsg{
  /** 消息内容 */
  private String content;

  public TextMsg(){
    setMsgType(MsgType.text);
  }

  /**
   * 获取内容
   * 
   * @return 内容
   */
  public String getContent(){
    return content;
  }

  /**
   * 设置内容
   * 
   * @param content 内容
   */
  public void setContent(String content){
    this.content = content;
  }
}