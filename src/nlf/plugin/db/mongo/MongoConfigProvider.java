package nlf.plugin.db.mongo;

import nc.liat6.frame.db.config.DbConfig;
import nc.liat6.frame.db.config.IDbConfigProvider;

/**
 * MONGO数据库配置提供器
 *
 * @author 6tail
 *
 */
public class MongoConfigProvider implements IDbConfigProvider{

  public DbConfig getDbConfig(){
    DbConfig dc = new DbConfig();
    dc.setDbType("MONGO");
    dc.setDriverClassName(MongoDriver.class.getName());
    dc.setUrl("?:?");
    dc.setSuperInterfaceName(IMongo.class.getName());
    return dc;
  }
}