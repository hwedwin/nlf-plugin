package sample;

import nlf.plugin.weixin.base.AccessTokenHelper;
import nlf.plugin.weixin.base.bean.AccessToken;
import nlf.plugin.weixin.exception.WeixinException;

/**
 * 二维码示例
 * @author 6tail
 *
 */
public class AccessTokenSample{
  public static void main(String[] args) throws WeixinException{
    //appid，在公众号开发者中心找
    String appid = "";
    //secret，在公众号开发者中心找
    String secret = "";
    //换取access token
    AccessToken accessToken = AccessTokenHelper.getAccessToken(appid,secret);
    System.out.println("accessToken："+accessToken.getToken());
    System.out.println("accessToken有效秒数："+accessToken.getExpiresIn());
  }
}