package nlf.plugin.hikari;

import nc.liat6.frame.db.config.DbConfig;
import nc.liat6.frame.db.config.IDbConfigProvider;

/**
 * HikariCP数据库配置提供器
 *
 * @author 6tail
 *
 */
public class HikariConfigProvider implements IDbConfigProvider{

  public DbConfig getDbConfig(){
    DbConfig dc = new DbConfig();
    dc.setDbType("HIKARICP");
    dc.setDriverClassName(HikariDriver.class.getName());
    dc.setSuperInterfaceName(IHikari.class.getName());
    return dc;
  }
}