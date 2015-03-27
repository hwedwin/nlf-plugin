package nlf.plugin.weibo.msg.bean.impl;

import nlf.plugin.weibo.msg.bean.AbstractMsg;
import nlf.plugin.weibo.msg.bean.IRequestMsg;
import nlf.plugin.weibo.msg.bean.IResponseMsg;
import nlf.plugin.weibo.msg.type.MsgType;

/**
 * @ 消息
 * 
 * @author 6tail
 * 
 */
public class MentionMsg extends AbstractMsg implements IRequestMsg,IResponseMsg{
  /** 消息内容 */
  private String content;
  private String subtype;
  private String key;

  public MentionMsg(){
    setMsgType(MsgType.mention);
  }

  public String getSubtype(){
    return subtype;
  }

  public void setSubtype(String subtype){
    this.subtype = subtype;
  }

  public String getKey(){
    return key;
  }

  public void setKey(String key){
    this.key = key;
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