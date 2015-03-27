package nlf.plugin.weibo.msg.bean;

import nlf.plugin.weibo.msg.type.MsgType;

/**
 * 微博消息接口
 * 
 * @author 6tail
 *
 */
public interface IMsg{
  /**
   * 设置消息类型
   * 
   * @param msgType 消息类型
   */
  public void setMsgType(MsgType msgType);

  /**
   * 获取消息类型
   * 
   * @return
   */
  public MsgType getMsgType();

  /**
   * 获取发送者
   * 
   * @return 发送者
   */
  public String getFromUser();

  /**
   * 设置发送者
   * 
   * @param fromUser 发送者
   */
  public void setFromUser(String fromUser);

  /**
   * 获取接收者
   * 
   * @return 接收者
   */
  public String getToUser();

  /**
   * 设置接收者
   * 
   * @param toUser 接收者
   */
  public void setToUser(String toUser);

  /**
   * 获取创建时间
   * 
   * @return 创建时间
   */
  public String getCreateTime();

  /**
   * 设置创建时间
   * 
   * @param createTime 创建时间
   */
  public void setCreateTime(String createTime);
}