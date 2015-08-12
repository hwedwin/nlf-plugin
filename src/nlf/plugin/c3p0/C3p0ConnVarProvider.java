package nlf.plugin.c3p0;

import java.sql.Connection;
import java.sql.SQLException;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.connection.SqlConnection;
import nc.liat6.frame.db.connection.impl.SuperConnVarProvider;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Reflector;

/**
 * C3P0连接变量提供器
 *
 * @author 6tail
 *
 */
public class C3p0ConnVarProvider extends SuperConnVarProvider{
  private static Object dataSource = null;

  public ConnVar getConnVar(){
    C3p0Setting setting = (C3p0Setting)this.setting;
    ConnVar cv = new ConnVar();
    cv.setDbType(setting.getDbType());
    cv.setAlias(setting.getAlias());
    if(null==dataSource){
      Object ds = Reflector.newInstance("com.mchange.v2.c3p0.ComboPooledDataSource");
      Reflector.execute(ds,"setDriverClass",new Class[]{String.class},new Object[]{setting.getDriver()});
      Reflector.execute(ds,"setJdbcUrl",new Class[]{String.class},new Object[]{setting.getUrl()});
      Reflector.execute(ds,"setUser",new Class[]{String.class},new Object[]{setting.getUser()});
      Reflector.execute(ds,"setPassword",new Class[]{String.class},new Object[]{setting.getPassword()});
      Reflector.execute(ds,"setTestConnectionOnCheckin",new Class[]{boolean.class},new Object[]{setting.isTestConnectionOnCheckin()});
      Reflector.execute(ds,"setTestConnectionOnCheckout",new Class[]{boolean.class},new Object[]{setting.isTestConnectionOnCheckout()});
      if(-1!=setting.getMiniPoolSize()){
        Reflector.execute(ds,"setMinPoolSize",new Class[]{int.class},new Object[]{setting.getMiniPoolSize()});
      }
      if(-1!=setting.getMaxPoolSize()){
        Reflector.execute(ds,"setMaxPoolSize",new Class[]{int.class},new Object[]{setting.getMaxPoolSize()});
      }
      if(-1!=setting.getInitialPoolSize()){
        Reflector.execute(ds,"setInitialPoolSize",new Class[]{int.class},new Object[]{setting.getInitialPoolSize()});
      }
      if(-1!=setting.getMaxIdleTime()){
        Reflector.execute(ds,"setMaxIdleTime",new Class[]{int.class},new Object[]{setting.getMaxIdleTime()});
      }
      if(-1!=setting.getMaxConnectionAge()){
        Reflector.execute(ds,"setMaxConnectionAge",new Class[]{int.class},new Object[]{setting.getMaxConnectionAge()});
      }
      if(-1!=setting.getAcquireIncrement()){
        Reflector.execute(ds,"setAcquireIncrement",new Class[]{int.class},new Object[]{setting.getAcquireIncrement()});
      }
      if(-1!=setting.getAcquireRetryAttempts()){
        Reflector.execute(ds,"setAcquireRetryAttempts",new Class[]{int.class},new Object[]{setting.getAcquireRetryAttempts()});
      }
      if(-1!=setting.getAcquireRetryDelay()){
        Reflector.execute(ds,"setAcquireRetryDelay",new Class[]{int.class},new Object[]{setting.getAcquireRetryDelay()});
      }
      if(-1!=setting.getIdleConnectionTestPeriod()){
        Reflector.execute(ds,"setIdleConnectionTestPeriod",new Class[]{int.class},new Object[]{setting.getIdleConnectionTestPeriod()});
      }
      if(-1!=setting.getCheckoutTimeout()){
        Reflector.execute(ds,"setCheckoutTimeout",new Class[]{int.class},new Object[]{setting.getCheckoutTimeout()});
      }
      if(-1!=setting.getMaxStatements()){
        Reflector.execute(ds,"setMaxStatements",new Class[]{int.class},new Object[]{setting.getMaxStatements()});
      }
      if(-1!=setting.getMaxStatementsPerConnection()){
        Reflector.execute(ds,"setMaxStatementsPerConnection",new Class[]{int.class},new Object[]{setting.getMaxStatementsPerConnection()});
      }
      dataSource = ds;
    }
    Connection conn = (Connection)Reflector.execute(dataSource,"getConnection");
    try{
      conn.setAutoCommit(false);
    }catch(SQLException e){
      Logger.getLog().error(L.get(LocaleFactory.locale,"db.commit_not_support"),e);
    }
    SqlConnection sc = new SqlConnection();
    sc.setSqlConnection(conn);
    cv.setConnection(sc);
    cv.setSetting(setting);
    return cv;
  }

  public boolean support(String connType){
    return "c3p0".equalsIgnoreCase(connType);
  }
}