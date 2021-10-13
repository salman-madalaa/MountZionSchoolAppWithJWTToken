package com.zion.school.helper;

import com.zion.school.model.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring5.SpringTemplateEngine;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


/**
 * Created by Lenovo on 07-10-2021.
 */
@Component
public class EmailHelper {
    private static final String NOREPLY_ADDRESS = "noreply@mountZionSchool.com";

    @Value("${spring.mail.username}")
    private String emailUserName;

    @Value("${spring.mail.password}")
    private String emailPassword;

    @Autowired(required = true)
    private JavaMailSender javaMailSender;

//    @Autowired
//    private SimpleMailMessage simpleMailMessage;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendmail(){

    /* -----------------sending mail using jakartha mail --------------------
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUserName, emailPassword);
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(emailUserName, false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("madalasalman@gmail.com"));
        msg.setSubject("Tutorials point email");
        msg.setContent("Tutorials point email", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Tutorials point email", "text/html");

        Transport.send(msg);
*/
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emailUserName);
        msg.setFrom(NOREPLY_ADDRESS);
        msg.setSubject("Welcome to Mount Zion School");
        msg.setText("checking email message");
        javaMailSender.send(msg);
    }

    public void sendUserNameAndPasswordMail(User user){
        /* -----------------sending mail using jakartha mail --------------------
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUserName, emailPassword);
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(emailUserName, false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        msg.setSubject("Welcome to CassiniPLM!");
        msg.setContent("username :"+ username + ";" +" password: "+password, "../resources/templates/email/newUser.html");
        msg.setSentDate(new Date());
        Transport.send(msg);
        */

        /*----------------- Code for Sending HTML Email -----------*/
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(NOREPLY_ADDRESS);
            helper.setTo(emailUserName);
            helper.setSubject("Welcome to CassiniPLM!");
//            helper.setText("<b>Hi</b>,<br><h2>Welcome to CassiniPLM</h2>", true);


            Context context = new Context();
            context.setVariable("user",user);
            String process = templateEngine.process("email/newUser",context);

            helper.setText(process, true);


            /*------------Code for Sending Email with Attachment--------------------*/
//            FileSystemResource file = new FileSystemResource(new File("C:/Users/Lenovo/folders/Desktop/oter/dadyUANCard.pdf"));
//            helper.addAttachment("DadyUANCard.pdf", file);

            /*------------Code Example for Sending Email with Inline Images --------------*/
//            FileSystemResource resource = new FileSystemResource(new File("C:/Users/Lenovo/folders/Desktop/oter/Screenshot.png"));
//            helper.addInline("image", resource);


            javaMailSender.send(message);

        } catch (MessagingException exception) {
            exception.printStackTrace();
        }

    }

}
