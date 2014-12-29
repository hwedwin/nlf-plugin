package nlf.plugin.weixin.user;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.util.Stringer;
import nlf.plugin.weixin.exception.WeixinException;
import nlf.plugin.weixin.user.bean.UserInfo;
import nlf.plugin.weixin.util.HttpsClient;

/**
 * 公众号用户工具类
 * @author 6tail
 *
 */
public class UserHelper{
  public static String URL_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=?&openid=?&lang=?";

  private UserHelper(){}

  /**
   * 获取用户信息
   * @param accessToken 令牌
   * @param openid 普通用户标识，对该公众帐号唯一
   * @param lang 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
   * @return 用户信息
   * @throws WeixinException
   */
  public static UserInfo getUser(String accessToken,String openid,String lang) throws WeixinException{
    try{
      String url = Stringer.print(URL_USER_INFO,"?",accessToken,openid,lang);
      String result = HttpsClient.get(url);
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errorcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      UserInfo u = new UserInfo();
      u.setSubscribe(o.getInt("subscribe",0));
      u.setOpenid(o.getString("openid"));
      u.setNickname(o.getString("nickname"));
      u.setSex(o.getInt("sex",0));
      u.setProvince(o.getString("province"));
      u.setCity(o.getString("city"));
      u.setCountry(o.getString("country"));
      u.setLanguage(o.getString("language"));
      u.setHeadimgurl(o.getString("headimgurl"));
      u.setSubscribeTime(o.getLong("subscribe_time",0));
      u.setUnionid(o.getString("unionid"));
      return u;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}