package nlf.plugin.weixin.menu;

import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import nlf.plugin.weixin.exception.WeixinException;
import nlf.plugin.weixin.menu.bean.MenuButton;
import nlf.plugin.weixin.menu.type.MenuButtonType;
import nlf.plugin.weixin.util.HttpsClient;

/**
 * 菜单工具类
 * 
 * @author 6tail
 *
 */
public class MenuHelper{
  /** 创建菜单URL */
  public static String URL_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=?";
  /** 删除菜单URL */
  public static String URL_DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=?";
  /** 查询菜单URL */
  public static String URL_GET = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=?";

  private MenuHelper(){}

  /**
   * 创建菜单
   * 
   * @param buttons 菜单
   * @param accessToken
   * @throws WeixinException
   */
  public static void createMenu(List<MenuButton> buttons,String accessToken) throws WeixinException{
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
        }
        btn.remove("subs");
      }
      Bean dataBean = new Bean().set("button",btns);
      String data = JSON.toJson(dataBean);
      Logger.getLog().debug(Stringer.print("nlf.plugin.weixin.send",data));
      String result = HttpsClient.post(Stringer.print(URL_CREATE,"?",accessToken),data);
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errorcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }

  /**
   * 删除菜单
   * 
   * @param accessToken
   * @throws WeixinException
   */
  public static void deleteMenu(String accessToken) throws WeixinException{
    try{
      String result = HttpsClient.get(Stringer.print(URL_DELETE,"?",accessToken));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errorcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }

  /**
   * 查询菜单
   * 
   * @param accessToken
   * @throws WeixinException
   */
  public static List<MenuButton> getMenu(String accessToken) throws WeixinException{
    try{
      String result = HttpsClient.get(Stringer.print(URL_GET,"?",accessToken));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errorcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      o = o.get("menu");
      List<Bean> btns = o.get("button");
      List<MenuButton> l = new ArrayList<MenuButton>(btns.size());
      for(Bean btn:btns){
        MenuButton b = new MenuButton();
        b.setKey(btn.getString("key"));
        b.setName(btn.getString("name"));
        b.setType(MenuButtonType.valueOf(btn.getString("type")));
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
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}