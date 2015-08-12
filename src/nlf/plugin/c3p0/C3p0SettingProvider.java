package nlf.plugin.c3p0;

import nc.liat6.frame.db.config.DbConfig;
import nc.liat6.frame.db.config.DbConfigFactory;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.setting.IDbSetting;
import nc.liat6.frame.db.setting.IDbSettingProvider;
import nc.liat6.frame.util.Stringer;

/**
 * C3P0连接池配置提供器
 * @author 6tail
 *
 */
public class C3p0SettingProvider implements IDbSettingProvider{

  public IDbSetting getDbSetting(Bean o){
    String type = o.getString("type","");
    String alias = o.getString("alias","");
    String dbType = o.getString("dbtype","");
    String user = o.getString("user","");
    String password = o.getString("password","");
    String server = o.getString("server","");
    String port = o.getString("port","");
    String dbname = o.getString("dbname","");
    type = type.toUpperCase();
    dbType = dbType.toLowerCase();
    DbConfig dc = DbConfigFactory.getDbConfig(dbType);
    C3p0Setting ps = new C3p0Setting();
    ps.setAlias(alias);
    ps.setDriver(dc.getDriverClassName());
    ps.setPassword(password);
    ps.setUrl(Stringer.print(dc.getUrl(),server,port,dbname));
    ps.setUser(user);
    ps.setDbType(dbType);
    ps.setDbName(dbname);
    ps.setMiniPoolSize(o.getInt("miniPoolSize",-1));
    ps.setMaxPoolSize(o.getInt("maxPoolSize",-1));
    ps.setInitialPoolSize(o.getInt("initialPoolSize",-1));
    ps.setMaxIdleTime(o.getInt("maxIdleTime",-1));
    ps.setAcquireIncrement(o.getInt("acquireIncrement",-1));
    ps.setAcquireRetryAttempts(o.getInt("acquireRetryAttempts",-1));
    ps.setAcquireRetryDelay(o.getInt("acquireRetryDelay",-1));
    ps.setTestConnectionOnCheckin(o.getBoolean("testConnectionOnCheckin",true));
    ps.setTestConnectionOnCheckout(o.getBoolean("testConnectionOnCheckout",true));
    ps.setAutomaticTestTable(o.getString("automaticTestTable"));
    ps.setIdleConnectionTestPeriod(o.getInt("idleConnectionTestPeriod",-1));
    ps.setCheckoutTimeout(o.getInt("checkoutTimeout",-1));
    ps.setMaxStatements(o.getInt("maxStatements",-1));
    ps.setMaxStatementsPerConnection(o.getInt("maxStatementsPerConnection",-1));
    return ps;
  }

  public boolean support(String type){
    return "c3p0".equalsIgnoreCase(type);
  }
}