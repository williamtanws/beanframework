package com.beanframework.ckfinder;

import org.springframework.stereotype.Component;
import com.cksource.ckfinder.config.Config;
import com.cksource.ckfinder.config.loader.ConfigLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

@Component
public class CustomConfigLoader implements ConfigLoader {

  @Override
  public Config loadConfig() throws Exception {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    return mapper.readValue(CustomConfigLoader.class.getResourceAsStream("/ckfinder.yml"),
        CustomConfig.class);
  }
}
