package nlf.plugin.db.mongo;

import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.sql.ITemplate;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.util.Stringer;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoTemplate implements ITemplate,IMongo{

  /** 当前连接变量 */
  protected ConnVar cv;
  /** 事务接口 */
  protected ITrans trans;

  public void setTrans(ITrans t){
    trans = t;
  }

  public ITrans getTrans(){
    return trans;
  }

  public Object[] one(String sql){
    List<Object[]> l = query(sql);
    if(l.size()>1){
      throw new DaoException(L.get("sql.record_too_many"));
    }
    if(l.size()<1){
      throw new DaoException(L.get("sql.record_not_found"));
    }
    return l.get(0);
  }

  public Object[] one(String sql,Object param){
    Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.param_ignored"),sql));
    return one(sql);
  }

  public List<Object[]> query(String sql){
    MongoConnection conn = (MongoConnection)cv.getConnection();
    if(-1==sql.indexOf(".")){
      throw new DaoException(L.get("sql.sql_not_support")+sql);
    }
    if(-1==sql.indexOf(".find(")){
      throw new DaoException(L.get("sql.sql_not_support")+sql);
    }
    String tableName = Stringer.cut(sql,".",".");
    DBCursor cur = conn.getDb().getCollection(tableName).find();
    List<Object[]> l = new ArrayList<Object[]>(cur.count());
    while(cur.hasNext()){
      DBObject o = cur.next();
      Object[] b = new Object[]{o};
      l.add(b);
    }
    return l;
  }

  public List<Object[]> query(String sql,Object param){
    Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.param_ignored"),sql));
    return query(sql);
  }

  public List<Bean> queryEntity(String sql){
    MongoConnection conn = (MongoConnection)cv.getConnection();
    if(-1==sql.indexOf(".")){
      throw new DaoException(L.get("sql.sql_not_support")+sql);
    }
    if(-1==sql.indexOf(".find(")){
      throw new DaoException(L.get("sql.sql_not_support")+sql);
    }
    String tableName = Stringer.cut(sql,".",".");
    DBCursor cur = conn.getDb().getCollection(tableName).find();
    List<Bean> l = new ArrayList<Bean>(cur.count());
    while(cur.hasNext()){
      DBObject o = cur.next();
      Bean b = JSON.toBean(o.toString());
      l.add(b);
    }
    return l;
  }

  public List<Bean> queryEntity(String sql,Object param){
    Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.param_ignored"),sql));
    return queryEntity(sql);
  }

  public Bean oneEntity(String sql){
    List<Bean> l = queryEntity(sql);
    if(l.size()>1){
      throw new DaoException(L.get("sql.record_too_many"));
    }
    if(l.size()<1){
      throw new DaoException(L.get("sql.record_not_found"));
    }
    return l.get(0);
  }

  public Bean oneEntity(String sql,Object param){
    Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.param_ignored"),sql));
    return oneEntity(sql);
  }

  public int count(String sql){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public int count(String sql,Object param){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public int update(String sql){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public int update(String sql,Object param){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public PageData query(String sql,int pageNumber,int pageSize){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public PageData query(String sql,int pageNumber,int pageSize,Object param){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public PageData queryEntity(String sql,int pageNumber,int pageSize){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public PageData queryEntity(String sql,int pageNumber,int pageSize,Object param){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public ConnVar getConnVar(){
    return cv;
  }

  public int[] flush(){
    return new int[]{};
  }

  public void setAlias(String alias){
    List<ConnVar> l = Context.get(Statics.CONNECTIONS);
    for(ConnVar n:l){
      if(n.getAlias().equals(alias)){
        cv = n;
        break;
      }
    }
  }

  public void call(String procName,Object param){
    throw new DaoException(L.get("sql.proc_not_support")+cv.getDbType());
  }

  public void call(String procName){
    throw new DaoException(L.get("sql.proc_not_support")+cv.getDbType());
  }
}