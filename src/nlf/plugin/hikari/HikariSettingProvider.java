package nlf.plugin.hikari;

import nc.liat6.frame.db.config.DbConfig;
import nc.liat6.frame.db.config.DbConfigFactory;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.setting.IDbSetting;
import nc.liat6.frame.db.setting.IDbSettingProvider;

/**
 * HikariCP连接池配置提供器
 * @author 6tail
 *
 */
public class HikariSettingProvider implements IDbSettingProvider{

  public IDbSetting getDbSetting(Bean o){
    String type = o.getString("type","");
    String alias = o.getString("alias","");
    String dbType = o.getString("dbtype","");
    String user = o.getString("user","");
    String password = o.getString("password","");
    String server = o.getString("server","");
    int port = o.getInt("port",0);
    String dbname = o.getString("dbname","");
    type = type.toUpperCase();
    dbType = dbType.toLowerCase();
    DbConfig dc = DbConfigFactory.getDbConfig(dbType);
    HikariSetting ps = new HikariSetting();
    ps.setType(type);
    ps.setAlias(alias);
    ps.setServer(server);
    ps.setPort(port);
    ps.setDriver(dc.getDriverClassName());
    ps.setPassword(password);
    ps.setUser(user);
    ps.setDbType(dbType);
    ps.setDbName(dbname);
    ps.setDataSourceClassName(o.getString("dataSourceClassName"));
    ps.setMinimumIdle(o.getInt("minimumIdle",-1));
    ps.setMaximumPoolSize(o.getInt("maximumPoolSize",-1));
    ps.setConnectionTestQuery(o.getString("testSql"));
    ps.setConnectionTimeout(o.getLong("connectionTimeout",-1));
    ps.setIdleTimeout(o.getLong("idleTimeout",-1));
    ps.setMaxLifetime(o.getLong("maxLifeTime",-1));
    ps.setUseUnicode(o.getBoolean("useUnicode",true));
    ps.setCharacterEncoding(o.getString("characterEncoding","utf8"));
    return ps;
  }

  public boolean support(String type){
    return "HikariCP".equalsIgnoreCase(type);
  }
}