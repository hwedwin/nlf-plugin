package nlf.plugin.weixin.message;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import nlf.plugin.weixin.exception.WeixinException;
import nlf.plugin.weixin.message.bean.Message;
import nlf.plugin.weixin.util.HttpsClient;

/**
 * 模板消息工具类
 * 
 * @author 6tail
 *
 */
public class MessageHelper{
  /** 发送模板消息的URL */
  public static String URL_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=?";

  protected MessageHelper(){}

  /**
   * 发送模板消息
   * 
   * @param accessToken
   * @param message
   * @return 消息ID
   * @see nlf.plugin.weixin.base.BaseHelper
   * @see nlf.plugin.weixin.base.AccessToken
   * @throws WeixinException
   */
  public static String send(String accessToken,Message message) throws WeixinException{
    try{
      String url = Stringer.print(URL_SEND,"?",accessToken);
      Bean b = new Bean();
      b.set("touser",message.getToUser());
      b.set("template_id",message.getTemplateId());
      b.set("url",message.getUrl());
      b.set("topcolor",message.getTopColor());
      Bean d = new Bean();
      d.set("first",message.getFirst());
      for(String key:message.getDatas().keySet()){
        d.set(key,message.getDatas().get(key));
      }
      d.set("remark",message.getRemark());
      b.set("data",d);
      String data = JSON.toJson(b);
      Logger.getLog().debug(L.get("nlf.plugin.weixin.send")+data);
      String result = HttpsClient.post(url,data);
      Logger.getLog().debug(L.get("nlf.plugin.weixin.recv")+result);
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg")+" template_id="+o.getString("template_id"));
      }
      return o.getString("msgid");
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}