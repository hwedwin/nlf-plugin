package nlf.plugin.db.mongo;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.IUpdater;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * MONGO更新器
 * 
 * @author 6tail
 * 
 */
public class MongoUpdater extends MongoExecuter implements IUpdater{

  public IUpdater table(String tableName){
    initTable(tableName);
    return this;
  }

  public IUpdater where(String sql){
    Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
    return this;
  }

  public IUpdater whereSql(String sql,Object[] values){
    Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
    return this;
  }

  public IUpdater where(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("");
    r.setOpEnd("");
    r.setTag("");
    wheres.add(r);
    paramWheres.add(value);
    return this;
  }

  public IUpdater whereLike(String column,Object value){
    Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereLike"));
    return where(column,value);
  }

  public IUpdater whereLeftLike(String column,Object value){
    Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereLeftLike"));
    return where(column,value);
  }

  public IUpdater whereRightLike(String column,Object value){
    Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereRightLike"));
    return where(column,value);
  }

  public IUpdater whereNq(String column,Object value){
    Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereNq"));
    return where(column,value);
  }

  public IUpdater whereIn(String column,Object... value){
    Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereIn"));
    return where(column,value);
  }

  public IUpdater whereNotIn(String column,Object... value){
    Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereNotIn"));
    return where(column,value);
  }

  public IUpdater set(String column,Object value){
    // 如果有重复的，替换值
    for(int i = 0;i<cols.size();i++){
      if(cols.get(i).getColumn().equals(column)){
        paramCols.set(i,value);
        return this;
      }
    }
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("");
    r.setOpEnd("");
    r.setTag("");
    cols.add(r);
    paramCols.add(value);
    return this;
  }

  public IUpdater set(String sql){
    Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.update_not_support"),sql));
    return this;
  }

  public IUpdater setSql(String sql,Object[] values){
    Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.update_not_support"),sql));
    return this;
  }

  public int update(){
    if(null==tableName){
      throw new DaoException(Stringer.print("??.?",L.get("sql.table_not_found"),template.getConnVar().getAlias(),tableName));
    }
    MongoConnection conn = (MongoConnection)template.getConnVar().getConnection();
    DBObject query = new BasicDBObject();
    DBObject update = new BasicDBObject();
    for(int i = 0;i<wheres.size();i++){
      Rule r = wheres.get(i);
      Object v = paramWheres.get(i);
      query.put(r.getColumn(),v);
    }
    for(int i = 0;i<cols.size();i++){
      Rule r = cols.get(i);
      Object v = paramCols.get(i);
      update.put(r.getColumn(),v);
    }
    reset();
    conn.getDb().getCollection(tableName).findAndModify(query,update);
    return 1;
  }

  public IUpdater setBean(Bean bean){
    for(String col:bean.keySet()){
      set(col,bean.get(col));
    }
    return this;
  }
}