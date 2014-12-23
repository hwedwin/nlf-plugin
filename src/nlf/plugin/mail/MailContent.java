package nlf.plugin.mail;

/**
 * 邮件内容
 * 
 * @author 6tail
 *
 */
public class MailContent{
  /** 类型 */
  private ContentType type;
  /** 内容 */
  private String content;

  public MailContent(){}

  public MailContent(ContentType type,String content){
    this.type = type;
    this.content = content;
  }

  public ContentType getType(){
    return type;
  }

  public void setType(ContentType type){
    this.type = type;
  }

  public String getContent(){
    return content;
  }

  public void setContent(String content){
    this.content = content;
  }
}
