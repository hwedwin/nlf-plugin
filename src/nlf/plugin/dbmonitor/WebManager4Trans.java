package nlf.plugin.dbmonitor;

import java.util.List;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.connection.IConnection;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.web.config.IWebConfig;
import nc.liat6.frame.web.config.WebManager;

/**
 * 连接监控专用web管理器
 * 
 * @author 6tail
 *
 */
public class WebManager4Trans extends WebManager{
  public WebManager4Trans(IWebConfig config){
    super(config);
  }

  public void after(){
    //在执行结束后，自动关闭未关闭的连接
    List<ConnVar> l = Context.get(Statics.CONNECTIONS);
    if(null!=l){
      for(ConnVar o:l){
        if(null==o){
          continue;
        }
        IConnection conn = o.getConnection();
        if(null==conn){
          continue;
        }
        try{
          if(conn.isClosed()){
            continue;
          }
          conn.rollback();
          conn.close();
          ConnectionMonitor.removeConnection(conn+"");
        }catch(Exception e){
          Logger.getLog().error(L.get("nlf.plugin.db.monitor.close_failed")+ConnectionMonitor.getConnection(conn.toString()),e);
        }
      }
    }
  }
}