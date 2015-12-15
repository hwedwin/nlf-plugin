package nlf.plugin.weixin.msg.bean.impl;

import nlf.plugin.weixin.msg.bean.AbstractEventMsg;
import nlf.plugin.weixin.msg.type.EventType;

/**
 * 多客服接入会话
 * 
 * @author 6tail
 * 
 */
public class KfCreateSessionEventMsg extends AbstractEventMsg{
  /** 客服账号 */
  private String kfAccount;

  public KfCreateSessionEventMsg(){
    setEventType(EventType.kf_create_session);
  }

  /**
   * 获取客服账号
   * @return 客服账号
   */
  public String getKfAccount(){
    return kfAccount;
  }

  /**
   * 设置客服账号
   * @param kfAccount 客服账号
   */
  public void setKfAccount(String kfAccount){
    this.kfAccount = kfAccount;
  }
}