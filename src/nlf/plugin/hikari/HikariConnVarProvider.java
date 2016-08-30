package nlf.plugin.hikari;

import java.sql.Connection;
import java.sql.SQLException;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.connection.SqlConnection;
import nc.liat6.frame.db.connection.impl.SuperConnVarProvider;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * HikariCP连接变量提供器
 *
 * @author 6tail
 *
 */
public class HikariConnVarProvider extends SuperConnVarProvider{
  private static HikariDataSource dataSource = null;

  public ConnVar getConnVar(){
    HikariSetting setting = (HikariSetting)this.setting;
    ConnVar cv = new ConnVar();
    cv.setDbType(setting.getDbType());
    cv.setAlias(setting.getAlias());
    if(null==dataSource){
      HikariConfig config = new HikariConfig();
      config.setDataSourceClassName(setting.getDataSourceClassName());
      config.addDataSourceProperty("serverName",setting.getServer());
      config.addDataSourceProperty("port",setting.getPort());
      config.addDataSourceProperty("databaseName",setting.getDbName());
      config.addDataSourceProperty("user",setting.getUser());
      config.addDataSourceProperty("password",setting.getPassword());
      config.setConnectionTestQuery(setting.getConnectionTestQuery());
      if(setting.getIdleTimeout()>-1){
        config.setIdleTimeout(setting.getIdleTimeout());
      }
      if(setting.getConnectionTimeout()>-1){
        config.setConnectionTimeout(setting.getConnectionTimeout());
      }
      if(setting.getMaxLifetime()>-1){
        config.setMaxLifetime(setting.getMaxLifetime());
      }
      if(setting.getMaximumPoolSize()>-1){
        config.setMaximumPoolSize(setting.getMaximumPoolSize());
      }
      if(setting.getMinimumIdle()>-1){
        config.setMinimumIdle(setting.getMinimumIdle());
      }
      config.addDataSourceProperty("useUnicode",setting.isUseUnicode());
      config.addDataSourceProperty("characterEncoding",setting.getCharacterEncoding());
      HikariDataSource ds = new HikariDataSource(config);
      dataSource = ds;
    }
    Connection conn = null;
    try{
      conn = dataSource.getConnection();
    }catch(SQLException e){
      throw new DaoException(L.get(LocaleFactory.locale,"exception.dao"),e);
    }
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
    return "HikariCP".equalsIgnoreCase(connType);
  }
}