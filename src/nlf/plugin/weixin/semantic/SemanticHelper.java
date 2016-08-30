package nlf.plugin.weixin.semantic;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import nlf.plugin.weixin.exception.WeixinException;
import nlf.plugin.weixin.semantic.bean.SemanticRequest;
import nlf.plugin.weixin.semantic.bean.SemanticResponse;
import nlf.plugin.weixin.semantic.bean.SemanticResponseFlight;
import nlf.plugin.weixin.semantic.code.SemanticErrorCode;
import nlf.plugin.weixin.util.HttpsClient;

/**
 * 语义识别
 * @author 6tail
 *
 */
public class SemanticHelper{
  /** 语义识别请求URL */
  public static String URL_SEND = "https://api.weixin.qq.com/semantic/semproxy/search?access_token=?";

  protected SemanticHelper(){}

  /**
   * 识别
   * @param accessToken accessToken
   * @param request 语义识别请求
   * @return 语义识别结果
   * @throws WeixinException
   */
  public static SemanticResponse recognize(String accessToken,SemanticRequest request) throws WeixinException{
    try{
      String url = Stringer.print(URL_SEND,"?",accessToken);
      String data = JSON.toJson(request);
      Logger.getLog().debug(L.get("nlf.plugin.weixin.send")+data);
      String result = HttpsClient.post(url,data);
      Logger.getLog().debug(L.get("nlf.plugin.weixin.recv")+result);
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,SemanticErrorCode.getErrorMsg(errorCode));
      }
      String type = o.getString("type");
      SemanticResponse response = new SemanticResponse();
      if("flight".equals(type)){
        SemanticResponseFlight res = new SemanticResponseFlight();
        Bean details = o.getBean("semantic").get("details");
        res.setAirline(details.getString("airline"));
        Bean startLoc = details.get("start_loc");
        res.setDep(startLoc.getString("loc_ori"));
        res.setDepCityName(startLoc.getString("city"));
        res.setDepCityNameSimple(startLoc.getString("city_simple"));
        Bean endLoc = details.get("end_loc");
        res.setArr(endLoc.getString("loc_ori"));
        res.setArrCityName(endLoc.getString("city"));
        res.setArrCityNameSimple(endLoc.getString("city_simple"));
        Bean startDate = details.get("start_date");
        res.setDay(startDate.getString("date_ori"));
        res.setDayInYmd(startDate.getString("date"));
        response = res;
      }
      response.setQuery(o.getString("query"));
      response.setType(type);
      response.setAnswer(o.getString("answer"));
      response.setText(o.getString("text"));
      return response;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}