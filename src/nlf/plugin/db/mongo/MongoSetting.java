package nlf.plugin.db.mongo;

import nc.liat6.frame.db.setting.impl.SuperDbSetting;

/**
 * MONGO连接配置
 * 
 * @author 6tail
 * 
 */
public class MongoSetting extends SuperDbSetting{

  private static final long serialVersionUID = 5902769339352767337L;

  /** 默认连接类型 */
  public static final String DEFAULT_TYPE = "mongo";
  
  public MongoSetting(){
    type = DEFAULT_TYPE;
  }
}