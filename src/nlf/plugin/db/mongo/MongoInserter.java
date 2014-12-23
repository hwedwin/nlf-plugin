package nlf.plugin.db.mongo;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.IInserter;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

/**
 * MONG插入器
 * 
 * @author 6tail
 * 
 */
public class MongoInserter extends MongoExecuter implements IInserter{

  private Bean row = new Bean();

  public IInserter table(String tableName){
    initTable(tableName);
    return this;
  }

  public IInserter set(String column,Object value){
    row.set(column,value);
    return this;
  }

  public IInserter setSql(String column,String valueSql){
    Logger.getLog().warn(Stringer.print("??=?",L.get(LocaleFactory.locale,"sql.insert_not_support"),column,valueSql));
    return this;
  }

  public IInserter setSql(String column,String valueSql,Object[] values){
    Logger.getLog().warn(Stringer.print("??=?",L.get(LocaleFactory.locale,"sql.insert_not_support"),column,valueSql));
    return this;
  }

  public void reset(){
    row.clear();
  }

  public int insert(){
    if(null==tableName){
      throw new DaoException(Stringer.print("??.?",L.get("sql.table_not_found"),template.getConnVar().getAlias(),tableName));
    }
    MongoConnection conn = (MongoConnection)template.getConnVar().getConnection();
    conn.getDb().getCollection(tableName).save(bean2DBObject(row));
    reset();
    return 1;
  }

  public IInserter setBean(Bean bean){
    for(String col:bean.keySet()){
      set(col,bean.get(col));
    }
    return this;
  }
}