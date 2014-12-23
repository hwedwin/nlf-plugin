package sample;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import nlf.plugin.mail.Email;

/**
 * 发送邮件
 * @author 6tail
 *
 */
public class MailSample{
  public static void main(String[] args) throws MessagingException, UnsupportedEncodingException{
    Email.create()
      .smtp("smtp.qq.com",465)
      .auth("370082990","密码")
      .from("6tail@6tail.cn")
      .to("6tail@6tail.cn")
      .ssl()
      .subject("通知")
      .text("测试一下吧")
      .send();
  }
}