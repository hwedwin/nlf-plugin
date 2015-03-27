package nlf.plugin.weibo.menu;

import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import nlf.plugin.weibo.exception.WeiboException;
import nlf.plugin.weibo.menu.bean.MenuButton;
import nlf.plugin.weibo.menu.type.MenuButtonType;
import nlf.plugin.weibo.util.HttpsClient;

/**
 * 微博菜单工具类
 * 
 * @author 6tail
 *
 */
public class WeiboMenuHelper{
  /** 创建菜单URL */
  public static String URL_CREATE = "https://m.api.weibo.com/2/messages/menu/create.json?access_token=?";
  /** 删除菜单URL */
  public static String URL_DELETE = "https://m.api.weibo.com/2/messages/menu/delete.json?access_token=?";
  /** 查询菜单URL */
  public static String URL_GET = "https://m.api.weibo.com/2/messages/menu/show.json?access_token=?";

  private WeiboMenuHelper(){}

  /**
   * 创建菜单
   * 
   * @param buttons 菜单
   * @param accessToken
   * @throws WeiboException
   */
  public static void createMenu(List<MenuButton> buttons,String accessToken) throws WeiboException{
    try{
      //重新构造json
      List<Bean> btns = JSON.toBean(JSON.toJson(buttons));
      for(Bean btn:btns){
        if(!MenuButtonType.view.toString().equals(btn.getString("type"))){
          btn.remove("url");
        }
        List<Bean> subs = btn.get("subs");
        if(subs.size()>0){
          for(Bean sub:subs){
            if(!MenuButtonType.view.toString().equals(sub.getString("type"))){
              sub.remove("url");
            }
            sub.remove("subs");
          }
          btn.set("sub_button",subs);
          btn.remove("type");
        }
        btn.remove("subs");
      }
      Bean dataBean = new Bean().set("button",btns);
      String data = JSON.toJson(dataBean);
      Logger.getLog().debug(L.get("nlf.plugin.weibo.send")+data);
      String result = HttpsClient.post(Stringer.print(URL_CREATE,"?",accessToken),data);
      Logger.getLog().debug(L.get("nlf.plugin.weibo.recv")+result);
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("error_code",0);
      if(0!=errorCode){
        throw new WeiboException(errorCode,o.getString("error"),o.getString("request"));
      }
    }catch(WeiboException e){
      throw e;
    }catch(Exception e){
      throw new WeiboException(e);
    }
  }

  /**
   * 删除菜单
   * 
   * @param accessToken
   * @throws WeiboException
   */
  public static void deleteMenu(String accessToken) throws WeiboException{
    try{
      String result = HttpsClient.get(Stringer.print(URL_DELETE,"?",accessToken));
      Logger.getLog().debug(L.get("nlf.plugin.weibo.recv")+result);
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("error_code",0);
      if(0!=errorCode){
        throw new WeiboException(errorCode,o.getString("error"),o.getString("request"));
      }
    }catch(WeiboException e){
      throw e;
    }catch(Exception e){
      throw new WeiboException(e);
    }
  }

  /**
   * 查询菜单
   * 
   * @param accessToken
   * @throws WeiboException
   */
  public static List<MenuButton> getMenu(String accessToken) throws WeiboException{
    try{
      String result = HttpsClient.get(Stringer.print(URL_GET,"?",accessToken));
      Logger.getLog().debug(L.get("nlf.plugin.weibo.recv")+result);
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("error_code",0);
      if(0!=errorCode){
        throw new WeiboException(errorCode,o.getString("error"),o.getString("request"));
      }
      o = o.get("menu");
      List<Bean> btns = o.get("button");
      List<MenuButton> l = new ArrayList<MenuButton>(btns.size());
      for(Bean btn:btns){
        MenuButton b = new MenuButton();
        b.setKey(btn.getString("key"));
        b.setName(btn.getString("name"));
        try{
          b.setType(MenuButtonType.valueOf(btn.getString("type")));
        }catch(Exception e){}
        b.setUrl(btn.getString("url"));
        List<Bean> subs = btn.get("sub_button");
        if(null!=subs){
          for(Bean sub:subs){
            MenuButton sb = new MenuButton();
            sb.setKey(sub.getString("key"));
            sb.setName(sub.getString("name"));
            sb.setType(MenuButtonType.valueOf(sub.getString("type")));
            sb.setUrl(sub.getString("url"));
            b.addSub(sb);
          }
        }
      }
      return l;
    }catch(WeiboException e){
      throw e;
    }catch(Exception e){
      throw new WeiboException(e);
    }
  }
}