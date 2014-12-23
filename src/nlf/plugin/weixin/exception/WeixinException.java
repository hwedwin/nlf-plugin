package nlf.plugin.weixin.exception;

/**
 * 微信公众号异常
 * 
 * @author 6tail
 *
 */
public class WeixinException extends Exception{
  private static final long serialVersionUID = 2246440553954369261L;
  private int errorCode;

  public WeixinException(){
    super();
  }

  public WeixinException(int errorCode,String message){
    super(message);
    this.errorCode = errorCode;
  }

  public WeixinException(String message){
    super(message);
  }

  public WeixinException(String message,Throwable cause){
    super(message,cause);
  }

  public WeixinException(Throwable cause){
    super(cause);
  }

  public int getErrorCode(){
    return errorCode;
  }

  public void setErrorCode(int errorCode){
    this.errorCode = errorCode;
  }
}