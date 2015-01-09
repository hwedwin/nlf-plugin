package nlf.plugin.dbmonitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;

/**
 * 连接监控
 * 
 * @author 6tail
 *
 */
public class ConnectionMonitor{
  /** 连接信息池 */
  private static final Map<String,ConnectionInfo> pool = new HashMap<String,ConnectionInfo>();

  private ConnectionMonitor(){}

  /**
   * 添加连接信息
   * 
   * @param ci 连接信息
   */
  public static void addConnection(ConnectionInfo ci){
    Logger.getLog().debug(L.get("nlf.plugin.db.monitor.create_conn")+ci);
    pool.put(ci.getConnectionId(),ci);
  }

  /**
   * 获取连接信息
   * 
   * @param connectionId 连接ID
   * @return 连接信息
   */
  public static ConnectionInfo getConnection(String connectionId){
    return pool.get(connectionId);
  }

  /**
   * 移除连接信息
   * 
   * @param connectionId 连接ID
   */
  public static void removeConnection(String connectionId){
    Logger.getLog().debug(L.get("nlf.plugin.db.monitor.remove_conn")+connectionId);
    pool.remove(connectionId);
  }

  /**
   * 获取正在使用的连接数量
   * 
   * @return 正在使用的连接数量
   */
  public static int getRunningCount(){
    return pool.size();
  }
  
  /**
   * 获取所有的连接ID
   * @return 所有的连接ID
   */
  public Set<String> keySet(){
    return pool.keySet();
  }
}