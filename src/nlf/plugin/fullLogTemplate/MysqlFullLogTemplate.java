package nlf.plugin.fullLogTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.db.custom.mysql.IMysql;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.entity.IBeanRule;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.ILog;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.util.Stringer;

/**
 * SQL执行模板的MYSQL实现
 * 
 * @author 6tail
 * 
 */
public class MysqlFullLogTemplate extends CommonFullLogTemplate implements IMysql{
  private static ILog log = Logger.getLog();

  public int count(String sql,Object param){
    flush();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    String nsql = sql;
    String upsql = sql.toUpperCase();
    int index = upsql.indexOf(" ORDER BY ");
    if(index>-1){
      nsql = sql.substring(0,index);
    }
    nsql = "SELECT COUNT(*) FROM ("+nsql+") NLFTABLE_";
    List<Object> pl = processParams(param);
    String logSql = nsql;
    for(Object p:pl){
      logSql = logSql.replaceFirst("\\?","'"+p+"'");
    }
    log.debug(Stringer.print("??",L.get(LocaleFactory.locale,"sql.count"),logSql));
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(nsql);
      processParams(stmt,pl);
      rs = stmt.executeQuery();
      rs.next();
      return rs.getInt(1);
    }catch(SQLException e){
      throw new DaoException(sql,e);
    }finally{
      finalize(stmt,rs);
    }
  }

  public int count(String sql){
    return count(sql,null);
  }

  public PageData query(String sql,int pageNumber,int pageSize){
    return query(sql,pageNumber,pageSize,null);
  }

  public PageData query(String sql,int pageNumber,int pageSize,Object param){
    flush();
    PageData d = new PageData();
    d.setRecordCount(count(sql,param));
    d.setPageSize(pageSize);
    int pageCount = d.getPageCount();
    d.setPageNumber(pageNumber>pageCount?pageCount:pageNumber);
    String nsql = "SELECT * FROM ("+sql+") NLFTABLE_ LIMIT "+((d.getPageNumber()-1)*d.getPageSize())+","+d.getPageSize();
    List<Object> pl = processParams(param);
    String logSql = nsql;
    for(Object p:pl){
      logSql = logSql.replaceFirst("\\?","'"+p+"'");
    }
    log.debug(Stringer.print("??\r\n??\r\n??",L.get(LocaleFactory.locale,"sql.query_page"),logSql,L.get(LocaleFactory.locale,"sql.query_page_num"),pageNumber,L.get(LocaleFactory.locale,"sql.query_page_size"),pageSize));
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<Object[]> l = null;
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(nsql);
      processParams(stmt,pl);
      rs = stmt.executeQuery();
      l = objs(rs);
    }catch(SQLException e){
      throw new DaoException(sql,e);
    }finally{
      finalize(stmt,rs);
    }
    d.setData(l);
    return d;
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
      throw new DaoException(sql,e);
    }finally{
      finalize(stmt,rs);
    }
  }

  public PageData queryEntity(String sql,int pageNumber,int pageSize){
    return queryEntity(sql,pageNumber,pageSize,null);
  }

  public PageData queryEntity(String sql,int pageNumber,int pageSize,Object param){
    flush();
    PageData d = new PageData();
    d.setRecordCount(count(sql,param));
    d.setPageSize(pageSize);
    int pageCount = d.getPageCount();
    d.setPageNumber(pageNumber>pageCount?pageCount:pageNumber);
    String nsql = "SELECT * FROM ("+sql+") NLFTABLE_ LIMIT "+((d.getPageNumber()-1)*d.getPageSize())+","+d.getPageSize();
    List<Object> pl = processParams(param);
    String logSql = nsql;
    for(Object p:pl){
      logSql = logSql.replaceFirst("\\?","'"+p+"'");
    }
    log.debug(Stringer.print("??\r\n??\r\n??",L.get(LocaleFactory.locale,"sql.query_entity_page"),logSql,L.get(LocaleFactory.locale,"sql.query_page_num"),pageNumber,L.get(LocaleFactory.locale,"sql.query_page_size"),pageSize));
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<Bean> l = null;
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(nsql);
      processParams(stmt,pl);
      rs = stmt.executeQuery();
      l = beans(rs);
    }catch(SQLException e){
      throw new DaoException(sql,e);
    }finally{
      finalize(stmt,rs);
    }
    d.setData(l);
    return d;
  }

  public List<Object[]> top(String sql,Object param,int n){
    flush();
    String nsql = sql+" LIMIT 0,"+n;
    List<Object> pl = processParams(param);
    String logSql = nsql;
    for(Object p:pl){
      logSql = logSql.replaceFirst("\\?","'"+p+"'");
    }
    log.debug(Stringer.print("??",L.get(LocaleFactory.locale,"sql.query_top"),logSql));
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(nsql);
      processParams(stmt,pl);
      rs = stmt.executeQuery();
      return objs(rs);
    }catch(SQLException e){
      throw new DaoException(sql,e);
    }finally{
      finalize(stmt,rs);
    }
  }

  public List<Bean> topEntity(String sql,Object param,int n){
    flush();
    String nsql = sql+" LIMIT 0,"+n;
    List<Object> pl = processParams(param);
    String logSql = nsql;
    for(Object p:pl){
      logSql = logSql.replaceFirst("\\?","'"+p+"'");
    }
    log.debug(Stringer.print("??",L.get(LocaleFactory.locale,"sql.query_top"),logSql));
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(nsql);
      processParams(stmt,pl);
      rs = stmt.executeQuery();
      return beans(rs);
    }catch(SQLException e){
      throw new DaoException(sql,e);
    }finally{
      finalize(stmt,rs);
    }
  }

  public <T>List<T> topObject(String sql,Object param,int n,Class<?> klass,IBeanRule rule){
    List<Bean> l = topEntity(sql,param,n);
    List<T> lt = new ArrayList<T>(l.size());
    for(Bean o:l){
      T t = o.toObject(klass,rule);
      lt.add(t);
    }
    return lt;
  }
}