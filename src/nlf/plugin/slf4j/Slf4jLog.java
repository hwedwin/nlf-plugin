package nlf.plugin.slf4j;

import nc.liat6.frame.log.impl.AbstractLog;
import nc.liat6.frame.util.Reflector;

/**
 * slf4j
 * 
 * @author 6tail
 * 
 */
public class Slf4jLog extends AbstractLog{

  private Object log;

  Slf4jLog(String klass){
    super(klass);
    log = Reflector.executeStatic("org.slf4j.LoggerFactory","getLogger",new Class[]{String.class},new String[]{klass});
  }

  public void debug(Object o){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    Reflector.execute(log,"debug",new Class[]{String.class},new String[]{sts[2]+"\r\n"+o});
  }

  public void debug(Object o,Throwable e){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    Reflector.execute(log,"debug",new Class[]{String.class,Throwable.class},new Object[]{sts[2]+"\r\n"+o,e});
  }

  public void error(Object o){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    Reflector.execute(log,"error",new Class[]{String.class},new String[]{sts[2]+"\r\n"+o});
  }

  public void error(Object o,Throwable e){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    Reflector.execute(log,"error",new Class[]{String.class,Throwable.class},new Object[]{sts[2]+"\r\n"+o,e});
  }

  public void fatal(Object o){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    Reflector.execute(log,"fatal",new Class[]{String.class},new String[]{sts[2]+"\r\n"+o});
  }

  public void fatal(Object o,Throwable e){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    Reflector.execute(log,"fatal",new Class[]{String.class,Throwable.class},new Object[]{sts[2]+"\r\n"+o,e});
  }

  public void info(Object o){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    Reflector.execute(log,"info",new Class[]{String.class},new String[]{sts[2]+"\r\n"+o});
  }

  public void info(Object o,Throwable e){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    Reflector.execute(log,"info",new Class[]{String.class,Throwable.class},new Object[]{sts[2]+"\r\n"+o,e});
  }

  public boolean isDebugEnabled(){
    return (Boolean)Reflector.execute(log,"isDebugEnabled");
  }

  public boolean isErrorEnabled(){
    return (Boolean)Reflector.execute(log,"isErrorEnabled");
  }

  public boolean isFatalEnabled(){
    return (Boolean)Reflector.execute(log,"isFatalEnabled");
  }

  public boolean isInfoEnabled(){
    return (Boolean)Reflector.execute(log,"isInfoEnabled");
  }

  public boolean isTraceEnabled(){
    return (Boolean)Reflector.execute(log,"isTraceEnabled");
  }

  public boolean isWarnEnabled(){
    return (Boolean)Reflector.execute(log,"isWarnEnabled");
  }

  public void trace(Object o){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    Reflector.execute(log,"trace",new Class[]{String.class},new String[]{sts[2]+"\r\n"+o});
  }

  public void trace(Object o,Throwable e){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    Reflector.execute(log,"trace",new Class[]{String.class,Throwable.class},new Object[]{sts[2]+"\r\n"+o,e});
  }

  public void warn(Object o){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    Reflector.execute(log,"warn",new Class[]{String.class},new String[]{sts[2]+"\r\n"+o});
  }

  public void warn(Object o,Throwable e){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    Reflector.execute(log,"warn",new Class[]{String.class,Throwable.class},new Object[]{sts[2]+"\r\n"+o,e});
  }
}
