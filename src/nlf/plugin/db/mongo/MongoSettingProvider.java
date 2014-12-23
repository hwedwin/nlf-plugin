package nlf.plugin.db.mongo;

import nc.liat6.frame.db.config.DbConfig;
import nc.liat6.frame.db.config.DbConfigFactory;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.setting.IDbSetting;
import nc.liat6.frame.db.setting.IDbSettingProvider;
import nc.liat6.frame.util.Stringer;

public class MongoSettingProvider implements IDbSettingProvider{

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
    MongoSetting ms = new MongoSetting();
    ms.setAlias(alias);
    ms.setDriver(dc.getDriverClassName());
    ms.setPassword(password);
    ms.setUrl(Stringer.print(dc.getUrl(),server,port));
    ms.setUser(user);
    ms.setDbType(dbType);
    ms.setDbName(dbname);
    return ms;
  }

  public boolean support(String type){
    return "mongo".equalsIgnoreCase(type);
  }
}