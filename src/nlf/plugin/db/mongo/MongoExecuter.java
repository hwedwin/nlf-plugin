package nlf.plugin.db.mongo;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.plugin.impl.SuperExecuter;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * MONGO执行器
 * 
 * @author 6tail
 * 
 */
public abstract class MongoExecuter extends SuperExecuter implements IMongo{

  protected String tableName;

  public String getSql(){
    return null;
  }
  
  @Override
  protected void reset(){
    super.reset();
    tableName = null;
  }

  protected void initTable(String tableName){
    this.tableName = tableName;
  }

  protected DBObject bean2DBObject(Bean bean){
    if(null==bean){
      return null;
    }
    DBObject o = new BasicDBObject();
    for(String k:bean.keySet()){
      Object param = bean.get(k);
      if(null!=param&&param instanceof Bean){
        o.put(k,bean2DBObject((Bean)param));
      }else{
        o.put(k,param);
      }
    }
    return o;
  }
}