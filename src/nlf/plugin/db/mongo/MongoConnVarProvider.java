package nlf.plugin.db.mongo;

import java.net.UnknownHostException;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.connection.impl.SuperConnVarProvider;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.util.IOHelper;
import nc.liat6.frame.util.Stringer;
import com.mongodb.Mongo;

/**
 * MONGO连接变量提供器
 * 
 * @author 6tail
 * 
 */
public class MongoConnVarProvider extends SuperConnVarProvider{

  public ConnVar getConnVar(){
    MongoSetting setting = (MongoSetting)this.setting;
    ConnVar cv = new ConnVar();
    cv.setDbType(setting.getDbType());
    cv.setAlias(setting.getAlias());
    MongoConnection conn = new MongoConnection();
    String ip = Stringer.cut(setting.getUrl(),"",":").trim();
    int port = Integer.parseInt(Stringer.cut(setting.getUrl(),":").trim());
    try{
      Mongo mongo = new Mongo(ip,port);
      mongo.getMongoOptions().setAutoConnectRetry(true);
      conn.setDb(mongo.getDB(setting.getDbName()));
    }catch(UnknownHostException e){
      IOHelper.closeQuietly(conn);
      throw new DaoException(L.get("db.exception.dao")+":"+setting.getUrl(),e);
    }
    conn.setConnVar(cv);
    cv.setConnection(conn);
    cv.setSetting(setting);
    return cv;
  }

  public boolean support(String connType){
    return "mongo".equalsIgnoreCase(connType);
  }
}