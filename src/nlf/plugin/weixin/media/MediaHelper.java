package nlf.plugin.weixin.media;

import java.io.File;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import nlf.plugin.weixin.exception.WeixinException;
import nlf.plugin.weixin.media.bean.Media;
import nlf.plugin.weixin.util.HttpClient;

/**
 * 媒体工具
 * 
 * @author 6tail
 *
 */
public class MediaHelper{
  /** 媒体上传URL */
  public static String URL_UPLOAD = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=?&type=?";
  /** 媒体下载URL */
  public static String URL_DOWNLOAD = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=?&media_id=?";

  protected MediaHelper(){}

  /**
   * 媒体上传
   * 
   * @param file 文件
   * @param accessToken
   * @param type 媒体类型
   * @return 媒体信息
   * @throws WeixinException
   */
  public static Media upload(File file,String accessToken,MediaType type) throws WeixinException{
    try{
      String url = Stringer.print(URL_UPLOAD,"?",accessToken,type);
      String result = HttpClient.upload(url,file);
      Logger.getLog().debug(L.get("nlf.plugin.weixin.recv")+result);
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      Media media = new Media();
      media.setMediaId(o.getString("media_id"));
      media.setType(MediaType.valueOf(o.getString("type")));
      media.setCreatedAt(o.getString("created_at"));
      return media;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
  
  /**
   * 下载媒体文件
   * @param accessToken
   * @param mediaId 媒体ID
   * @param file 保存文件
   * @throws WeixinException
   */
  public static void download(String accessToken,String mediaId,File file) throws WeixinException{
    try{
      String url = Stringer.print(URL_DOWNLOAD,"?",accessToken,mediaId);
      HttpClient.download(url,file);
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}