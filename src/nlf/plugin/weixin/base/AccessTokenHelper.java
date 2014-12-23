package nlf.plugin.weixin.base;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.util.Stringer;
import nlf.plugin.weixin.base.bean.AccessToken;
import nlf.plugin.weixin.exception.WeixinException;
import nlf.plugin.weixin.util.HttpsClient;

/**
 * 获取AccessToken工具类
 * @author 6tail
 *
 */
public class AccessTokenHelper{
  public static String URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=?&secret=?";

  private AccessTokenHelper(){}

  /** 当前的令牌 */
  private static AccessToken token;

  public synchronized static AccessToken getAccessToken(String appid,String secret) throws WeixinException{
    if(null!=token){
      if(System.currentTimeMillis()>token.getCreateTime()+token.getExpiresIn()*1000){
        return token;
      }
    }
    try{
      String url = Stringer.print(URL,"?",appid,secret);
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
}