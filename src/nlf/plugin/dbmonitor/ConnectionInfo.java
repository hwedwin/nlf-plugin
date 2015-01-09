package nlf.plugin.dbmonitor;

import nc.liat6.frame.json.JSON;

/**
 * 连接信息封装
 * 
 * @author 6tail
 *
 */
public class ConnectionInfo{
  /** 调用的类名 */
  private String klass;
  /** 调用的方法名 */
  private String method;
  /** 调用的行号 */
  private int lineNumber;
  /** 唯一标识，其实就是Connection对象的toString */
  private String connectionId;
  /** 创建时间 */
  private long createTimeMillis;

  public String getKlass(){
    return klass;
  }

  public void setKlass(String klass){
    this.klass = klass;
  }

  public String getMethod(){
    return method;
  }

  public void setMethod(String method){
    this.method = method;
  }

  public int getLineNumber(){
    return lineNumber;
  }

  public void setLineNumber(int lineNumber){
    this.lineNumber = lineNumber;
  }

  public String getConnectionId(){
    return connectionId;
  }

  public void setConnectionId(String connectionId){
    this.connectionId = connectionId;
  }

  public long getCreateTimeMillis(){
    return createTimeMillis;
  }

  public void setCreateTimeMillis(long createTimeMillis){
    this.createTimeMillis = createTimeMillis;
  }

  public String toString(){
    return JSON.toJson(this,false);
  }
}