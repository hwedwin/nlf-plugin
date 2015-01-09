package nlf.plugin.dbmonitor;

import java.lang.reflect.Method;
import java.util.List;
import nc.liat6.frame.aop.AopAfterManager;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.connection.IConnection;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.db.transaction.impl.Trans;

/**
 * AOP切Trans类的init和close方法，当方法调用完成后执行
 * 
 * @author 6tail
 *
 */
public class Aop4Trans extends AopAfterManager{
  public Aop4Trans(){
    super(Trans.class.getName(),"init,close");
  }

  @Override
  public void execute(Object o,Method m,Object[] args){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    String method = m.getName();
    //Trans类的init方法负责创建连接，而这个init方法是在TransFactory.getTrans方法中调用的。
    if("init".equals(method)){
      int index = 0;
      StackTraceElement st;
      //取调用栈，找出是谁调用TransFactory.getTrans方法
      for(int i = 0,j = sts.length;i<j;i++){
        st = sts[i];
        if("getTrans".equals(st.getMethodName())){
          index = i+1;
        }else{
          if(index>0){
            break;
          }
        }
      }
      //取到了调用者
      st = sts[index];
      //找连接
      List<ConnVar> l = Context.get(Statics.CONNECTIONS);
      if(null!=l){
        for(ConnVar cv:l){
          if(null==cv){
            continue;
          }
          IConnection conn = cv.getConnection();
          if(null==conn){
            continue;
          }
          //添加到监控池
          ConnectionInfo ci = new ConnectionInfo();
          ci.setConnectionId(conn+"");
          ci.setKlass(st.getClassName());
          ci.setMethod(st.getMethodName());
          ci.setLineNumber(st.getLineNumber());
          ci.setCreateTimeMillis(System.currentTimeMillis());
          ConnectionMonitor.addConnection(ci);
        }
      }
    }else if("close".equals(method)){
      //Trans类的close方法负责关闭连接
      int index = 0;
      StackTraceElement st;
      for(int i = 0,j = sts.length;i<j;i++){
        st = sts[i];
        if("close".equals(st.getMethodName())){
          index = i+1;
          break;
        }
      }
      //取到了调用者
      st = sts[index];
      ITrans t = (ITrans)o;
      ConnectionMonitor.removeConnection(t.getTemplate().getConnVar().getConnection()+"");
    }
  }
}