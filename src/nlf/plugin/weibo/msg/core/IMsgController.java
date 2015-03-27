package nlf.plugin.weibo.msg.core;

/**
 * 微博消息控制接口
 * 
 * @author 6tail
 *
 */
public interface IMsgController{
  /**
   * 处理消息请求
   * 
   * @param handler 微博消息处理接口
   * @return 处理结果字符串
   */
  String handle(IMsgHandler handler);
}