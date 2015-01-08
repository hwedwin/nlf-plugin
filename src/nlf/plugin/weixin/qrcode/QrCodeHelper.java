package nlf.plugin.weixin.qrcode;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import nlf.plugin.weixin.exception.WeixinException;
import nlf.plugin.weixin.qrcode.bean.QrCodeRequest;
import nlf.plugin.weixin.qrcode.bean.QrCodeResponse;
import nlf.plugin.weixin.qrcode.type.QrCodeType;
import nlf.plugin.weixin.util.HttpsClient;

/**
 * 二维码工具类
 * 
 * @author 6tail
 *
 */
public class QrCodeHelper{
  /** 创建二维码URL */
  public static String URL_CREATE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=?";
  /** 显示二维码URL */
  public static String URL_SHOW = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=?";

  private QrCodeHelper(){}

  /**
   * 创建二维码
   * 
   * @param request 二维码请求
   * @param accessToken
   * @return 二维码响应
   * @throws WeixinException
   */
  public static QrCodeResponse createQrCode(QrCodeRequest request,String accessToken) throws WeixinException{
    try{
      Bean dataBean = new Bean();
      dataBean.set("action_name",request.getType().toString());
      dataBean.set("action_info",new Bean().set("scene",new Bean().set("scene_id",request.getSceneId())));
      if(QrCodeType.QR_SCENE==request.getType()){
        dataBean.set("expire_seconds",request.getExpireIn());
      }
      String data = JSON.toJson(dataBean);
      Logger.getLog().debug(L.get("nlf.plugin.weixin.send")+data);
      String result = HttpsClient.post(Stringer.print(URL_CREATE,"?",accessToken),data);
      Logger.getLog().debug(L.get("nlf.plugin.weixin.recv")+result);
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      QrCodeResponse response = new QrCodeResponse();
      response.setExpireIn(o.getInt("expire_seconds",1800));
      response.setTicket(o.getString("ticket"));
      response.setUrl(o.getString("url"));
      return response;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }

  /**
   * 获取二维码图片URL
   * 
   * @param ticket 二维码ticket
   * @return 二维码图片的URL
   * @throws WeixinException
   */
  public static String showQrCode(String ticket) throws WeixinException{
    return Stringer.print(URL_SHOW,"?",ticket);
  }
}