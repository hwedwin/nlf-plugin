package nlf.plugin.weixin.msg.bean.impl;

import java.util.ArrayList;
import java.util.List;
import nlf.plugin.weixin.msg.bean.AbstractMsg;
import nlf.plugin.weixin.msg.bean.IResponseMsg;
import nlf.plugin.weixin.msg.type.MsgType;

/**
 * 图文消息
 * 
 * @author 6tail
 * 
 */
public class NewsMsg extends AbstractMsg implements IResponseMsg{
  /** 图文列表 */
  private List<NewsItem> items = new ArrayList<NewsItem>();

  public NewsMsg(){
    setMsgType(MsgType.news);
  }

  public void addItem(NewsItem item){
    items.add(item);
  }

  public List<NewsItem> getItems(){
    return items;
  }

  public void setItems(List<NewsItem> items){
    this.items = items;
  }
}