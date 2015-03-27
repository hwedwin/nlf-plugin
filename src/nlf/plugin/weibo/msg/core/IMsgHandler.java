package nlf.plugin.weibo.msg.core;

import nlf.plugin.weibo.msg.bean.IResponseMsg;
import nlf.plugin.weibo.msg.bean.impl.ClickEventMsg;
import nlf.plugin.weibo.msg.bean.impl.FollowEventMsg;
import nlf.plugin.weibo.msg.bean.impl.MentionMsg;
import nlf.plugin.weibo.msg.bean.impl.SubscribeEventMsg;
import nlf.plugin.weibo.msg.bean.impl.TextMsg;
import nlf.plugin.weibo.msg.bean.impl.UnFollowEventMsg;
import nlf.plugin.weibo.msg.bean.impl.UnSubscribeEventMsg;

/**
 * 微博消息处理接口
 * 
 * @author 6tail
 *
 */
public interface IMsgHandler{
  /**
   * 当用户订阅时
   * 
   * @param event 订阅事件
   * @return 响应消息
   */
  IResponseMsg onSubscribe(SubscribeEventMsg event);

  /**
   * 当用户取消订阅时
   * 
   * @param event 取消订阅事件
   * @return 响应消息
   */
  IResponseMsg onUnSubscribe(UnSubscribeEventMsg event);

  /**
   * 当用户关注时
   * 
   * @param event 关注事件
   * @return 响应消息
   */
  IResponseMsg onFollow(FollowEventMsg event);

  /**
   * 当用户取消关注时
   * 
   * @param event 取消关注事件
   * @return 响应消息
   */
  IResponseMsg onUnFollow(UnFollowEventMsg event);

  /**
   * 当用户发来文字消息
   * 
   * @param msg 用户发来的消息
   * @return 响应消息
   */
  IResponseMsg onText(TextMsg msg);
  
  /**
   * 当用户@时
   * 
   * @param msg 用户@的消息
   * @return 响应消息
   */
  IResponseMsg onMention(MentionMsg msg);
  
  /**
   * 当用户点击菜单
   * @param msg 用户点击菜单事件
   * @return 响应消息
   */
  IResponseMsg onClick(ClickEventMsg msg);
}