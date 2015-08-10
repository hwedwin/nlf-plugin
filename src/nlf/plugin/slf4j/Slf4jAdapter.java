package nlf.plugin.slf4j;

import nc.liat6.frame.log.ILog;
import nc.liat6.frame.log.ILogAdapter;

/**
 * slf4j适配器
 * 
 * @author 6tail
 * 
 */
public class Slf4jAdapter implements ILogAdapter{

  public ILog getLog(String klass){
    return new Slf4jLog(klass);
  }

  public boolean isSupported(){
    try{
      Class.forName("org.slf4j.LoggerFactory");
    }catch(ClassNotFoundException e){
      return false;
    }
    return true;
  }
}
