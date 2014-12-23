package nlf.plugin.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮件
 * 
 * @author 6tail
 *
 */
public class MailMsg{
  /** 发件人 */
  private String fromAddress;
  /** 收件人 */
  private List<String> toAddresses = new ArrayList<String>();
  /** 主题 */
  private String subject;
  /** 内容 */
  private List<MailContent> contents = new ArrayList<MailContent>();

  public String getFromAddress(){
    return fromAddress;
  }

  public void setFromAddress(String fromAddress){
    this.fromAddress = fromAddress;
  }

  public List<String> getToAddresses(){
    return toAddresses;
  }

  public void setToAddresses(List<String> toAddresses){
    this.toAddresses = toAddresses;
  }

  public void addToAddress(String toAddress){
    toAddresses.add(toAddress);
  }

  public String getSubject(){
    return subject;
  }

  public void setSubject(String subject){
    this.subject = subject;
  }

  public List<MailContent> getContents(){
    return contents;
  }

  public void setContents(List<MailContent> contents){
    this.contents = contents;
  }

  public void addContent(MailContent content){
    contents.add(content);
  }
}