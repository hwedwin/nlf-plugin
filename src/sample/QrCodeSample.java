package sample;

import nlf.plugin.weixin.base.BaseHelper;
import nlf.plugin.weixin.base.bean.AccessToken;
import nlf.plugin.weixin.exception.WeixinException;
import nlf.plugin.weixin.qrcode.QrCodeHelper;
import nlf.plugin.weixin.qrcode.bean.QrCodeRequest;
import nlf.plugin.weixin.qrcode.bean.QrCodeResponse;
import nlf.plugin.weixin.qrcode.type.QrCodeType;

/**
 * 二维码示例
 * @author 6tail
 *
 */
public class QrCodeSample{
  public static void main(String[] args) throws WeixinException{
    //appid，在公众号开发者中心找
    String appid = "";
    //secret，在公众号开发者中心找
    String secret = "";
    //换取access token
    AccessToken accessToken = BaseHelper.getAccessToken(appid,secret);
    System.out.println("accessToken："+accessToken.getToken());
    System.out.println("accessToken有效秒数："+accessToken.getExpiresIn());
    
    //封装临时二维码请求
    QrCodeRequest requestTemp = new QrCodeRequest();
    requestTemp.setExpireIn(60);
    requestTemp.setSceneId(1);
    requestTemp.setType(QrCodeType.QR_SCENE);
    //创建二维码
    QrCodeResponse responseTemp = QrCodeHelper.createQrCode(requestTemp,accessToken.getToken());
    //二维码相关信息
    System.out.println("有效秒数："+responseTemp.getExpireIn());
    System.out.println("Ticket："+responseTemp.getTicket());
    System.out.println("Url："+responseTemp.getUrl());
    System.out.println("图片Url："+QrCodeHelper.showQrCode(responseTemp.getTicket()));
    
    //封装永久二维码请求
    QrCodeRequest requestLimit = new QrCodeRequest();
    requestTemp.setSceneId(2);
    requestTemp.setType(QrCodeType.QR_LIMIT_SCENE);
    //创建二维码
    QrCodeResponse responseLimit = QrCodeHelper.createQrCode(requestLimit,accessToken.getToken());
    //二维码相关信息
    System.out.println("Ticket："+responseLimit.getTicket());
    System.out.println("Url："+responseLimit.getUrl());
    System.out.println("图片Url："+QrCodeHelper.showQrCode(responseLimit.getTicket()));
  }
}