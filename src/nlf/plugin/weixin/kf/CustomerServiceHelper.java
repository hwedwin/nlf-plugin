package nlf.plugin.weixin.kf;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import nlf.plugin.weixin.exception.WeixinException;
import nlf.plugin.weixin.util.HttpsClient;

public class CustomerServiceHelper{
  /** 发送客服消息的URL */
  public static String URL_SEND = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=?";

  protected CustomerServiceHelper(){}

  public static void sendText(String accessToken,String openid,String text) throws WeixinException{
    try{
      String url = Stringer.print(URL_SEND,"?",accessToken);
      Bean b = new Bean();
      b.set("touser",openid);
      b.set("msgtype","text");
      b.set("text",new Bean().set("content",text));
      String data = JSON.toJson(b);
      Logger.getLog().debug(L.get("nlf.plugin.weixin.send")+data);
      String result = HttpsClient.post(url,data);
      Logger.getLog().debug(L.get("nlf.plugin.weixin.recv")+result);
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}