package nlf.plugin.db.mongo;

import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.IDeleter;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * MONGO删除器
 * 
 * @author 6tail
 * 
 */
public class MongoDeleter extends MongoExecuter implements IDeleter{

  public IDeleter table(String tableName){
    initTable(tableName);
    return this;
  }

  public IDeleter where(String sql){
    Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
    return this;
  }

  public IDeleter whereSql(String sql,Object[] values){
    Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
    return this;
  }

  public IDeleter where(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("");
    r.setOpEnd("");
    r.setTag("");
    wheres.add(r);
    paramWheres.add(value);
    return this;
  }

  public IDeleter whereIn(String column,Object... value){
    Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereIn"));
    return where(column,value);
  }

  public IDeleter whereNotIn(String column,Object... value){
    Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereNotIn"));
    return where(column,value);
  }

  public int delete(){
    if(null==tableName){
      throw new DaoException(Stringer.print("??.?",L.get("sql.table_not_found"),template.getConnVar().getAlias(),tableName));
    }
    MongoConnection conn = (MongoConnection)template.getConnVar().getConnection();
    DBObject query = new BasicDBObject();
    for(int i = 0;i<wheres.size();i++){
      Rule r = wheres.get(i);
      Object v = paramWheres.get(i);
      query.put(r.getColumn(),v);
    }
    reset();
    conn.getDb().getCollection(tableName).findAndRemove(query);
    return 1;
  }
}