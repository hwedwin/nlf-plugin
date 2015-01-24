package nlf.plugin.weixin.js;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import nlf.plugin.weixin.base.BaseHelper;
import nlf.plugin.weixin.js.bean.JsConfig;
import nlf.plugin.weixin.js.bean.JsPayConfig;

public class JsHelper{
  protected JsHelper(){}

  public static String signConfig(JsConfig cfg) throws NoSuchAlgorithmException{
    StringBuffer s = new StringBuffer();
    s.append("jsapi_ticket=");
    s.append(cfg.getTicket());
    s.append("&noncestr=");
    s.append(cfg.getNoncestr());
    s.append("&timestamp=");
    s.append(cfg.getTimestamp());
    s.append("&url=");
    s.append(cfg.getUrl());
    return BaseHelper.sha1(s.toString());
  }

  public static String paySign(JsPayConfig payConfig,String secret) throws NoSuchAlgorithmException{
    Bean o = new Bean();
    o.fromObject(payConfig);
    o.set("package",o.get("pkg"));
    o.remove("pkg");
    List<String> args = new ArrayList<String>();
    Iterator<String> it = o.keySet().iterator();
    String key;
    while(it.hasNext()){
      key = it.next();
      if(o.getString(key,"").length()>0){
        args.add(key);
      }
    }
    Collections.sort(args);
    for(int i = 0,j = args.size();i<j;i++){
      key = args.get(i);
      args.set(i,key+"="+o.getString(key));
    }
    args.add("key="+secret);
    String s = Stringer.join(args,"&");
    Logger.getLog().debug(L.get("nlf.plugin.weixin.sign_data")+s);
    s = Stringer.md5(s);
    Logger.getLog().debug(L.get("nlf.plugin.weixin.sign_result")+s);
    return s;
  }
}