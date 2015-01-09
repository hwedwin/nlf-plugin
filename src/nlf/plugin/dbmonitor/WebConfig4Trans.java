package nlf.plugin.dbmonitor;

import nc.liat6.frame.web.config.IWebManager;
import nc.liat6.frame.web.config.WebConfig;

/**
 * 连接监控专用web配置
 * @author 6tail
 *
 */
public class WebConfig4Trans extends WebConfig{
  
  @Override
  public IWebManager getWebManager(){
    return new WebManager4Trans(this);
  }
}