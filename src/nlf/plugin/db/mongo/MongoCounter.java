package nlf.plugin.db.mongo;

import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.ICounter;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * MONGO计数器
 * 
 * @author 6tail
 * 
 */
public class MongoCounter extends MongoExecuter implements ICounter{

  public ICounter table(String tableName){
    initTable(tableName);
    return this;
  }

  public ICounter column(String... column){
    for(String c:column){
      Rule rule = new Rule();
      rule.setColumn(c);
      cols.add(rule);
    }
    return this;
  }

  public ICounter where(String sql){
    Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
    return this;
  }

  public ICounter whereSql(String sql,Object[] values){
    Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
    return this;
  }

  public ICounter where(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("");
    r.setOpEnd("");
    r.setTag("");
    wheres.add(r);
    paramWheres.add(value);
    return this;
  }

  public ICounter whereLike(String column,Object value){
    Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereLike"));
    return where(column,value);
  }

  public ICounter whereLeftLike(String column,Object value){
    Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereLeftLike"));
    return where(column,value);
  }

  public ICounter whereRightLike(String column,Object value){
    Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereRightLike"));
    return where(column,value);
  }

  public ICounter whereNq(String column,Object value){
    Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereNq"));
    return where(column,value);
  }

  public ICounter whereIn(String column,Object... value){
    Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereIn"));
    return where(column,value);
  }

  public ICounter whereNotIn(String column,Object... value){
    Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereNotIn"));
    return where(column,value);
  }

  public int count(){
    if(null==tableName){
      throw new DaoException(Stringer.print("??.?",L.get("sql.table_not_found"),template.getConnVar().getAlias(),tableName));
    }
    MongoConnection conn = (MongoConnection)template.getConnVar().getConnection();
    DBObject ref = new BasicDBObject();
    DBObject keys = new BasicDBObject();
    for(int i = 0;i<cols.size();i++){
      keys.put(cols.get(i).getColumn(),1);
    }
    for(int i = 0;i<wheres.size();i++){
      Rule r = wheres.get(i);
      Object v = paramWheres.get(i);
      ref.put(r.getColumn(),v);
    }
    DBCursor cur = conn.getDb().getCollection(tableName).find(ref,keys);
    return cur.count();
  }
}