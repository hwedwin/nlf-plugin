package nlf.plugin.weibo.msg.bean;

import nlf.plugin.weibo.msg.type.MsgType;

/**
 * 微博抽象消息
 * 
 * @author 6tail
 * 
 */
public abstract class AbstractMsg implements IMsg{
  /** 消息类型 */
  private MsgType msgType;
  /** 发送者 */
  private String fromUser;
  /** 接收者 */
  private String toUser;
  /** 创建时间 */
  private String createTime;

  public MsgType getMsgType(){
    return msgType;
  }

  public void setMsgType(MsgType msgType){
    this.msgType = msgType;
  }

  public String getFromUser(){
    return fromUser;
  }

  public void setFromUser(String fromUser){
    this.fromUser = fromUser;
  }

  public String getToUser(){
    return toUser;
  }

  public void setToUser(String toUser){
    this.toUser = toUser;
  }

  public String getCreateTime(){
    return createTime;
  }

  public void setCreateTime(String createTime){
    this.createTime = createTime;
  }
}
