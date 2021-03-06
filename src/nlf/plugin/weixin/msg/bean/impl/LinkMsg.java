﻿package nlf.plugin.weixin.msg.bean.impl;

import nlf.plugin.weixin.msg.bean.AbstractMsg;
import nlf.plugin.weixin.msg.bean.IRequestMsg;
import nlf.plugin.weixin.msg.type.MsgType;

/**
 * 链接消息
 * 
 * @author 6tail
 * 
 */
public class LinkMsg extends AbstractMsg implements IRequestMsg{
  /** 标题 */
  private String title;
  /** 描述 */
  private String description;
  /** URL */
  private String url;
  /** 消息id */
  private String msgId;

  public LinkMsg(){
    setMsgType(MsgType.link);
  }

  public String getTitle(){
    return title;
  }

  public void setTitle(String title){
    this.title = title;
  }

  public String getDescription(){
    return description;
  }

  public void setDescription(String description){
    this.description = description;
  }

  public String getUrl(){
    return url;
  }

  public void setUrl(String url){
    this.url = url;
  }

  public String getMsgId(){
    return msgId;
  }

  public void setMsgId(String msgId){
    this.msgId = msgId;
  }
}
