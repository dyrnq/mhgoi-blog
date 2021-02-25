package net.mhgoi.blog.components;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 少年
 */
@Component
public class EmailUtil {

    @Value("${emial.hostName}")
    private String hostName;

    @Value("${emial.charset}")
    private String charset;

    @Value("${emial.from}")
    private String from;

    @Value("${emial.fromName}")
    private String fromName;

    @Value("${emial.username}")
    private String username;

    @Value("${emial.password}")
    private String password;

    @Value("${emial.port}")
    private int port;

    public boolean sendEmail(String receiver, String subject, String content) {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(hostName);
        email.setSmtpPort(port);
        email.setSSLOnConnect(true);
        email.setCharset(charset);
        try {
            email.addTo(receiver);
            email.setFrom(from, fromName);
            email.setAuthentication(username, password);
            email.setSubject(subject);//设置发送主题
            email.setMsg(content);//设置发送内容
            email.send();
            return true;
        } catch (EmailException e) {
            return false;
        }
    }
}
