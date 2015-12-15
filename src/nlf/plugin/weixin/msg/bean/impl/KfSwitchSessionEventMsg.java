package nlf.plugin.weixin.msg.bean.impl;

import nlf.plugin.weixin.msg.bean.AbstractEventMsg;
import nlf.plugin.weixin.msg.type.EventType;

/**
 * 多客服转接会话
 * 
 * @author 6tail
 * 
 */
public class KfSwitchSessionEventMsg extends AbstractEventMsg{
  /** 原客服账号 */
  private String fromKfAccount;
  /** 新客服账号 */
  private String toKfAccount;

  public KfSwitchSessionEventMsg(){
    setEventType(EventType.kf_switch_session);
  }

  public String getFromKfAccount(){
    return fromKfAccount;
  }

  public void setFromKfAccount(String fromKfAccount){
    this.fromKfAccount = fromKfAccount;
  }

  public String getToKfAccount(){
    return toKfAccount;
  }

  public void setToKfAccount(String toKfAccount){
    this.toKfAccount = toKfAccount;
  }
}