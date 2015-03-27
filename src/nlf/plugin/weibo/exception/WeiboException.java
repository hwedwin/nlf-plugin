package nlf.plugin.weibo.exception;

/**
 * 微博异常
 * 
 * @author 6tail
 *
 */
public class WeiboException extends Exception{
  private static final long serialVersionUID = -4810936950278218182L;
  private int errorCode;
  private String error;
  private String request;

  public WeiboException(){
    super();
  }

  public WeiboException(int errorCode,String error,String request){
    super(error);
    this.error = error;
    this.errorCode = errorCode;
    this.request = request;
  }

  public WeiboException(String error){
    super(error);
  }

  public WeiboException(String error,Throwable cause){
    super(error,cause);
  }

  public WeiboException(Throwable cause){
    super(cause);
  }

  public int getErrorCode(){
    return errorCode;
  }

  public void setErrorCode(int errorCode){
    this.errorCode = errorCode;
  }

  public String getError(){
    return error;
  }

  public void setError(String error){
    this.error = error;
  }

  public String getRequest(){
    return request;
  }

  public void setRequest(String request){
    this.request = request;
  }
}