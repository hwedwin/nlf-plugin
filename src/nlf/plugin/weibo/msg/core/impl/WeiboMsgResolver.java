package nlf.plugin.weibo.msg.core.impl;

import java.net.URLEncoder;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.JSON;
import nlf.plugin.weibo.msg.bean.IEventMsg;
import nlf.plugin.weibo.msg.bean.IRequestMsg;
import nlf.plugin.weibo.msg.bean.IResponseMsg;
import nlf.plugin.weibo.msg.bean.impl.MentionMsg;
import nlf.plugin.weibo.msg.bean.impl.SubscribeEventMsg;
import nlf.plugin.weibo.msg.bean.impl.TextMsg;
import nlf.plugin.weibo.msg.bean.impl.UnSubscribeEventMsg;
import nlf.plugin.weibo.msg.core.IMsgResolver;
import nlf.plugin.weibo.msg.type.EventType;
import nlf.plugin.weibo.msg.type.MsgType;

/**
 * 默认微博消息解析器
 * 
 * @author 6tail
 *
 */
public class WeiboMsgResolver implements IMsgResolver{
  /**
   * 解析文本
   * 
   * @param o 数据Bean
   * @return 请求消息
   */
  private IRequestMsg decodeTextMsg(Bean d){
    TextMsg tm = new TextMsg();
    tm.setContent(d.getString("text",""));
    return tm;
  }

  /**
   * 解析@消息
   * 
   * @param o 数据Bean
   * @return 请求消息
   */
  private IRequestMsg decodeMentionMsg(Bean d){
    MentionMsg tm = new MentionMsg();
    tm.setContent(d.getString("text",""));
    Bean data = d.get("data");
    tm.setSubtype(data.getString("subtype",""));
    tm.setKey(data.getString("key",""));
    return tm;
  }

  /**
   * 解析订阅事件
   * 
   * @param o 数据Bean
   * @return 事件消息
   */
  private IEventMsg decodeSubscribeMsg(Bean d){
    return new SubscribeEventMsg();
  }

  /**
   * 解析取消订阅事件
   * 
   * @param o 数据Bean
   * @return 事件消息
   */
  private IEventMsg decodeUnSubscribeMsg(Bean d){
    return new UnSubscribeEventMsg();
  }

  /**
   * 解析关注事件
   * 
   * @param o 数据Bean
   * @return 事件消息
   */
  private IEventMsg decodeFollowMsg(Bean d){
    return new SubscribeEventMsg();
  }

  /**
   * 解析取消关注事件
   * 
   * @param o 数据Bean
   * @return 事件消息
   */
  private IEventMsg decodeUnFollowMsg(Bean d){
    return new UnSubscribeEventMsg();
  }

  /**
   * 解析事件
   * 
   * @param o 数据Bean
   * @return 事件消息
   */
  private IEventMsg decodeEvent(Bean d){
    try{
      IEventMsg msg = null;
      Bean data = d.get("data");
      EventType type = EventType.valueOf(data.getString("subtype"));
      switch(type){
        case follow:
          msg = decodeFollowMsg(d);
          break;
        case unfollow:
          msg = decodeUnFollowMsg(d);
          break;
        case subscribe:
          msg = decodeSubscribeMsg(d);
          break;
        case unsubscribe:
          msg = decodeUnSubscribeMsg(d);
          break;
      }
      return msg;
    }catch(Exception e){
      return null;
    }
  }

  public IRequestMsg decode(String s){
    try{
      IRequestMsg msg = null;
      Bean d = JSON.toBean(s);
      MsgType type = MsgType.valueOf(d.getString("type"));
      if(null==type){
        return null;
      }
      switch(type){
        case text:
          msg = decodeTextMsg(d);
          break;
        case mention:
          msg = decodeMentionMsg(d);
          break;
        case event:
          msg = decodeEvent(d);
          break;
        default:
          break;
      }
      if(null==msg){
        return null;
      }
      msg.setFromUser(d.getString("sender_id",""));
      msg.setToUser(d.getString("receiver_id",""));
      msg.setCreateTime(d.getString("created_at",""));
      return msg;
    }catch(Exception e){
      return null;
    }
  }

  /**
   * 构造文本消息字符串
   * 
   * @param msg 消息
   * @return 字符串
   */
  private String encodeTextMsg(TextMsg msg){
    try{
      TextMsg tm = (TextMsg)msg;
      Bean o = new Bean();
      o.set("text",tm.getContent());
      return URLEncoder.encode(JSON.toJson(o),"utf-8");
    }catch(Exception e){
      return "";
    }
  }

  public String encode(IResponseMsg msg){
    if(null==msg){
      return "";
    }
    Bean o = new Bean();
    o.set("result",true);
    o.set("receiver_id",msg.getToUser());
    o.set("sender_id",msg.getFromUser());
    o.set("type",msg.getMsgType()+"");
    switch(msg.getMsgType()){
      case text:
        o.set("data",encodeTextMsg((TextMsg)msg));
        break;
      default:
        return "";
    }
    return JSON.toJson(o);
  }
}