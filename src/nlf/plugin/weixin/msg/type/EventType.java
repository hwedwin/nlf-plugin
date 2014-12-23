package nlf.plugin.weixin.msg.type;

/**
 * 事件类型
 * 
 * @author 6tail
 * 
 */
public enum EventType{
  /** 用户关注 */
  subscribe,
  /** 用户取消关注 */
  unsubscribe,
  /** 二维码扫描 */
  scan,
  /** 地理位置 */
  LOCATION,
  /** 菜单点击 */
  CLICK,
  /** 网页 */
  VIEW
}