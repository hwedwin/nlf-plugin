package nlf.plugin.weibo.msg.core.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nlf.plugin.weibo.msg.bean.IEventMsg;
import nlf.plugin.weibo.msg.bean.IMsg;
import nlf.plugin.weibo.msg.bean.IResponseMsg;
import nlf.plugin.weibo.msg.bean.impl.FollowEventMsg;
import nlf.plugin.weibo.msg.bean.impl.MentionMsg;
import nlf.plugin.weibo.msg.bean.impl.SubscribeEventMsg;
import nlf.plugin.weibo.msg.bean.impl.TextMsg;
import nlf.plugin.weibo.msg.bean.impl.UnFollowEventMsg;
import nlf.plugin.weibo.msg.bean.impl.UnSubscribeEventMsg;
import nlf.plugin.weibo.msg.core.IMsgController;
import nlf.plugin.weibo.msg.core.IMsgHandler;
import nlf.plugin.weibo.msg.core.IMsgResolver;

/**
 * 微博消息控制器
 * 
 * @author 6tail
 *
 */
public class WeiboMsgController implements IMsgController{
  /** appsecret */
  protected String appsecret;
  /** 消息解析接口 */
  protected IMsgResolver resolver;

  /**
   * 构造控制器
   * 
   * @param appsecret appsecret
   */
  public WeiboMsgController(String appsecret){
    this(appsecret,new WeiboMsgResolver());
  }

  /**
   * 构造控制器
   * 
   * @param appsecret appsecret
   * @param resolver 消息解析器
   */
  public WeiboMsgController(String appsecret,IMsgResolver resolver){
    this.appsecret = appsecret;
    this.resolver = resolver;
  }

  /**
   * sha1
   * 
   * @param s 原字符串
   * @return 结果字符串
   * @throws NoSuchAlgorithmException
   */
  private String sha1(String s) throws NoSuchAlgorithmException{
    MessageDigest md = MessageDigest.getInstance("SHA-1");
    md.update(s.getBytes());
    byte[] b = md.digest();
    StringBuffer sb = new StringBuffer();
    for(int i = 0;i<b.length;i++){
      String hex = Integer.toHexString(b[i]&0xFF);
      hex = (hex.length()==1?"0":"")+hex;
      sb.append(hex);
    }
    return sb.toString();
  }

  /**
   * 验证
   * 
   * @return true/false 验证成功/失败
   * @throws NoSuchAlgorithmException
   */
  private boolean check() throws NoSuchAlgorithmException{
    Request r = Context.get(Statics.REQUEST);
    String signature = r.get("signature");
    String timestamp = r.get("timestamp");
    String nonce = r.get("nonce");
    Logger.getLog().info("signature="+signature+",timestamp="+timestamp+",nonce="+nonce);
    if("".equals(signature)){
      return false;
    }
    if("".equals(timestamp)){
      return false;
    }
    if("".equals(nonce)){
      return false;
    }
    appsecret = null==appsecret?"":appsecret.trim();
    if("".equals(appsecret)){
      return false;
    }
    String[] a = new String[]{appsecret,timestamp,nonce};
    Arrays.sort(a);
    StringBuilder s = new StringBuilder();
    for(String o:a){
      s.append(o);
    }
    return signature.equals(sha1(s.toString()));
  }

  /**
   * get处理，用于首次验证
   * 
   * @return 响应内容
   * @throws NoSuchAlgorithmException
   */
  protected String onGet() throws NoSuchAlgorithmException{
    Request r = Context.get(Statics.REQUEST);
    String echostr = r.get("echostr");
    if(echostr.length()<1){
      return "";
    }
    return check()?echostr:"";
  }

  /**
   * post处理
   * 
   * @param handler 消息处理接口
   * @return 响应内容
   * @throws NoSuchAlgorithmException
   * @throws IOException
   */
  protected String onPost(IMsgHandler handler) throws NoSuchAlgorithmException,IOException{
    //如果token为null或空字符串，用于本地测试，不进行验证
    if(null!=appsecret&&appsecret.length()>0){
      if(!check()){
        return "";
      }
    }
    Request r = Context.get(Statics.REQUEST);
    HttpServletRequest or = r.find(Statics.FIND_REQUEST);
    BufferedReader br = new BufferedReader(new InputStreamReader(or.getInputStream(),"utf-8"));
    StringBuilder s = new StringBuilder();
    String line = "";
    while(null!=(line = br.readLine())){
      s.append(line);
    }
    Logger.getLog().debug(L.get("nlf.plugin.weibo.request")+s);
    if(null==handler){
      return "";
    }
    IMsg msg = resolver.decode(s.toString());
    if(null==msg){
      return "";
    }
    IResponseMsg responseMsg = null;
    switch(msg.getMsgType()){
      case event:
        IEventMsg em = (IEventMsg)msg;
        switch(em.getEventType()){
          case subscribe:
            responseMsg = handler.onSubscribe((SubscribeEventMsg)em);
            break;
          case unsubscribe:
            responseMsg = handler.onUnSubscribe((UnSubscribeEventMsg)em);
            break;
          case follow:
            responseMsg = handler.onFollow((FollowEventMsg)em);
            break;
          case unfollow:
            responseMsg = handler.onUnFollow((UnFollowEventMsg)em);
            break;
          default:
            break;
        }
        break;
      case text:
        responseMsg = handler.onText((TextMsg)msg);
        break;
      case mention:
        responseMsg = handler.onMention((MentionMsg)msg);
        break;
      default:
        break;
    }
    String responseStr = resolver.encode(responseMsg);
    Logger.getLog().debug(L.get("nlf.plugin.weibo.response")+responseStr);
    return responseStr;
  }

  public String handle(IMsgHandler handler){
    try{
      Request r = Context.get(Statics.REQUEST);
      HttpServletRequest or = r.find(Statics.FIND_REQUEST);
      String method = or.getMethod();
      if("GET".equalsIgnoreCase(method)){
        return onGet();
      }
      if("POST".equals(method)){
        return onPost(handler);
      }
    }catch(Throwable e){
      Logger.getLog().error(L.get("nlf.plugin.weibo.error"),e);
    }
    return "";
  }
}