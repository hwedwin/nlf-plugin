package nlf.plugin.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 邮件客户端
 * 
 * @author 6tail
 *
 */
public class Email{
  /** 默认的SSL SMTP端口 */
  public static final int SSL_SMTP_PORT = 465;
  /** SSL */
  private boolean ssl;
  /** SMTP服务器 */
  private String hostSmtp;
  /** SMTP端口 */
  private int portSmtp;
  /** 用户名 */
  private String account;
  /** 密码 */
  private String password;
  /** 邮件内容 */
  private MailMsg msg = new MailMsg();

  private Properties getSmtpProperties(){
    Properties p = new Properties();
    p.put("mail.smtp.host",hostSmtp);
    p.put("mail.smtp.port",portSmtp+"");
    p.put("mail.smtp.auth","true");
    p.put("mail.smtp.debug","true");
    if(ssl){
      p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
      p.put("mail.smtp.socketFactory.fallback","false");
      p.put("mail.smtp.socketFactory.port",portSmtp+"");
    }
    return p;
  }

  private Email(){}

  public static Email create(){
    return new Email();
  }

  /**
   * 设置SMTP服务器地址和端口
   * 
   * @param host SMTP服务器地址
   * @param port SMTP端口
   * @return
   */
  public Email smtp(String host,int port){
    this.hostSmtp = host;
    this.portSmtp = port;
    return this;
  }

  /**
   * 设置发件人
   * 
   * @param from 发件人
   * @return
   */
  public Email from(String from){
    msg.setFromAddress(from);
    return this;
  }

  /**
   * 添加收件人
   * 
   * @param to 收件人邮箱
   * @return
   */
  public Email to(String to){
    msg.addToAddress(to);
    return this;
  }

  /**
   * 设置主题
   * 
   * @param subject 主题
   * @return
   */
  public Email subject(String subject){
    msg.setSubject(subject);
    return this;
  }

  /**
   * 添加内容
   * 
   * @param content 内容
   * @return
   */
  public Email content(MailContent content){
    msg.addContent(content);
    return this;
  }

  /**
   * 添加文本内容
   * 
   * @param text 文本内容
   * @return
   */
  public Email text(String text){
    return content(new MailContent(ContentType.TEXT,text));
  }

  /**
   * 添加html内容
   * 
   * @param html html内容
   * @return
   */
  public Email html(String html){
    return content(new MailContent(ContentType.HTML,html));
  }

  /**
   * 添加附件
   * 
   * @param file 文件
   * @return
   */
  public Email file(File file){
    return content(new MailContent(ContentType.FILE,file.getAbsolutePath()));
  }

  /**
   * SSL加密
   * 
   * @return
   */
  public Email ssl(){
    ssl = true;
    return this;
  }

  /**
   * 设置邮箱登录用户名密码
   * 
   * @param account 用户名
   * @param password 密码
   * @return
   */
  public Email auth(String account,String password){
    this.account = account;
    this.password = password;
    return this;
  }

  /**
   * 发送邮件
   * 
   * @throws MessagingException
   * @throws UnsupportedEncodingException
   */
  public void send() throws MessagingException,UnsupportedEncodingException{
    Session session = Session.getDefaultInstance(getSmtpProperties(),new Authenticator(){
      protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(account,password);
      }
    });
    Message message = new MimeMessage(session);
    message.setSubject(msg.getSubject());
    message.setFrom(new InternetAddress(msg.getFromAddress()));
    for(String s:msg.getToAddresses()){
      message.addRecipient(Message.RecipientType.TO,new InternetAddress(s));
    }
    message.setSentDate(new Date());
    Multipart multipart = new MimeMultipart();
    for(MailContent content:msg.getContents()){
      BodyPart contentPart = new MimeBodyPart();
      switch(content.getType()){
        case TEXT:
          contentPart.setContent(content.getContent(),"text/plain;charset=utf-8");
          break;
        case HTML:
          contentPart.setContent(content.getContent(),"text/html;charset=utf-8");
          break;
        case FILE:
          File file = new File(content.getContent());
          DataSource source = new FileDataSource(file);
          contentPart.setDataHandler(new DataHandler(source));
          contentPart.setFileName(MimeUtility.encodeWord(file.getName()));
          break;
      }
      multipart.addBodyPart(contentPart);
    }
    message.setContent(multipart);
    Transport.send(message);
  }
}