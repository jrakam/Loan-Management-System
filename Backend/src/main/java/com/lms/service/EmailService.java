package com.lms.service;



import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;



@Service
public class EmailService {

  public static void sendEmail(String recipient, String subject, String body) {
    String sender = "rakamjyotshnapriya@gmail.com"; // replace with your email
    String host = "smtp.gmail.com"; // replace with your SMTP server

    // Get system properties
    Properties properties = System.getProperties();

    // Setup mail server
    properties.setProperty("mail.smtp.host", host);
    properties.setProperty("mail.smtp.auth", "true");
    properties.setProperty("mail.smtp.port", "587"); // or 465
    properties.setProperty("mail.smtp.starttls.enable", "true"); // if the server uses TLS

    // Get the default Session object
    Session session = Session.getInstance(properties, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("", ""); // replace with your email and password
      }
    });

    try {
      // Create a default MimeMessage object
      MimeMessage message = new MimeMessage(session);

      // Set From: header field
      message.setFrom(new InternetAddress(sender));

      // Set To: header field
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

      // Set Subject: header field
      message.setSubject(subject);

      // Now set the actual message
      message.setText(body);

      // Send message
      Transport.send(message);
      System.out.println("Sent message successfully....");
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
  }
}
