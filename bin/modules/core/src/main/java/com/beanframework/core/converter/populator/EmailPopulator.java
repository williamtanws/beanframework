package com.beanframework.core.converter.populator;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.EmailDto;
import com.beanframework.email.domain.Email;

@Component
public class EmailPopulator extends AbstractPopulator<Email, EmailDto>
    implements Populator<Email, EmailDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(EmailPopulator.class);

  @Override
  public void populate(Email source, EmailDto target) throws PopulatorException {
    populateGeneric(source, target);
    target.setName(source.getName());
    target.setToRecipients(source.getToRecipients());
    target.setCcRecipients(source.getCcRecipients());
    target.setBccRecipients(source.getBccRecipients());
    target.setSubject(source.getSubject());
    target.setText(source.getText());
    target.setHtml(source.getHtml());
    target.setStatus(source.getStatus());
    target.setResult(source.getResult());
    target.setMessage(source.getMessage());

    for (UUID uuid : source.getMedias()) {
      target.getMedias().add(populateMedia(uuid));
    }
  }

}
