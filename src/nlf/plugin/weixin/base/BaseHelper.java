package nlf.plugin.weixin.base;

import java.util.List;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.util.Stringer;
import nlf.plugin.weixin.base.bean.AccessToken;
import nlf.plugin.weixin.exception.WeixinException;
import nlf.plugin.weixin.util.HttpsClient;

/**
 * 获取AccessToken工具类
 * 
 * @author 6tail
 *
 */
public class BaseHelper{
  public static String URL_GET_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=?&secret=?";
  public static String URL_GET_SERVER_IPS = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=?";

  private BaseHelper(){}

  /** 当前的令牌 */
  private static AccessToken token;

  /**
   * 获取令牌
   * 
   * @param appid 第三方用户唯一凭证
   * @param secret 第三方用户唯一凭证密钥，即appsecret
   * @return 令牌
   * @throws WeixinException
   */
  public synchronized static AccessToken getAccessToken(String appid,String secret) throws WeixinException{
    if(null!=token){
      if(System.currentTimeMillis()>token.getCreateTime()+token.getExpiresIn()*1000){
        return token;
      }
    }
    try{
      String url = Stringer.print(URL_GET_TOKEN,"?",appid,secret);
      String result = HttpsClient.get(url);
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errorcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      if(null==token){
        token = new AccessToken();
      }
      token.setCreateTime(System.currentTimeMillis());
      token.setToken(o.getString("access_token"));
      token.setExpiresIn(o.getInt("expires_in",7200));
      return token;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }

  /**
   * 获取微信服务器IP列表
   * 
   * @param accessToken 令牌
   * @return 服务器IP列表
   * @throws WeixinException
   */
  public static List<String> getServerIps(String accessToken) throws WeixinException{
    try{
      String url = Stringer.print(URL_GET_SERVER_IPS,"?",accessToken);
      String result = HttpsClient.get(url);
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errorcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      List<String> l = o.get("ip_list");
      return l;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}