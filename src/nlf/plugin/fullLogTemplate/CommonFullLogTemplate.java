package nlf.plugin.fullLogTemplate;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.entity.IBeanRule;
import nc.liat6.frame.db.entity.ResultSetIterator;
import nc.liat6.frame.db.entity.StatementAndResultSet;
import nc.liat6.frame.db.entity.TResultSetIterator;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.sql.impl.SuperTemplate;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.ILog;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.util.Stringer;

/**
 * SQL执行模板的通用实现
 * 
 * @author 6tail
 * 
 */
public class CommonFullLogTemplate extends SuperTemplate{
  private static ILog log = Logger.getLog();

  public List<Object[]> query(String sql){
    return query(sql,null);
  }

  public List<Object[]> query(String sql,Object param){
    flush();
    List<Object> pl = processParams(param);
    String logSql = sql;
    for(Object p:pl){
      logSql = logSql.replaceFirst("\\?","'"+p+"'");
    }
    log.debug(Stringer.print("??",L.get(LocaleFactory.locale,"sql.query"),logSql));
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(sql);
      processParams(stmt,pl);
      rs = stmt.executeQuery();
      return objs(rs);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,rs);
    }
  }

  public Object[] one(String sql){
    return one(sql,null);
  }

  public Object[] one(String sql,Object param){
    List<Object[]> l = query(sql,param);
    int n = l.size();
    if(n>1){
      throw new DaoException(L.get("sql.record_too_many"));
    }
    if(n<1){
      throw new DaoException(L.get("sql.record_not_found"));
    }
    return l.get(0);
  }

  public Bean oneEntity(String sql){
    return oneEntity(sql,null);
  }

  public Bean oneEntity(String sql,Object param){
    List<Bean> l = queryEntity(sql,param);
    int n = l.size();
    if(n>1){
      throw new DaoException(L.get("sql.record_too_many"));
    }
    if(n<1){
      throw new DaoException(L.get("sql.record_not_found"));
    }
    return l.get(0);
  }

  public List<Bean> queryEntity(String sql){
    return queryEntity(sql,null);
  }

  public List<Bean> queryEntity(String sql,Object param){
    flush();
    List<Object> pl = processParams(param);
    String logSql = sql;
    for(Object p:pl){
      logSql = logSql.replaceFirst("\\?","'"+p+"'");
    }
    log.debug(Stringer.print("??",L.get(LocaleFactory.locale,"sql.query_entity"),logSql));
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(sql);
      processParams(stmt,pl);
      rs = stmt.executeQuery();
      return beans(rs);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,rs);
    }
  }

  public int update(String sql){
    return update(sql,null);
  }

  private int[] batchUpdate() throws SQLException{
    if(null==stackSql){
      return new int[]{};
    }
    stackSql = null;
    if(null==stackStatement){
      return new int[]{};
    }
    if(!cv.getConnection().isSupportsBatchUpdates()){
      return new int[]{};
    }
    if(!trans.isBatchEnabled()){
      return new int[]{};
    }
    try{
      return stackStatement.executeBatch();
    }finally{
      finalize(stackStatement,null);
    }
  }

  public int[] flush(){
    try{
      return batchUpdate();
    }catch(SQLException e){
      throw new DaoException(e);
    }
  }

  /**
   * 更新，如果支持批处理，返回-1，如果不支持批处理，返回更新记录数
   */
  public int update(String sql,Object param){
    boolean sptBatch = cv.getConnection().isSupportsBatchUpdates()&&trans.isBatchEnabled();
    List<Object> pl = processParams(param);
    String logSql = sql;
    for(Object p:pl){
      logSql = logSql.replaceFirst("\\?","'"+p+"'");
    }
    log.debug(Stringer.print("??",L.get(LocaleFactory.locale,"sql.update"),logSql));
    PreparedStatement stmt = null;
    try{
      // 如果支持批处理
      if(sptBatch){
        if(sql.equals(stackSql)){// 如果SQL语句一样，直接复用原来的Statement
          stmt = stackStatement;
        }else{// 如果SQL语句变了，提交上次的批处理，新建Statement
          flush();
          stmt = cv.getConnection().getSqlConnection().prepareStatement(sql);
          stackStatement = stmt;
          stackSql = sql;
        }
      }else{// 如果不支持批处理，新建Statement
        stmt = cv.getConnection().getSqlConnection().prepareStatement(sql);
        stackSql = null;
      }
      processParams(stmt,pl);
      // 如果支持批处理，加入批处理
      if(sptBatch){
        stmt.addBatch();
        return -1;
      }
      // 如果不支持批处理，执行update
      return stmt.executeUpdate();
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      // 如果不支持批处理，直接善后
      if(!sptBatch){
        finalize(stmt,null);
      }
    }
  }

  public void call(String procName){
    flush();
    CallableStatement stmt = null;
    try{
      String sql = Stringer.print("{call ?()}",procName);
      log.debug(Stringer.print("??",L.get("sql.call_proc"),sql));
      stmt = cv.getConnection().getSqlConnection().prepareCall(sql);
      stmt.execute();
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,null);
    }
  }

  public void call(String procName,Object param){
    flush();
    List<Object> pl = processParams(param);
    StringBuilder s = new StringBuilder();
    s.append("{call ?(");
    for(int i = 0,j = pl.size();i<j;i++){
      s.append("?");
      if(i<j-1){
        s.append(",");
      }
    }
    s.append(")}");
    String sql = Stringer.print(s.toString(),procName);
    String logSql = sql;
    for(Object p:pl){
      logSql = logSql.replaceFirst("\\?","'"+p+"'");
    }
    log.debug(Stringer.print("??",L.get(LocaleFactory.locale,"sql.call_proc"),logSql));
    CallableStatement stmt = null;
    try{
      stmt = cv.getConnection().getSqlConnection().prepareCall(sql);
      processParams(stmt,pl);
      stmt.execute();
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,null);
    }
  }

  public int count(String sql){
    return count(sql,null);
  }

  public int count(String sql,Object param){
    throw new DaoException(L.get("sql.count_not_support")+cv.getDbType());
  }

  public PageData query(String sql,int pageNumber,int pageSize){
    return query(sql,pageNumber,pageSize,null);
  }

  public PageData query(String sql,int pageNumber,int pageSize,Object param){
    throw new DaoException(L.get("sql.page_not_support")+cv.getDbType());
  }

  public PageData queryEntity(String sql,int pageNumber,int pageSize){
    return queryEntity(sql,pageNumber,pageSize,null);
  }

  public PageData queryEntity(String sql,int pageNumber,int pageSize,Object param){
    throw new DaoException(L.get("sql.page_not_support")+cv.getDbType());
  }

  public Iterator<Bean> iterator(String sql){
    return iterator(sql,null);
  }

  public Iterator<Bean> iterator(String sql,Object param){
    flush();
    List<Object> pl = processParams(param);
    String logSql = sql;
    for(Object p:pl){
      logSql = logSql.replaceFirst("\\?","'"+p+"'");
    }
    log.debug(Stringer.print("??",L.get(LocaleFactory.locale,"sql.query_entity"),logSql));
    Iterator<Bean> l = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(sql);
      processParams(stmt,pl);
      rs = stmt.executeQuery();
      l = new ResultSetIterator(rs);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      if(null!=stmt&&null!=rs){
        sars.add(new StatementAndResultSet(stmt,rs));
      }
    }
    return l;
  }

  public <T>List<T> queryObject(String sql,Class<?> klass){
    return queryObject(sql,null,klass,null);
  }

  public <T>List<T> queryObject(String sql,Class<?> klass,IBeanRule rule){
    return queryObject(sql,null,klass,rule);
  }

  public <T>List<T> queryObject(String sql,Object param,Class<?> klass){
    return queryObject(sql,param,klass,null);
  }

  public <T>List<T> queryObject(String sql,Object param,Class<?> klass,IBeanRule rule){
    List<Bean> l = queryEntity(sql,param);
    List<T> lt = new ArrayList<T>(l.size());
    for(Bean o:l){
      T t = o.toObject(klass,rule);
      lt.add(t);
    }
    return lt;
  }

  public <T>T oneObject(String sql,Class<?> klass){
    return oneObject(sql,null,klass,null);
  }

  public <T>T oneObject(String sql,Class<?> klass,IBeanRule rule){
    return oneObject(sql,null,klass,rule);
  }

  public <T>T oneObject(String sql,Object param,Class<?> klass){
    return oneObject(sql,param,klass,null);
  }

  public <T>T oneObject(String sql,Object param,Class<?> klass,IBeanRule rule){
    return oneEntity(sql,param).toObject(klass,rule);
  }

  public PageData queryObject(String sql,int pageNumber,int pageSize,Class<?> klass){
    return queryObject(sql,pageNumber,pageSize,null,klass,null);
  }

  public PageData queryObject(String sql,int pageNumber,int pageSize,Class<?> klass,IBeanRule rule){
    return queryObject(sql,pageNumber,pageSize,null,klass,rule);
  }

  public PageData queryObject(String sql,int pageNumber,int pageSize,Object param,Class<?> klass){
    return queryObject(sql,pageNumber,pageSize,param,klass,null);
  }

  public PageData queryObject(String sql,int pageNumber,int pageSize,Object param,Class<?> klass,IBeanRule rule){
    PageData pd = queryEntity(sql,pageNumber,pageSize,param);
    int size = pd.getSize();
    List<Object> l = new ArrayList<Object>(size);
    for(int i = 0,j = pd.getSize();i<j;i++){
      l.add(pd.getBean(i).toObject(klass,rule));
    }
    pd.setData(l);
    return pd;
  }

  public <T>Iterator<T> iterator(String sql,Class<?> klass){
    return iterator(sql,null,klass,null);
  }

  public <T>Iterator<T> iterator(String sql,Class<?> klass,IBeanRule rule){
    return iterator(sql,null,klass,rule);
  }

  public <T>Iterator<T> iterator(String sql,Object param,Class<?> klass){
    return iterator(sql,param,klass,null);
  }

  public <T>Iterator<T> iterator(String sql,Object param,Class<?> klass,IBeanRule rule){
    flush();
    List<Object> pl = processParams(param);
    String logSql = sql;
    for(Object p:pl){
      logSql = logSql.replaceFirst("\\?","'"+p+"'");
    }
    log.debug(Stringer.print("??",L.get(LocaleFactory.locale,"sql.query_entity"),logSql));
    Iterator<T> l = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(sql);
      processParams(stmt,pl);
      rs = stmt.executeQuery();
      l = new TResultSetIterator<T>(rs,klass,rule);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      if(null!=stmt&&null!=rs){
        sars.add(new StatementAndResultSet(stmt,rs));
      }
    }
    return l;
  }

  public List<Object[]> top(String sql,int n){
    return top(sql,null,n);
  }

  public List<Object[]> top(String sql,Object param,int n){
    throw new DaoException(L.get("sql.top_not_support")+cv.getDbType());
  }

  public List<Bean> topEntity(String sql,int n){
    return topEntity(sql,null,n);
  }

  public List<Bean> topEntity(String sql,Object param,int n){
    throw new DaoException(L.get("sql.top_not_support")+cv.getDbType());
  }

  public <T>List<T> topObject(String sql,int n,Class<?> klass){
    return topObject(sql,n,klass,null);
  }

  public <T>List<T> topObject(String sql,int n,Class<?> klass,IBeanRule rule){
    return topObject(sql,null,n,klass,rule);
  }

  public <T>List<T> topObject(String sql,Object param,int n,Class<?> klass){
    return topObject(sql,param,n,klass,null);
  }

  public <T>List<T> topObject(String sql,Object param,int n,Class<?> klass,IBeanRule rule){
    throw new DaoException(L.get("sql.top_not_support")+cv.getDbType());
  }
}