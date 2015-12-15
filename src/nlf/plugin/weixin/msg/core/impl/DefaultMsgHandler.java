package nlf.plugin.weixin.msg.core.impl;

import nlf.plugin.weixin.msg.bean.IResponseMsg;
import nlf.plugin.weixin.msg.bean.impl.ClickEventMsg;
import nlf.plugin.weixin.msg.bean.impl.ImageMsg;
import nlf.plugin.weixin.msg.bean.impl.KfCloseSessionEventMsg;
import nlf.plugin.weixin.msg.bean.impl.KfCreateSessionEventMsg;
import nlf.plugin.weixin.msg.bean.impl.KfSwitchSessionEventMsg;
import nlf.plugin.weixin.msg.bean.impl.LinkMsg;
import nlf.plugin.weixin.msg.bean.impl.LocationEventMsg;
import nlf.plugin.weixin.msg.bean.impl.LocationMsg;
import nlf.plugin.weixin.msg.bean.impl.ScanEventMsg;
import nlf.plugin.weixin.msg.bean.impl.SubscribeEventMsg;
import nlf.plugin.weixin.msg.bean.impl.TemplateSendJobFinishEventMsg;
import nlf.plugin.weixin.msg.bean.impl.TextMsg;
import nlf.plugin.weixin.msg.bean.impl.UnSubscribeEventMsg;
import nlf.plugin.weixin.msg.bean.impl.VideoMsg;
import nlf.plugin.weixin.msg.bean.impl.ViewEventMsg;
import nlf.plugin.weixin.msg.bean.impl.VoiceMsg;
import nlf.plugin.weixin.msg.core.IMsgHandler;

/**
 * 抽象微信公众号消息处理者
 * 
 * @author 6tail
 *
 */
public abstract class DefaultMsgHandler implements IMsgHandler{
  public IResponseMsg onSubscribe(SubscribeEventMsg event){
    return null;
  }

  public IResponseMsg onUnSubscribe(UnSubscribeEventMsg event){
    return null;
  }

  public IResponseMsg onClick(ClickEventMsg event){
    return null;
  }

  public IResponseMsg onLocation(LocationEventMsg event){
    return null;
  }

  public IResponseMsg onScan(ScanEventMsg event){
    return null;
  }

  public IResponseMsg onView(ViewEventMsg event){
    return null;
  }

  public IResponseMsg onImage(ImageMsg msg){
    return null;
  }

  public IResponseMsg onLink(LinkMsg msg){
    return null;
  }

  public IResponseMsg onLocation(LocationMsg msg){
    return null;
  }

  public IResponseMsg onText(TextMsg msg){
    return null;
  }

  public IResponseMsg onVideo(VideoMsg msg){
    return null;
  }

  public IResponseMsg onVoice(VoiceMsg msg){
    return null;
  }
  
  public IResponseMsg onTemplateSendJobFinish(TemplateSendJobFinishEventMsg event){
    return null;
  }
  
  public IResponseMsg onKfCreateSession(KfCreateSessionEventMsg msg){
    return null;
  }
  
  public IResponseMsg onKfCloseSession(KfCloseSessionEventMsg msg){
    return null;
  }
  
  public IResponseMsg onKfSwitchSession(KfSwitchSessionEventMsg msg){
    return null;
  }
}