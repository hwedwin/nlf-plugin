package nlf.plugin.weibo.msg.core.impl;

import nlf.plugin.weibo.msg.bean.IResponseMsg;
import nlf.plugin.weibo.msg.bean.impl.FollowEventMsg;
import nlf.plugin.weibo.msg.bean.impl.MentionMsg;
import nlf.plugin.weibo.msg.bean.impl.SubscribeEventMsg;
import nlf.plugin.weibo.msg.bean.impl.TextMsg;
import nlf.plugin.weibo.msg.bean.impl.UnFollowEventMsg;
import nlf.plugin.weibo.msg.bean.impl.UnSubscribeEventMsg;
import nlf.plugin.weibo.msg.core.IMsgHandler;

/**
 * 抽象微博消息处理者
 * 
 * @author 6tail
 *
 */
public abstract class WeiboMsgHandler implements IMsgHandler{
  public IResponseMsg onSubscribe(SubscribeEventMsg event){
    return null;
  }

  public IResponseMsg onUnSubscribe(UnSubscribeEventMsg event){
    return null;
  }

  public IResponseMsg onFollow(FollowEventMsg event){
    return null;
  }

  public IResponseMsg onMention(MentionMsg msg){
    return null;
  }

  public IResponseMsg onText(TextMsg msg){
    return null;
  }

  public IResponseMsg onUnFollow(UnFollowEventMsg event){
    return null;
  }
}