package com.beanframework.email.service;

import java.io.File;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

  @Autowired
  private JavaMailSender javaMailSender;

  @Value("${spring.mail.from.email}")
  private String fromEmail;

  @Override
  public void sendEmail(String[] to, String[] cc, String[] bcc, String subject, String text,
      String html, File[] files) throws Exception {

    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
    helper.setFrom(fromEmail);
    if (to != null) {
      helper.setTo(to);
    }
    if (cc != null) {
      helper.setCc(cc);
    }
    if (bcc != null) {
      helper.setBcc(bcc);
    }
    if (StringUtils.isNotBlank(subject)) {
      helper.setSubject(subject);
    }
    if (StringUtils.isNotBlank(text) && StringUtils.isNotBlank(html)) {
      helper.setText(text, html);
    } else {
      if (StringUtils.isNotBlank(text)) {
        helper.setText(text);
      }
      if (StringUtils.isNotBlank(html)) {
        helper.setText(html, true);
      }
    }

    if (files != null) {
      for (int i = 0; i < files.length; i++) {
        FileSystemResource file = new FileSystemResource(files[i]);
        helper.addAttachment(files[i].getName(), file);
      }
    }
    javaMailSender.send(mimeMessage);
  }
}
