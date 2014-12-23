package nlf.plugin.db.mongo;

import java.sql.Connection;
import nc.liat6.frame.db.connection.SuperConnection;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.locale.L;
import com.mongodb.DB;

/**
 * MONGO数据库连接
 * 
 * @author 6tail
 * 
 */
public class MongoConnection extends SuperConnection{

  private DB db;

  public void close(){}

  public void rollback(){}

  public Connection getSqlConnection(){
    throw new DaoException(L.get("sql.conn_not_support"));
  }

  public boolean isSupportsBatchUpdates(){
    return false;
  }

  public void commit(){}

  public boolean isClosed(){
    return false;
  }

  public DB getDb(){
    return db;
  }

  public void setDb(DB db){
    this.db = db;
  }
}