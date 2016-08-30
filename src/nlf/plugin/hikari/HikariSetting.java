package nlf.plugin.hikari;

import nc.liat6.frame.db.setting.impl.SuperDbSetting;

/**
 * HikariCP连接池配置
 * 
 * @author 6tail
 * 
 */
public class HikariSetting extends SuperDbSetting{
  private static final long serialVersionUID = -1;
  /** 默认连接类型 */
  public static final String DEFAULT_TYPE = "HikariCP";
  private int port;
  private int minimumIdle = -1;
  private int maximumPoolSize = -1;
  private long maxLifetime = -1;
  private long idleTimeout = -1;
  private long connectionTimeout = -1;
  private boolean useUnicode = true;
  private String characterEncoding = "utf8";
  private String dataSourceClassName;
  private String connectionTestQuery;
  private String server;

  public int getPort(){
    return port;
  }

  public void setPort(int port){
    this.port = port;
  }

  public void setServer(String server){
    this.server = server;
  }

  public String getServer(){
    return server;
  }

  public int getMinimumIdle(){
    return minimumIdle;
  }

  public void setMinimumIdle(int minimumIdle){
    this.minimumIdle = minimumIdle;
  }

  public int getMaximumPoolSize(){
    return maximumPoolSize;
  }

  public void setMaximumPoolSize(int maximumPoolSize){
    this.maximumPoolSize = maximumPoolSize;
  }

  public boolean isUseUnicode(){
    return useUnicode;
  }

  public void setUseUnicode(boolean useUnicode){
    this.useUnicode = useUnicode;
  }

  public String getCharacterEncoding(){
    return characterEncoding;
  }

  public void setCharacterEncoding(String characterEncoding){
    this.characterEncoding = characterEncoding;
  }

  public String getDataSourceClassName(){
    return dataSourceClassName;
  }

  public void setDataSourceClassName(String dataSourceClassName){
    this.dataSourceClassName = dataSourceClassName;
  }

  public long getMaxLifetime(){
    return maxLifetime;
  }

  public void setMaxLifetime(long maxLifetime){
    this.maxLifetime = maxLifetime;
  }

  public long getIdleTimeout(){
    return idleTimeout;
  }

  public void setIdleTimeout(long idleTimeout){
    this.idleTimeout = idleTimeout;
  }

  public long getConnectionTimeout(){
    return connectionTimeout;
  }

  public void setConnectionTimeout(long connectionTimeout){
    this.connectionTimeout = connectionTimeout;
  }

  public String getConnectionTestQuery(){
    return connectionTestQuery;
  }

  public void setConnectionTestQuery(String connectionTestQuery){
    this.connectionTestQuery = connectionTestQuery;
  }
}