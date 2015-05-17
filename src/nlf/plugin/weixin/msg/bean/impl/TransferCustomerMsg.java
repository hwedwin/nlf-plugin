package nlf.plugin.weixin.msg.bean.impl;

import nlf.plugin.weixin.msg.bean.AbstractMsg;
import nlf.plugin.weixin.msg.bean.IRequestMsg;
import nlf.plugin.weixin.msg.bean.IResponseMsg;
import nlf.plugin.weixin.msg.type.MsgType;

/**
 * 将用户消息转发到多客服
 * 
 * @author 6tail
 * 
 */
public class TransferCustomerMsg extends AbstractMsg implements IRequestMsg,IResponseMsg{
  /** 指定客服账号，如果为null，则不指定 */
  private String kfAccount;

  public TransferCustomerMsg(){
    setMsgType(MsgType.transfer_customer_service);
  }

  public String getKfAccount(){
    return kfAccount;
  }

  public void setKfAccount(String kfAccount){
    this.kfAccount = kfAccount;
  }
}