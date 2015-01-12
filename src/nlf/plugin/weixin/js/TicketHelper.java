package nlf.plugin.weixin.js;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import nlf.plugin.weixin.exception.WeixinException;
import nlf.plugin.weixin.js.bean.Ticket;
import nlf.plugin.weixin.util.HttpsClient;

/**
 * Ticket工具
 * 
 * @author 6tail
 *
 */
public class TicketHelper{
  /** JSAPI获取ticket的URL */
  public static String URL_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=?&type=jsapi";

  protected TicketHelper(){}

  /**
   * 获取jsapi的ticket
   * 
   * @param accessToken
   * @see nlf.plugin.weixin.base.BaseHelper
   * @see nlf.plugin.weixin.base.AccessToken
   * @throws WeixinException
   */
  public static Ticket getTicket4JsApi(String accessToken) throws WeixinException{
    try{
      String url = Stringer.print(URL_JSAPI_TICKET,"?",accessToken);
      String result = HttpsClient.get(url);
      Logger.getLog().debug(L.get("nlf.plugin.weixin.recv")+result);
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      Ticket ticket = new Ticket();
      ticket.setTicket(o.getString("ticket"));
      ticket.setExpiresIn(o.getInt("expires_in",7200));
      ticket.setCreateTime(System.currentTimeMillis());
      return ticket;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}