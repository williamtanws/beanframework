package com.beanframework.email.service;

import java.io.File;

public interface EmailService {
  void sendEmail(String[] to, String[] cc, String[] bcc, String subject, String text, String html,
      File[] files) throws Exception;
}
